package com.example.decathlon.core;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class ScoringService {
    public enum Type { TRACK, FIELD }
    public record EventDef(String id, Type type, double A, double B, double C, String unit) {}

    private final Map<String, EventDef> events = buildEvents();

    private static Map<String, EventDef> buildEvents() {
        Map<String, EventDef> m = new LinkedHashMap<>();
        m.put("100m",             new EventDef("100m",             Type.TRACK, 25.4347,   18.0,  1.81,  "s"));
        m.put("deca110mHurdles",  new EventDef("deca110mHurdles",  Type.TRACK, 5.74352,   28.5,  1.92,  "s"));
        m.put("decathlon1500m",   new EventDef("decathlon1500m",   Type.TRACK, 0.03768,   480.0, 18.5,  "s"));
        m.put("400m",             new EventDef("400m",             Type.TRACK, 1.53775,   82.0,  1.81,  "s"));
        m.put("longJump",         new EventDef("longJump",         Type.FIELD, 0.14354,   220.0, 1.4,   "cm"));
        m.put("decaHighJump",     new EventDef("decaHighJump",     Type.FIELD, 0.8465,    75.0,  1.42,  "cm"));
        m.put("decaPoleVault",    new EventDef("decaPoleVault",    Type.FIELD, 0.2797,    100.0, 1.35,  "cm"));
        m.put("decaDiscusThrow",  new EventDef("decaDiscusThrow",  Type.FIELD, 12.91,     4.0,   1.1,   "m"));
        m.put("decaJavelinThrow", new EventDef("decaJavelinThrow", Type.FIELD, 10.14,     7.0,   1.08,  "m"));
        m.put("shotPut",          new EventDef("shotPut",          Type.FIELD, 51.39,     1.5,   1.05,  "m"));
        m.put("hep200m",          new EventDef("hep200m",          Type.TRACK, 4.99087,   42.5,  1.81,  "s"));
        m.put("hep800m",          new EventDef("hep800m",          Type.TRACK, 0.11193,   254.0, 1.88,  "s"));
        m.put("hep100mHurdles",   new EventDef("hep100mHurdles",   Type.TRACK, 9.23076,   26.7,  18.35, "s"));
        m.put("hepLongJump",      new EventDef("hepLongJump",      Type.FIELD, 0.1888807, 210.0, 1.41,  "cm"));
        m.put("hepHighJump",      new EventDef("hepHighJump",      Type.FIELD, 1.84523,   75.0,  1.348, "cm"));
        m.put("hepShotPut",       new EventDef("hepShotPut",       Type.FIELD, 56.0211,   1.5,   1.05,  "m"));
        m.put("hepJavelinThrow",  new EventDef("hepJavelinThrow",  Type.FIELD, 15.9803,   3.8,   1.04,  "m"));
        return Collections.unmodifiableMap(m);
    }

    public EventDef get(String id) { return events.get(id); }

    public int score(String eventId, double raw) {
        EventDef e = events.get(eventId);
        if (e == null) return 0;
        double points;
        if (e.type == Type.TRACK) {
            double x = e.B - raw;
            if (x <= 0) return 0;
            points = e.A * Math.pow(x, e.C);
        } else {
            double x = raw - e.B;
            if (x <= 0) return 0;
            points = e.A * Math.pow(x, e.C);
        }
        return (int)Math.floor(points);
    }
}