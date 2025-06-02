package Station;

import java.awt.*;
import javax.swing.*;

public class StationMenuGUI extends JFrame {
    private Station station;

    public StationMenuGUI(Station station) {
        this.station = station;
        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("Station Management System");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Title
        JLabel titleLabel = new JLabel("Station Management System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Create buttons
        JButton viewReportsBtn = new JButton("View All Reports");
        JButton addReportBtn = new JButton("Add Report");
        JButton updateReportBtn = new JButton("Update Report");
        JButton viewOfficersBtn = new JButton("View All Officers");
        JButton addOfficerBtn = new JButton("Add Officer");
        JButton removeOfficerBtn = new JButton("Remove Officer");
        JButton updateOfficerBtn = new JButton("Update Officer");
        JButton searchCriminalBtn = new JButton("Search Criminal History");
        JButton backBtn = new JButton("Back to Main Menu");

        // Add action listeners
        viewReportsBtn.addActionListener(e -> station.viewAll("report"));
        addReportBtn.addActionListener(e -> station.add("report"));
        updateReportBtn.addActionListener(e -> station.update("report"));
        viewOfficersBtn.addActionListener(e -> station.viewAll("officer"));
        addOfficerBtn.addActionListener(e -> station.add("officer"));
        removeOfficerBtn.addActionListener(e -> station.remove("officer"));
        updateOfficerBtn.addActionListener(e -> station.update("officer"));
        searchCriminalBtn.addActionListener(e -> station.searchCriminalHistory());
        backBtn.addActionListener(e -> {
            station.saveReports();
            station.saveOfficers();
            station.saveCriminalHistory();
            dispose();
        });

        // Position buttons
        gbc.gridx = 0; gbc.gridy = 0;
        buttonPanel.add(viewReportsBtn, gbc);
        gbc.gridx = 1;
        buttonPanel.add(addReportBtn, gbc);
        gbc.gridx = 2;
        buttonPanel.add(updateReportBtn, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        buttonPanel.add(viewOfficersBtn, gbc);
        gbc.gridx = 1;
        buttonPanel.add(addOfficerBtn, gbc);
        gbc.gridx = 2;
        buttonPanel.add(removeOfficerBtn, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        buttonPanel.add(updateOfficerBtn, gbc);
        gbc.gridx = 1;
        buttonPanel.add(searchCriminalBtn, gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        gbc.gridwidth = 1;
        buttonPanel.add(backBtn, gbc);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        add(mainPanel);
        setVisible(true);
    }
} 