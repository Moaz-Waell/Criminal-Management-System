package analysis;

import MainGUI.CriminalManagementSystemGUI;
import analysis.Analysis;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnalysisMenuGUI {
    private JFrame frame;

    public AnalysisMenuGUI(Analysis analysis) {
        frame = new JFrame("Analysis Menu");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);

        // Create buttons
        JButton prioritizeFemaleFilesButton = new JButton("Prioritize Female Files");
        prioritizeFemaleFilesButton.setBounds(200, 50, 200, 30);
        frame.add(prioritizeFemaleFilesButton);

        JButton sortHearingsButton = new JButton("Sort Hearings by Date");
        sortHearingsButton.setBounds(200, 100, 200, 30);
        frame.add(sortHearingsButton);

        JButton backButton = new JButton("Back to Main Menu");
        backButton.setBounds(200, 150, 200, 30);
        frame.add(backButton);

        // Button Actions
        prioritizeFemaleFilesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayFemaleFiles(analysis);
            }
        });

        sortHearingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displaySortedHearings(analysis);
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the Analysis menu
                new CriminalManagementSystemGUI(); // Return to main menu
            }
        });

        frame.setVisible(true);
    }

    private void displayFemaleFiles(Analysis analysis) {
        JFrame resultFrame = new JFrame("Female Victim Files");
        resultFrame.setSize(800, 600);

        // Column names for JTable
        String[] columnNames = {"Report ID", "Crime Type", "Details", "Victim", "Date"};

        // Get data from Analysis
        String[][] data = analysis.getFemaleVictimFiles(); // Implement this method in Analysis

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        resultFrame.add(scrollPane);
        resultFrame.setVisible(true);
    }

    private void displaySortedHearings(Analysis analysis) {
        JFrame resultFrame = new JFrame("Sorted Hearings by Date");
        resultFrame.setSize(800, 600);

        // Column names for JTable
        String[] columnNames = {"Hearing Date", "Suspect", "Status", "Decision"};

        // Get data from Analysis
        String[][] data = analysis.getSortedHearings(); // Implement this method in Analysis

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        resultFrame.add(scrollPane);
        resultFrame.setVisible(true);
    }
}
