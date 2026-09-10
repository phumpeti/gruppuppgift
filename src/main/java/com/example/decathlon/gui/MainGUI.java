package com.example.decathlon.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.example.decathlon.deca.*;
import com.example.decathlon.heptathlon.*;

public class MainGUI {

    private JTextField nameField;
    private JTextField resultField;
    private JComboBox<String> disciplineBox;
    private JTable resultsTable;
    private DefaultTableModel tableModel;

    private final Map<String, Map<String, Integer>> competitors = new LinkedHashMap<>();

    private static final String[] DISCIPLINES = {
            "Decathlon 100m",
            "Decathlon 400m",
            "Decathlon 1500m",
            "Decathlon 110m Hurdles",
            "Decathlon Long Jump",
            "Decathlon High Jump",
            "Decathlon Pole Vault",
            "Decathlon Discus Throw",
            "Decathlon Javelin Throw",
            "Decathlon Shot Put",
            "Heptathlon 200m",
            "Heptathlon 800m",
            "Heptathlon 100m Hurdles",
            "Heptathlon Long Jump",
            "Heptathlon High Jump",
            "Heptathlon Shot Put",
            "Heptathlon Javelin Throw"
    };

    public static void main(String[] args) {
        new MainGUI().createAndShowGUI();
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Track and Field Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 480);
        frame.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(4, 1));

        nameField = new JTextField(20);
        JPanel namePanel = new JPanel(new BorderLayout());
        namePanel.add(new JLabel("Enter Competitor's Name:"), BorderLayout.WEST);
        namePanel.add(nameField, BorderLayout.CENTER);
        JButton addCompetitorButton = new JButton("Add competitor");
        addCompetitorButton.addActionListener(new AddCompetitorButtonListener());
        namePanel.add(addCompetitorButton, BorderLayout.EAST);
        formPanel.add(namePanel);

        disciplineBox = new JComboBox<>(DISCIPLINES);
        JPanel disciplinePanel = new JPanel(new BorderLayout());
        disciplinePanel.add(new JLabel("Select Discipline:"), BorderLayout.WEST);
        disciplinePanel.add(disciplineBox, BorderLayout.CENTER);
        formPanel.add(disciplinePanel);

        resultField = new JTextField(10);
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.add(new JLabel("Enter Result:"), BorderLayout.WEST);
        resultPanel.add(resultField, BorderLayout.CENTER);
        formPanel.add(resultPanel);

        JButton calculateButton = new JButton("Calculate Score");
        calculateButton.addActionListener(new CalculateButtonListener());
        formPanel.add(calculateButton);

        tableModel = new DefaultTableModel(new Object[]{"Name", "Total", "Standing"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        resultsTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(resultsTable);

        frame.add(formPanel, BorderLayout.NORTH);
        frame.add(tableScrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private class AddCompetitorButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText().trim();

            if (name.isEmpty() || !name.matches(".*[a-zA-Z].*")) {
                JOptionPane.showMessageDialog(null,
                        "Please enter a name that contains letters.",
                        "Invalid Name", JOptionPane.ERROR_MESSAGE);
                return;
            }

            competitors.computeIfAbsent(name, n -> new LinkedHashMap<>());
            refreshTable();
        }
    }

    private class CalculateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText().trim();
            String discipline = (String) disciplineBox.getSelectedItem();
            String resultText = resultField.getText();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Please enter a competitor's name.",
                        "Missing Name", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                double result = Double.parseDouble(resultText);
                int score = calculateScore(discipline, result);

                Map<String, Integer> scores = competitors.computeIfAbsent(name, n -> new LinkedHashMap<>());
                scores.put(discipline, score);

                refreshTable();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number for the result.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Invalid Result", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private int calculateScore(String discipline, double result) {
        switch (discipline) {
            case "Decathlon 100m":
                return new Deca100M().calculateResult(result);
            case "Decathlon 400m":
                return new Deca400M().calculateResult(result);
            case "Decathlon 1500m":
                return new Deca1500M().calculateResult(result);
            case "Decathlon 110m Hurdles":
                return new Deca110MHurdles().calculateResult(result);
            case "Decathlon Long Jump":
                return new DecaLongJump().calculateResult(result);
            case "Decathlon High Jump":
                return new DecaHighJump().calculateResult(result);
            case "Decathlon Pole Vault":
                return new DecaPoleVault().calculateResult(result);
            case "Decathlon Discus Throw":
                return new DecaDiscusThrow().calculateResult(result);
            case "Decathlon Javelin Throw":
                return new DecaJavelinThrow().calculateResult(result);
            case "Decathlon Shot Put":
                return new DecaShotPut().calculateResult(result);
            case "Heptathlon 200m":
                return new Hep200M().calculateResult(result);
            case "Heptathlon 800m":
                return new Hep800M().calculateResult(result);
            case "Heptathlon 100m Hurdles":
                return new Hep100MHurdles().calculateResult(result);
            case "Heptathlon Long Jump":
                return new HeptLongJump().calculateResult(result);
            case "Heptathlon High Jump":
                return new HeptHightJump().calculateResult(result);
            case "Heptathlon Shot Put":
                return new HeptShotPut().calculateResult(result);
            case "Heptathlon Javelin Throw":
                return new HeptJavelinThrow().calculateResult(result);
            default:
                throw new IllegalArgumentException("Unknown discipline: " + discipline);
        }
    }

    private void refreshTable() {
        Set<String> usedDisciplines = new LinkedHashSet<>();
        for (String discipline : DISCIPLINES) {
            for (Map<String, Integer> scores : competitors.values()) {
                if (scores.containsKey(discipline)) {
                    usedDisciplines.add(discipline);
                    break;
                }
            }
        }

        Object[] columnNames = new Object[1 + usedDisciplines.size() + 2];
        columnNames[0] = "Name";
        int col = 1;
        for (String discipline : usedDisciplines) {
            columnNames[col++] = discipline;
        }
        columnNames[col++] = "Total";
        columnNames[col] = "Standing";

        Map<String, Integer> totals = new LinkedHashMap<>();
        for (Map.Entry<String, Map<String, Integer>> entry : competitors.entrySet()) {
            int total = entry.getValue().values().stream().mapToInt(Integer::intValue).sum();
            totals.put(entry.getKey(), total);
        }

        Object[][] rows = new Object[competitors.size()][columnNames.length];
        int row = 0;
        for (Map.Entry<String, Map<String, Integer>> entry : competitors.entrySet()) {
            String name = entry.getKey();
            Map<String, Integer> scores = entry.getValue();
            Object[] rowData = new Object[columnNames.length];
            rowData[0] = name;
            col = 1;
            for (String discipline : usedDisciplines) {
                rowData[col++] = scores.get(discipline);
            }
            int total = totals.get(name);
            rowData[col++] = total;
            rowData[col] = calculateStanding(name, totals);
            rows[row++] = rowData;
        }

        tableModel.setDataVector(rows, columnNames);
    }

    private int calculateStanding(String name, Map<String, Integer> totals) {
        int myTotal = totals.get(name);
        long higher = totals.values().stream().filter(t -> t > myTotal).count();
        return (int) higher + 1;
    }
}
