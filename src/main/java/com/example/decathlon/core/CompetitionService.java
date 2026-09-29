package com.example.decathlon.core;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class CompetitionService {
    private final ScoringService scoring;

    public CompetitionService(ScoringService scoring) {
        this.scoring = scoring;
    }

    public static class Competitor {
        public final String name;
        public final Map<String, Double> raw = new ConcurrentHashMap<>();
        public final Map<String, Integer> points = new ConcurrentHashMap<>();

        public Competitor(String name) {
            this.name = name;
        }

        public int total() {
            return points.values().stream().mapToInt(i -> i).sum();
        }
    }

    private final Map<String, Competitor> competitors = new LinkedHashMap<>();

    public synchronized void addCompetitor(String name) {
        if (!competitors.containsKey(name)) {
            competitors.put(name, new Competitor(name));
        }
    }

    public synchronized int score(String name, String eventId, double raw) {
        Competitor c = competitors.computeIfAbsent(name, Competitor::new);
        int pts = scoring.score(eventId, raw);
        c.raw.put(eventId, raw);
        c.points.put(eventId, pts);
        return pts;
    }

    public synchronized List<Map<String, Object>> standings() {
        List<Map<String, Object>> list = competitors.values().stream()
                .map(c -> {
                    Map<String, Object> m = new LinkedHashMap<>();
                    m.put("name", c.name);
                    m.put("raw", new LinkedHashMap<>(c.raw));
                    m.put("scores", new LinkedHashMap<>(c.points));
                    m.put("total", c.total());
                    return m;
                })
                .sorted(Comparator.comparingInt(m -> -((Integer) m.get("total"))))
                .collect(Collectors.toList());

        for (Map<String, Object> m : list) {
            int total = (Integer) m.get("total");
            long higher = list.stream().filter(o -> (Integer) o.get("total") > total).count();
            m.put("standing", (int) higher + 1);
        }

        return list;
    }

    public synchronized String exportCsv() {
        Set<String> eventIds = new LinkedHashSet<>();
        competitors.values().forEach(c -> eventIds.addAll(c.points.keySet()));
        List<String> header = new ArrayList<>();
        header.add("Name");
        header.addAll(eventIds);
        header.add("Total");
        header.add("Standing");

        List<Map<String, Object>> standings = standings();
        Map<String, Integer> standingByName = new LinkedHashMap<>();
        for (Map<String, Object> m : standings) {
            standingByName.put((String) m.get("name"), (Integer) m.get("standing"));
        }

        StringBuilder sb = new StringBuilder();
        sb.append(String.join(",", header)).append("\n");
        for (Competitor c : competitors.values()) {
            List<String> row = new ArrayList<>();
            row.add(c.name);
            int sum = 0;
            for (String ev : eventIds) {
                Integer p = c.points.get(ev);
                row.add(p == null ? "" : String.valueOf(p));
                if (p != null) sum += p;
            }
            row.add(String.valueOf(sum));
            row.add(String.valueOf(standingByName.get(c.name)));
            sb.append(String.join(",", row)).append("\n");
        }
        return sb.toString();
    }
}