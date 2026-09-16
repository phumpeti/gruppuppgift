package com.example.decathlon.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.example.decathlon.deca.*;
import com.example.decathlon.heptathlon.*;

public class MainGUI {

    private JTextField nameField;
    private JTextField resultField;
    private JLabel unitLabel;
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

    private static final Map<String, String> DISCIPLINE_UNITS = new LinkedHashMap<>();
    static {
        DISCIPLINE_UNITS.put("Decathlon 100m", "s");
        DISCIPLINE_UNITS.put("Decathlon 400m", "s");
        DISCIPLINE_UNITS.put("Decathlon 1500m", "min");
        DISCIPLINE_UNITS.put("Decathlon 110m Hurdles", "s");
        DISCIPLINE_UNITS.put("Decathlon Long Jump", "cm");
        DISCIPLINE_UNITS.put("Decathlon High Jump", "cm");
        DISCIPLINE_UNITS.put("Decathlon Pole Vault", "cm");
        DISCIPLINE_UNITS.put("Decathlon Discus Throw", "m");
        DISCIPLINE_UNITS.put("Decathlon Javelin Throw", "m");
        DISCIPLINE_UNITS.put("Decathlon Shot Put", "m");
        DISCIPLINE_UNITS.put("Heptathlon 200m", "s");
        DISCIPLINE_UNITS.put("Heptathlon 800m", "s");
        DISCIPLINE_UNITS.put("Heptathlon 100m Hurdles", "s");
        DISCIPLINE_UNITS.put("Heptathlon Long Jump", "m");
        DISCIPLINE_UNITS.put("Heptathlon High Jump", "cm");
        DISCIPLINE_UNITS.put("Heptathlon Shot Put", "m");
        DISCIPLINE_UNITS.put("Heptathlon Javelin Throw", "m");
    }

    public static void main(String[] args) {
        new MainGUI().createAndShowGUI();
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Track and Field Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(950, 520);
        frame.setLayout(new BorderLayout());

        JPanel formPanel = buildFormPanel();

        tableModel = new DefaultTableModel(new Object[]{"Name", "Total", "Standing"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        resultsTable = new JTable(tableModel);
        resultsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        resultsTable.setRowHeight(24);
        JScrollPane tableScrollPane = new JScrollPane(resultsTable);
        tableScrollPane.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(0, 15, 15, 15),
                BorderFactory.createTitledBorder("Results")));

        frame.add(formPanel, BorderLayout.NORTH);
        frame.add(tableScrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private JPanel buildFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(15, 15, 10, 15));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0;
        c.gridy = 0;
        formPanel.add(new JLabel("Competitor name:"), c);

        nameField = new JTextField(18);
        c.gridx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        formPanel.add(nameField, c);

        JButton addCompetitorButton = new JButton("Add competitor");
        addCompetitorButton.addActionListener(e -> addCompetitor());
        c.gridx = 2;
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0;
        formPanel.add(addCompetitorButton, c);

        c.gridx = 0;
        c.gridy = 1;
        formPanel.add(new JLabel("Discipline:"), c);

        disciplineBox = new JComboBox<>(DISCIPLINES);
        disciplineBox.addActionListener(e -> updateUnitLabel());
        c.gridx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        formPanel.add(disciplineBox, c);

        unitLabel = new JLabel();
        c.gridx = 2;
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0;
        formPanel.add(unitLabel, c);

        c.gridx = 0;
        c.gridy = 2;
        formPanel.add(new JLabel("Result:"), c);

        resultField = new JTextField(18);
        c.gridx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        formPanel.add(resultField, c);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        JButton calculateButton = new JButton("Calculate Score");
        calculateButton.addActionListener(e -> calculateScoreForCurrentInput());
        JButton renameButton = new JButton("Rename competitor");
        renameButton.addActionListener(e -> renameSelectedCompetitor());
        JButton exportButton = new JButton("Export");
        exportButton.addActionListener(e -> exportResults());
        buttonsPanel.add(calculateButton);
        buttonsPanel.add(renameButton);
        buttonsPanel.add(exportButton);

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 3;
        c.fill = GridBagConstraints.NONE;
        formPanel.add(buttonsPanel, c);

        updateUnitLabel();

        return formPanel;
    }

    private void updateUnitLabel() {
        String discipline = (String) disciplineBox.getSelectedItem();
        String unit = DISCIPLINE_UNITS.getOrDefault(discipline, "");
        unitLabel.setText(unit.isEmpty() ? "" : "Unit: " + unit);
    }

    private void addCompetitor() {
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

    private void calculateScoreForCurrentInput() {
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

    private void renameSelectedCompetitor() {
        int selectedRow = resultsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null,
                    "Select a competitor in the table first.",
                    "No Competitor Selected", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String oldName = tableModel.getValueAt(selectedRow, 0).toString();
        String newName = JOptionPane.showInputDialog(null, "New name:", oldName);

        if (newName == null) {
            return;
        }

        newName = newName.trim();

        if (newName.isEmpty() || !newName.matches(".*[a-zA-Z].*")) {
            JOptionPane.showMessageDialog(null,
                    "Please enter a name that contains letters.",
                    "Invalid Name", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (newName.equals(oldName)) {
            return;
        }

        if (competitors.containsKey(newName)) {
            JOptionPane.showMessageDialog(null,
                    "Competitor names must be unique.",
                    "Duplicate Name", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Map<String, Map<String, Integer>> renamed = new LinkedHashMap<>();
        for (Map.Entry<String, Map<String, Integer>> entry : competitors.entrySet()) {
            String key = entry.getKey().equals(oldName) ? newName : entry.getKey();
            renamed.put(key, entry.getValue());
        }
        competitors.clear();
        competitors.putAll(renamed);

        refreshTable();
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

    private void exportResults() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File("results.csv"));
        int choice = fileChooser.showSaveDialog(null);
        if (choice != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File file = fileChooser.getSelectedFile();
        if (!file.getName().toLowerCase().endsWith(".csv")) {
            file = new File(file.getParentFile(), file.getName() + ".csv");
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            int columnCount = tableModel.getColumnCount();
            StringBuilder header = new StringBuilder();
            for (int col = 0; col < columnCount; col++) {
                if (col > 0) {
                    header.append(",");
                }
                header.append(tableModel.getColumnName(col));
            }
            writer.println(header);

            for (int row = 0; row < tableModel.getRowCount(); row++) {
                StringBuilder line = new StringBuilder();
                for (int col = 0; col < columnCount; col++) {
                    if (col > 0) {
                        line.append(",");
                    }
                    Object value = tableModel.getValueAt(row, col);
                    line.append(value == null ? "" : value.toString());
                }
                writer.println(line);
            }

            JOptionPane.showMessageDialog(null, "Export complete: " + file.getAbsolutePath(), "Export", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Could not export results: " + ex.getMessage(), "Export Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}
