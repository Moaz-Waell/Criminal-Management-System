package MainGUI;

import Court.CourtMenuGUI;
import Deputy.DeputyMenuGUI;
import Station.StationMenuGUI;
import Station.*;
import Deputy.*;
import Court.*;
import analysis.*;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CriminalManagementSystemGUI {
    private JFrame frame;
    private Station station;
    private Deputy deputy;
    private Court court;
    private Analysis analysis;

    public CriminalManagementSystemGUI() {
        // Initialize modules
        this.station = new Station();
        this.deputy = new Deputy();
        this.court = new Court();
        this.analysis = new Analysis();

        // Initialize main frame
        frame = new JFrame("Criminal Management System");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Create buttons
        JButton stationButton = new JButton("Station");
        stationButton.setBounds(150, 50, 200, 30);
        frame.add(stationButton);

        JButton deputyButton = new JButton("Deputy");
        deputyButton.setBounds(150, 90, 200, 30);
        frame.add(deputyButton);

        JButton courtButton = new JButton("Court");
        courtButton.setBounds(150, 130, 200, 30);
        frame.add(courtButton);

        JButton analysisButton = new JButton("analysis");
        analysisButton.setBounds(150, 170, 200, 30);
        frame.add(analysisButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(150, 210, 200, 30);
        frame.add(exitButton);

        // Button Actions
        stationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the main menu
                new StationMenuGUI(station); // Open the Station menu
            }
        });

        deputyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the main menu
                new DeputyMenuGUI(deputy); // Open the Deputy menu
            }
        });

        courtButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the main menu
                new CourtMenuGUI(court); // Open the Court menu
            }
        });

        analysisButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the main menu
                new AnalysisMenuGUI(analysis); // Open the Analysis.Analysis menu
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Exit the program
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new CriminalManagementSystemGUI();
    }
}