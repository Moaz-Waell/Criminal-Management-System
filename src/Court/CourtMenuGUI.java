package Court;

import Deputy.*;
import Deputy.FilesDeputy;
import Deputy.StatusFile;
import MainGUI.CriminalManagementSystemGUI;
import Persons.Judge;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class CourtMenuGUI {
    private JFrame frame;
    private Deputy deputy; // This makes deputy accessible throughout the class

    public CourtMenuGUI(Court court) {
        frame = new JFrame("Court Menu");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);

        JButton viewHearingsButton = new JButton("View Hearings");
        viewHearingsButton.setBounds(150, 50, 200, 30);
        frame.add(viewHearingsButton);

        JButton scheduleHearingButton = new JButton("Schedule Hearing");
        scheduleHearingButton.setBounds(150, 90, 200, 30);
        frame.add(scheduleHearingButton);

        JButton updateHearingButton = new JButton("Update Hearing");
        updateHearingButton.setBounds(150, 130, 200, 30);
        frame.add(updateHearingButton);

        JButton viewJudgesButton = new JButton("View All Judges");
        viewJudgesButton.setBounds(150, 170, 200, 30); // Example positioning
        frame.add(viewJudgesButton);

        JButton updateJudgeButton = new JButton("Update Judge");
        updateJudgeButton.setBounds(150, 210, 200, 30); // Example positioning
        frame.add(updateJudgeButton);

        JButton addJudgeButton = new JButton("Add Judge");
        addJudgeButton.setBounds(150, 250, 200, 30); // Example positioning
        frame.add(addJudgeButton);
        addJudgeButton.addActionListener(e -> openAddJudgeForm(court));

        JButton removeJudgeButton = new JButton("Remove Judge");
        removeJudgeButton.setBounds(150, 290, 200, 30); // Example positioning
        frame.add(removeJudgeButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(150, 330, 200, 30); // Corrected position
        frame.add(backButton);

        // View Hearings Button ActionListener
        viewHearingsButton.addActionListener(e -> showHearings(court));

        scheduleHearingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame scheduleHearingFrame = new JFrame("Schedule Hearing");
                scheduleHearingFrame.setSize(600, 500);
                scheduleHearingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                scheduleHearingFrame.setLayout(null);

                // Add components
                JLabel dateLabel = new JLabel("Enter Hearing Date (YYYY-MM-DD):");
                dateLabel.setBounds(50, 50, 200, 30);
                scheduleHearingFrame.add(dateLabel);

                JTextField dateField = new JTextField();
                dateField.setBounds(250, 50, 200, 30);
                scheduleHearingFrame.add(dateField);

                JLabel fileLabel = new JLabel("Select File:");
                fileLabel.setBounds(50, 100, 200, 30);
                scheduleHearingFrame.add(fileLabel);

                JComboBox<String> fileComboBox = new JComboBox<>();
                for (FilesDeputy file : court.getFilesDeputy()) {
                    fileComboBox.addItem("File #" + file.getFileNumber() + " - " + file.getReport().getCrimeDetails());
                }
                fileComboBox.setBounds(250, 100, 300, 30);
                scheduleHearingFrame.add(fileComboBox);

                JLabel judgeLabel = new JLabel("Select Judge:");
                judgeLabel.setBounds(50, 150, 200, 30);
                scheduleHearingFrame.add(judgeLabel);

                JComboBox<String> judgeComboBox = new JComboBox<>();
                for (Judge judge : court.getJudges()) {
                    judgeComboBox.addItem(judge.getName() + " (ID: " + judge.getJ_ID() + ")");
                }
                judgeComboBox.setBounds(250, 150, 300, 30);
                scheduleHearingFrame.add(judgeComboBox);

                JButton saveButton = new JButton("Schedule Hearing");
                saveButton.setBounds(200, 250, 150, 30);
                scheduleHearingFrame.add(saveButton);

                // Save button action listener
                saveButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String hearingDateStr = dateField.getText();
                        int selectedFileIndex = fileComboBox.getSelectedIndex();
                        int selectedJudgeIndex = judgeComboBox.getSelectedIndex();

                        if (hearingDateStr.isEmpty() || selectedFileIndex == -1 || selectedJudgeIndex == -1) {
                            JOptionPane.showMessageDialog(scheduleHearingFrame, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        try {
                            LocalDate hearingDate = LocalDate.parse(hearingDateStr);
                            FilesDeputy selectedFile = court.getFilesDeputy().get(selectedFileIndex);
                            Judge selectedJudge = court.getJudges().get(selectedJudgeIndex);

                            // Schedule the hearing
                            Hearing newHearing = new Hearing(hearingDate, selectedJudge, selectedFile);
                            court.getHearings().add(newHearing);

                            JOptionPane.showMessageDialog(scheduleHearingFrame, "Hearing scheduled successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                            scheduleHearingFrame.dispose();
                        } catch (DateTimeParseException ex) {
                            JOptionPane.showMessageDialog(scheduleHearingFrame, "Invalid date format. Use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });

                // Set frame visible
                scheduleHearingFrame.setVisible(true);
            }
        });

        updateHearingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame viewHearingsFrame = new JFrame("Update Hearing");
                viewHearingsFrame.setSize(800, 500);
                viewHearingsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                viewHearingsFrame.setLayout(new BorderLayout());

                String[] columnNames = {"Hearing ID", "Date", "Judge", "File Number", "File Status", "Penalty Duration", "Suspect Status"};
                DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
                JTable hearingsTable = new JTable(tableModel);

                // Populate the table with hearing data
                for (Hearing hearing : court.getHearings()) {
                    tableModel.addRow(new Object[]{
                            hearing.getHearingId(),
                            hearing.getHearingDate(),
                            hearing.getJudge().getName(),
                            hearing.getFiles().getFileNumber(),
                            hearing.getFiles().getStatus(),
                            hearing.getPenaltyDuration(),
                            hearing.getSuspectStatus()
                    });
                }

                JScrollPane scrollPane = new JScrollPane(hearingsTable);
                viewHearingsFrame.add(scrollPane, BorderLayout.CENTER);

                JButton updateButton = new JButton("Update Selected Hearing");
                viewHearingsFrame.add(updateButton, BorderLayout.SOUTH);

                // Update button action
                updateButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int selectedRow = hearingsTable.getSelectedRow();
                        if (selectedRow == -1) {
                            JOptionPane.showMessageDialog(viewHearingsFrame, "Please select a hearing to update.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        // Get the selected hearing
                        Hearing selectedHearing = court.getHearings().get(selectedRow);
                        openUpdateHearingForm(court, selectedHearing, viewHearingsFrame);
                    }
                });

                viewHearingsFrame.setVisible(true);
            }
        });

        viewJudgesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame viewJudgesFrame = new JFrame("View All Judges");
                viewJudgesFrame.setSize(800, 500);
                viewJudgesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                viewJudgesFrame.setLayout(new BorderLayout());

                String[] columnNames = {"Judge ID", "Name", "SSN", "Address", "Age", "Gender", "Phone"};
                DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
                JTable judgesTable = new JTable(tableModel);

                // Populate the table with judge data
                for (Judge judge : court.getJudges()) {
                    tableModel.addRow(new Object[]{
                            judge.getJ_ID(),
                            judge.getName(),
                            judge.getSSN(),
                            judge.getAddress(),
                            judge.getAge(),
                            judge.getGender(),
                            judge.getPhone()
                    });
                }

                JScrollPane scrollPane = new JScrollPane(judgesTable);
                viewJudgesFrame.add(scrollPane, BorderLayout.CENTER);

                JButton closeButton = new JButton("Close");
                closeButton.addActionListener(e1 -> viewJudgesFrame.dispose());
                viewJudgesFrame.add(closeButton, BorderLayout.SOUTH);

                viewJudgesFrame.setVisible(true);
            }
        });

        updateJudgeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame viewJudgesFrame = new JFrame("Update Judge");
                viewJudgesFrame.setSize(800, 500);
                viewJudgesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                viewJudgesFrame.setLayout(new BorderLayout());

                String[] columnNames = {"Judge ID", "Name", "SSN", "Address", "Age", "Gender", "Phone"};
                DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
                JTable judgesTable = new JTable(tableModel);

                // Populate the table with judge data
                for (Judge judge : court.getJudges()) {
                    tableModel.addRow(new Object[]{
                            judge.getJ_ID(),
                            judge.getName(),
                            judge.getSSN(),
                            judge.getAddress(),
                            judge.getAge(),
                            judge.getGender(),
                            judge.getPhone()
                    });
                }

                JScrollPane scrollPane = new JScrollPane(judgesTable);
                viewJudgesFrame.add(scrollPane, BorderLayout.CENTER);

                JButton updateButton = new JButton("Update Selected Judge");
                viewJudgesFrame.add(updateButton, BorderLayout.SOUTH);

                // Update button action
                updateButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int selectedRow = judgesTable.getSelectedRow();
                        if (selectedRow == -1) {
                            JOptionPane.showMessageDialog(viewJudgesFrame, "Please select a judge to update.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        // Get the selected judge
                        Judge selectedJudge = court.getJudges().get(selectedRow);
                        openUpdateJudgeForm(court, selectedJudge, viewJudgesFrame);
                    }
                });

                viewJudgesFrame.setVisible(true);
            }
        });

        removeJudgeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame removeJudgeFrame = new JFrame("Remove Judge");
                removeJudgeFrame.setSize(800, 500);
                removeJudgeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                removeJudgeFrame.setLayout(new BorderLayout());

                String[] columnNames = {"Judge ID", "Name", "SSN", "Address", "Age", "Gender", "Phone"};
                DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
                JTable judgesTable = new JTable(tableModel);

                // Populate the table with judge data
                for (Judge judge : court.getJudges()) {
                    tableModel.addRow(new Object[]{
                            judge.getJ_ID(),
                            judge.getName(),
                            judge.getSSN(),
                            judge.getAddress(),
                            judge.getAge(),
                            judge.getGender(),
                            judge.getPhone()
                    });
                }

                JScrollPane scrollPane = new JScrollPane(judgesTable);
                removeJudgeFrame.add(scrollPane, BorderLayout.CENTER);

                JButton removeButton = new JButton("Remove Selected Judge");
                removeJudgeFrame.add(removeButton, BorderLayout.SOUTH);

                // Remove button action
                removeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int selectedRow = judgesTable.getSelectedRow();
                        if (selectedRow == -1) {
                            JOptionPane.showMessageDialog(removeJudgeFrame, "Please select a judge to remove.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        // Confirm removal
                        int confirm = JOptionPane.showConfirmDialog(
                                removeJudgeFrame,
                                "Are you sure you want to remove this judge?",
                                "Confirm Removal",
                                JOptionPane.YES_NO_OPTION
                        );

                        if (confirm == JOptionPane.YES_OPTION) {
                            // Remove the selected judge
                            court.getJudges().remove(selectedRow);
                            tableModel.removeRow(selectedRow); // Update table view
                            JOptionPane.showMessageDialog(removeJudgeFrame, "Judge removed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                });

                removeJudgeFrame.setVisible(true);
            }
        });


        // Back Button ActionListener
        backButton.addActionListener(e -> {
            frame.dispose();
            new CriminalManagementSystemGUI();
        });

        frame.setVisible(true);
    }

    private void showHearings(Court court) {
        JFrame hearingsFrame = new JFrame("View Hearings");
        hearingsFrame.setSize(800, 500);
        hearingsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        hearingsFrame.setLayout(new BorderLayout());

        String[] columnNames = {"Hearing ID", "Hearing Date", "Judge", "File Number", "File Status", "Hearing Decision", "Suspect Status", "Penalty Duration"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable hearingsTable = new JTable(tableModel);

        if (court.getHearings() != null && !court.getHearings().isEmpty()) {
            for (Hearing hearing : court.getHearings()) {
                tableModel.addRow(new Object[]{
                        hearing.getHearingId(),
                        hearing.getHearingDate(),
                        hearing.getJudge() != null ? hearing.getJudge().getName() : "N/A",
                        hearing.getFiles() != null ? hearing.getFiles().getFileNumber() : "N/A",
                        hearing.getFiles() != null && hearing.getFiles().getStatus() != null
                                ? hearing.getFiles().getStatus().toString() : "N/A",
                        hearing.getFiles() != null ? hearing.getFiles().getHearingDecision() : "N/A",
                        hearing.getSuspectStatus(),
                        hearing.getPenaltyDuration()
                });
            }
        } else {
            JOptionPane.showMessageDialog(hearingsFrame, "No hearings to display.", "Information", JOptionPane.INFORMATION_MESSAGE);
        }

        JScrollPane scrollPane = new JScrollPane(hearingsTable);
        hearingsFrame.add(scrollPane, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> hearingsFrame.dispose());
        hearingsFrame.add(closeButton, BorderLayout.SOUTH);

        hearingsFrame.setVisible(true);
    }

    private void openUpdateHearingForm(Court court, Hearing hearing, JFrame parentFrame) {
        parentFrame.dispose(); // Close the previous frame

        JFrame formFrame = new JFrame("Update Hearing");
        formFrame.setSize(800, 600);
        formFrame.setLayout(null);

        JLabel dateLabel = new JLabel("Hearing Date:");
        dateLabel.setBounds(50, 50, 150, 30);
        formFrame.add(dateLabel);

        JTextField dateField = new JTextField(hearing.getHearingDate().toString());
        dateField.setBounds(200, 50, 200, 30);
        formFrame.add(dateField);

        JLabel judgeLabel = new JLabel("Judge:");
        judgeLabel.setBounds(50, 100, 150, 30);
        formFrame.add(judgeLabel);

        JComboBox<String> judgeComboBox = new JComboBox<>();
        for (Judge judge : court.getJudges()) {
            judgeComboBox.addItem(judge.getName() + " (ID: " + judge.getJ_ID() + ")");
        }
        judgeComboBox.setSelectedItem(hearing.getJudge().getName() + " (ID: " + hearing.getJudge().getJ_ID() + ")");
        judgeComboBox.setBounds(200, 100, 300, 30);
        formFrame.add(judgeComboBox);

        JLabel fileStatusLabel = new JLabel("File Status:");
        fileStatusLabel.setBounds(50, 150, 150, 30);
        formFrame.add(fileStatusLabel);

        JComboBox<StatusFile> fileStatusComboBox = new JComboBox<>(StatusFile.values());
        fileStatusComboBox.setSelectedItem(hearing.getFiles().getStatus());
        fileStatusComboBox.setBounds(200, 150, 200, 30);
        formFrame.add(fileStatusComboBox);

        JLabel penaltyLabel = new JLabel("Penalty Duration:");
        penaltyLabel.setBounds(50, 200, 150, 30);
        formFrame.add(penaltyLabel);

        JTextField penaltyField = new JTextField(hearing.getPenaltyDuration());
        penaltyField.setBounds(200, 200, 200, 30);
        formFrame.add(penaltyField);

        JLabel suspectStatusLabel = new JLabel("Suspect Status:");
        suspectStatusLabel.setBounds(50, 250, 150, 30);
        formFrame.add(suspectStatusLabel);

        JComboBox<SuspectStatus> suspectStatusComboBox = new JComboBox<>(SuspectStatus.values());
        suspectStatusComboBox.setSelectedItem(hearing.getSuspectStatus());
        suspectStatusComboBox.setBounds(200, 250, 200, 30);
        formFrame.add(suspectStatusComboBox);

        JButton saveButton = new JButton("Save Changes");
        saveButton.setBounds(300, 350, 150, 30);
        formFrame.add(saveButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Update hearing details
                    hearing.setHearingDate(LocalDate.parse(dateField.getText()));
                    hearing.setJudge(court.getJudges().get(judgeComboBox.getSelectedIndex()));
                    hearing.getFiles().setStatus((StatusFile) fileStatusComboBox.getSelectedItem());
                    hearing.setPenaltyDuration(penaltyField.getText());
                    hearing.setSuspectStatus((SuspectStatus) suspectStatusComboBox.getSelectedItem());

                    JOptionPane.showMessageDialog(formFrame, "Hearing updated successfully!");
                    formFrame.dispose();
                    new CourtMenuGUI(court); // Return to court menu
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(formFrame, "Invalid date format. Use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        formFrame.setVisible(true);
    }

    private void openUpdateJudgeForm(Court court, Judge judge, JFrame parentFrame) {
        parentFrame.dispose(); // Close the previous frame

        JFrame formFrame = new JFrame("Update Judge");
        formFrame.setSize(600, 500);
        formFrame.setLayout(null);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 50, 100, 30);
        formFrame.add(nameLabel);

        JTextField nameField = new JTextField(judge.getName());
        nameField.setBounds(150, 50, 200, 30);
        formFrame.add(nameField);

        JLabel ssnLabel = new JLabel("SSN:");
        ssnLabel.setBounds(50, 100, 100, 30);
        formFrame.add(ssnLabel);

        JTextField ssnField = new JTextField(judge.getSSN());
        ssnField.setBounds(150, 100, 200, 30);
        formFrame.add(ssnField);

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(50, 150, 100, 30);
        formFrame.add(addressLabel);

        JTextField addressField = new JTextField(judge.getAddress());
        addressField.setBounds(150, 150, 200, 30);
        formFrame.add(addressField);

        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setBounds(50, 200, 100, 30);
        formFrame.add(ageLabel);

        JTextField ageField = new JTextField(String.valueOf(judge.getAge()));
        ageField.setBounds(150, 200, 200, 30);
        formFrame.add(ageField);

        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(50, 250, 100, 30);
        formFrame.add(genderLabel);

        JTextField genderField = new JTextField(judge.getGender());
        genderField.setBounds(150, 250, 200, 30);
        formFrame.add(genderField);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(50, 300, 100, 30);
        formFrame.add(phoneLabel);

        JTextField phoneField = new JTextField(judge.getPhone());
        phoneField.setBounds(150, 300, 200, 30);
        formFrame.add(phoneField);

        JButton saveButton = new JButton("Save Changes");
        saveButton.setBounds(200, 400, 150, 30);
        formFrame.add(saveButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Update judge details
                    judge.setName(nameField.getText());
                    judge.setSSN(ssnField.getText());
                    judge.setAddress(addressField.getText());
                    judge.setAge(Integer.parseInt(ageField.getText()));
                    judge.setGender(genderField.getText());
                    judge.setPhone(phoneField.getText());

                    JOptionPane.showMessageDialog(formFrame, "Judge updated successfully!");
                    formFrame.dispose();
                    new CourtMenuGUI(court); // Return to court menu
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(formFrame, "Invalid age. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        formFrame.setVisible(true);
    }


    private void openAddJudgeForm(Court court) {
        JFrame formFrame = new JFrame("Add Judge");
        formFrame.setSize(400, 400);
        formFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        formFrame.setLayout(null);

        // Label and input fields for judge details
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 50, 100, 30);
        formFrame.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(150, 50, 200, 30);
        formFrame.add(nameField);

        JLabel ssnLabel = new JLabel("SSN:");
        ssnLabel.setBounds(50, 100, 100, 30);
        formFrame.add(ssnLabel);

        JTextField ssnField = new JTextField();
        ssnField.setBounds(150, 100, 200, 30);
        formFrame.add(ssnField);

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(50, 150, 100, 30);
        formFrame.add(addressLabel);

        JTextField addressField = new JTextField();
        addressField.setBounds(150, 150, 200, 30);
        formFrame.add(addressField);

        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setBounds(50, 200, 100, 30);
        formFrame.add(ageLabel);

        JTextField ageField = new JTextField();
        ageField.setBounds(150, 200, 200, 30);
        formFrame.add(ageField);

        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(50, 250, 100, 30);
        formFrame.add(genderLabel);

        JComboBox<String> genderField = new JComboBox<>(new String[]{"Male", "Female"});
        genderField.setBounds(150, 250, 200, 30);
        formFrame.add(genderField);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(50, 300, 100, 30);
        formFrame.add(phoneLabel);

        JTextField phoneField = new JTextField();
        phoneField.setBounds(150, 300, 200, 30);
        formFrame.add(phoneField);

        // Save Button
        JButton saveButton = new JButton("Add Judge");
        saveButton.setBounds(150, 350, 100, 30);
        formFrame.add(saveButton);

        saveButton.addActionListener(e -> {
            try {
                // Create new Judge object
                Judge newJudge = new Judge(
                        nameField.getText(),
                        ssnField.getText(),
                        addressField.getText(),
                        Integer.parseInt(ageField.getText()),
                        genderField.getSelectedItem().toString(),
                        phoneField.getText()
                );

                // Add judge to court
                court.addJudge(newJudge); // Ensure this method exists in the Court class
                JOptionPane.showMessageDialog(formFrame, "Judge added successfully!");
                formFrame.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(formFrame, "Error adding judge: " + ex.getMessage());
            }
        });

        formFrame.setLocationRelativeTo(null);
        formFrame.setVisible(true);
    }


}
