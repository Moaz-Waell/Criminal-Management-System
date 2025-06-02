package Deputy;

import MainGUI.CriminalManagementSystemGUI;

import Persons.Lawyer;
import Persons.Procurator;
import Station.Report;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class DeputyMenuGUI {
    private JFrame frame;

    public DeputyMenuGUI(Deputy deputy) {
        frame = new JFrame("Deputy Menu");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(1, 3, 10, 10)); // 1 row, 3 columns with spacing

        // Panel for Column 1 (View Buttons)
        JPanel column1 = new JPanel(new GridLayout(0, 1, 5, 5));
        JButton viewFilesButton = new JButton("View Files");
        column1.add(viewFilesButton);
        JButton viewProcuratorsButton = new JButton("View All Procurators");
        column1.add(viewProcuratorsButton);
        JButton viewLawyersButton = new JButton("View All Lawyers");
        column1.add(viewLawyersButton);
        JButton viewArchivedFilesButton = new JButton("View All Archived Files");
        column1.add(viewArchivedFilesButton);

        // Panel for Column 2 (Add and Create Buttons)
        JPanel column2 = new JPanel(new GridLayout(0, 1, 5, 5));
        JButton addProcuratorButton = new JButton("Add Procurator");
        column2.add(addProcuratorButton);
        JButton addLawyerButton = new JButton("Add Lawyer");
        column2.add(addLawyerButton);
        JButton createFileButton = new JButton("Create File");
        column2.add(createFileButton);

        // Panel for Column 3 (Update and Remove Buttons)
        JPanel column3 = new JPanel(new GridLayout(0, 1, 5, 5));
        JButton updateProcuratorButton = new JButton("Update Procurator");
        column3.add(updateProcuratorButton);
        JButton updateLawyerButton = new JButton("Update Lawyer");
        column3.add(updateLawyerButton);
        JButton updateFileButton = new JButton("Update File");
        column3.add(updateFileButton);
        JButton removeProcuratorButton = new JButton("Remove Procurator");
        column3.add(removeProcuratorButton);
        JButton removeLawyerButton = new JButton("Remove Lawyer");
        column3.add(removeLawyerButton);
        JButton archiveFilesButton = new JButton("Archive Files");
        column3.add(archiveFilesButton);

        JButton backButton = new JButton("Back");
        column3.add(backButton);

        // Add panels to frame
        frame.add(column1);
        frame.add(column2);
        frame.add(column3);


        updateProcuratorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame viewProcuratorsFrame = new JFrame("View All Procurators");
                viewProcuratorsFrame.setSize(800, 500);
                viewProcuratorsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                viewProcuratorsFrame.setLayout(new BorderLayout());

                String[] columnNames = {"Name", "SSN", "Address", "Age", "Gender", "Phone", "License Number"};
                DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
                JTable procuratorsTable = new JTable(tableModel);

                // Populate table
                for (Procurator procurator : deputy.getProcurators()) {
                    tableModel.addRow(new Object[]{
                            procurator.getName(),
                            procurator.getSSN(),
                            procurator.getAddress(),
                            procurator.getAge(),
                            procurator.getGender(),
                            procurator.getPhone(),
                            procurator.getLicenseNumber()
                    });
                }

                JScrollPane scrollPane = new JScrollPane(procuratorsTable);
                viewProcuratorsFrame.add(scrollPane, BorderLayout.CENTER);

                JButton updateButton = new JButton("Update Selected Procurator");
                viewProcuratorsFrame.add(updateButton, BorderLayout.SOUTH);

                // Update button action
                updateButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int selectedRow = procuratorsTable.getSelectedRow();
                        if (selectedRow == -1) {
                            JOptionPane.showMessageDialog(viewProcuratorsFrame, "Please select a procurator to update.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        Procurator selectedProcurator = deputy.getProcurators().get(selectedRow);
                        openUpdateProcuratorForm(deputy, selectedProcurator, viewProcuratorsFrame);
                    }
                });

                viewProcuratorsFrame.setVisible(true);
            }
        });
        updateLawyerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame viewLawyersFrame = new JFrame("View All Lawyers");
                viewLawyersFrame.setSize(800, 500);
                viewLawyersFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                viewLawyersFrame.setLayout(new BorderLayout());

                String[] columnNames = {"Name", "SSN", "Address", "Age", "Gender", "Phone", "Lawyer ID"};
                DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
                JTable lawyersTable = new JTable(tableModel);

                // Populate the table with lawyer data
                for (Lawyer lawyer : deputy.getLawyers()) {
                    tableModel.addRow(new Object[]{
                            lawyer.getName(),
                            lawyer.getSSN(),
                            lawyer.getAddress(),
                            lawyer.getAge(),
                            lawyer.getGender(),
                            lawyer.getPhone(),
                            lawyer.getLawyerId()
                    });
                }

                JScrollPane scrollPane = new JScrollPane(lawyersTable);
                viewLawyersFrame.add(scrollPane, BorderLayout.CENTER);

                JButton updateButton = new JButton("Update Selected Lawyer");
                viewLawyersFrame.add(updateButton, BorderLayout.SOUTH);

                // Update button action
                updateButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int selectedRow = lawyersTable.getSelectedRow();
                        if (selectedRow == -1) {
                            JOptionPane.showMessageDialog(viewLawyersFrame, "Please select a lawyer to update.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        // Get selected lawyer details
                        Lawyer selectedLawyer = deputy.getLawyers().get(selectedRow);
                        openUpdateLawyerForm(deputy, selectedLawyer, viewLawyersFrame);
                    }
                });

                viewLawyersFrame.setVisible(true);
            }
        });

        viewFilesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame filesFrame = new JFrame("View Files");
                filesFrame.setSize(800, 500);
                filesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                filesFrame.setLayout(new BorderLayout());

                // Table to display files
                String[] columnNames = {"File Number", "Status", "Opened Date", "Closed Date", "Report Details", "Procurator", "Suspect Lawyer", "Victim Lawyer"};
                DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
                JTable filesTable = new JTable(tableModel);

                // Fetch data from the deputy's files
                if (deputy != null && !deputy.getFilesDeputy().isEmpty()) {
                    for (FilesDeputy file : deputy.getFilesDeputy()) {
                        Object[] rowData = {
                                file.getFileNumber(),
                                file.getStatus() != null ? file.getStatus().toString() : "N/A",
                                file.getFileOpenedDate(),
                                file.getFileClosedDate(),
                                file.getReport() != null ? file.getReport().toString() : "N/A",
                                file.getProcurator() != null ? file.getProcurator().getName() : "N/A",
                                file.getSuspectLawyer() != null ? file.getSuspectLawyer().getName() : "N/A",
                                file.getVictimLawyer() != null ? file.getVictimLawyer().getName() : "N/A"
                        };
                        tableModel.addRow(rowData);
                    }
                } else {
                    JOptionPane.showMessageDialog(filesFrame, "No files available to display.", "Information", JOptionPane.INFORMATION_MESSAGE);
                }

                JScrollPane scrollPane = new JScrollPane(filesTable);
                filesFrame.add(scrollPane, BorderLayout.CENTER);

                JButton closeButton = new JButton("Close");
                closeButton.addActionListener(e1 -> filesFrame.dispose());
                filesFrame.add(closeButton, BorderLayout.SOUTH);

                filesFrame.setVisible(true);
            }
        });

        viewProcuratorsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame procuratorsFrame = new JFrame("View All Procurators");
                procuratorsFrame.setSize(800, 500);
                procuratorsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                procuratorsFrame.setLayout(new BorderLayout());

                String[] columnNames = {"Name", "SSN", "Address", "Age", "Gender", "Phone", "License Number"};
                DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
                JTable procuratorsTable = new JTable(tableModel);

                if (deputy != null && !deputy.getProcurators().isEmpty()) {
                    for (Procurator procurator : deputy.getProcurators()) {
                        Object[] rowData = {
                                procurator.getName(),
                                procurator.getSSN(),
                                procurator.getAddress(),
                                procurator.getAge(),
                                procurator.getGender(),
                                procurator.getPhone(),
                                procurator.getLicenseNumber()
                        };
                        tableModel.addRow(rowData);
                    }
                } else {
                    JOptionPane.showMessageDialog(procuratorsFrame, "No procurators available to display.", "Information", JOptionPane.INFORMATION_MESSAGE);
                }

                JScrollPane scrollPane = new JScrollPane(procuratorsTable);
                procuratorsFrame.add(scrollPane, BorderLayout.CENTER);

                JButton closeButton = new JButton("Close");
                closeButton.addActionListener(e1 -> procuratorsFrame.dispose());
                procuratorsFrame.add(closeButton, BorderLayout.SOUTH);

                procuratorsFrame.setVisible(true);
            }
        });

        viewLawyersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame lawyersFrame = new JFrame("View All Lawyers");
                lawyersFrame.setSize(800, 500);
                lawyersFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                lawyersFrame.setLayout(new BorderLayout());

                String[] columnNames = {"Name", "SSN", "Address", "Age", "Gender", "Phone", "Lawyer ID"};
                DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
                JTable lawyersTable = new JTable(tableModel);

                if (deputy != null && !deputy.getLawyers().isEmpty()) {
                    for (Lawyer lawyer : deputy.getLawyers()) {
                        Object[] rowData = {
                                lawyer.getName(),
                                lawyer.getSSN(),
                                lawyer.getAddress(),
                                lawyer.getAge(),
                                lawyer.getGender(),
                                lawyer.getPhone(),
                                lawyer.getLawyerId()
                        };
                        tableModel.addRow(rowData);
                    }
                } else {
                    JOptionPane.showMessageDialog(lawyersFrame, "No lawyers available to display.", "Information", JOptionPane.INFORMATION_MESSAGE);
                }

                JScrollPane scrollPane = new JScrollPane(lawyersTable);
                lawyersFrame.add(scrollPane, BorderLayout.CENTER);

                JButton closeButton = new JButton("Close");
                closeButton.addActionListener(e1 -> lawyersFrame.dispose());
                lawyersFrame.add(closeButton, BorderLayout.SOUTH);

                lawyersFrame.setVisible(true);
            }
        });


        viewArchivedFilesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame archivedFilesFrame = new JFrame("View All Archived Files");
                archivedFilesFrame.setSize(800, 500);
                archivedFilesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                archivedFilesFrame.setLayout(new BorderLayout());

                String[] columnNames = {"File Number", "Status", "Opened Date", "Closed Date", "Report Details", "Procurator", "Suspect Lawyer", "Victim Lawyer"};
                DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
                JTable archivedFilesTable = new JTable(tableModel);

                if (deputy != null && !deputy.getArchivedFiles().isEmpty()) {
                    for (FilesDeputy file : deputy.getArchivedFiles()) {
                        Object[] rowData = {
                                file.getFileNumber(),
                                file.getStatus() != null ? file.getStatus().toString() : "N/A",
                                file.getFileOpenedDate(),
                                file.getFileClosedDate(),
                                file.getReport() != null ? file.getReport().toString() : "N/A",
                                file.getProcurator() != null ? file.getProcurator().getName() : "N/A",
                                file.getSuspectLawyer() != null ? file.getSuspectLawyer().getName() : "N/A",
                                file.getVictimLawyer() != null ? file.getVictimLawyer().getName() : "N/A"
                        };
                        tableModel.addRow(rowData);
                    }
                } else {
                    JOptionPane.showMessageDialog(archivedFilesFrame, "No archived files available to display.", "Information", JOptionPane.INFORMATION_MESSAGE);
                }

                JScrollPane scrollPane = new JScrollPane(archivedFilesTable);
                archivedFilesFrame.add(scrollPane, BorderLayout.CENTER);

                JButton closeButton = new JButton("Close");
                closeButton.addActionListener(e1 -> archivedFilesFrame.dispose());
                archivedFilesFrame.add(closeButton, BorderLayout.SOUTH);

                archivedFilesFrame.setVisible(true);
            }
        });

        addProcuratorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame addProcuratorFrame = new JFrame("Add Procurator");
                addProcuratorFrame.setSize(400, 500);
                addProcuratorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                addProcuratorFrame.setLayout(new GridLayout(8, 2, 10, 10));

                JLabel nameLabel = new JLabel("Name:");
                JTextField nameField = new JTextField();
                addProcuratorFrame.add(nameLabel);
                addProcuratorFrame.add(nameField);

                JLabel ssnLabel = new JLabel("SSN:");
                JTextField ssnField = new JTextField();
                addProcuratorFrame.add(ssnLabel);
                addProcuratorFrame.add(ssnField);

                JLabel addressLabel = new JLabel("Address:");
                JTextField addressField = new JTextField();
                addProcuratorFrame.add(addressLabel);
                addProcuratorFrame.add(addressField);

                JLabel ageLabel = new JLabel("Age:");
                JTextField ageField = new JTextField();
                addProcuratorFrame.add(ageLabel);
                addProcuratorFrame.add(ageField);

                JLabel genderLabel = new JLabel("Gender:");
                JTextField genderField = new JTextField();
                addProcuratorFrame.add(genderLabel);
                addProcuratorFrame.add(genderField);

                JLabel phoneLabel = new JLabel("Phone:");
                JTextField phoneField = new JTextField();
                addProcuratorFrame.add(phoneLabel);
                addProcuratorFrame.add(phoneField);

                JLabel licenseLabel = new JLabel("License Number:");
                JTextField licenseField = new JTextField();
                addProcuratorFrame.add(licenseLabel);
                addProcuratorFrame.add(licenseField);

                JButton submitButton = new JButton("Submit");
                JButton cancelButton = new JButton("Cancel");
                addProcuratorFrame.add(submitButton);
                addProcuratorFrame.add(cancelButton);

                // Submit Button Action
                submitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String name = nameField.getText();
                        String ssn = ssnField.getText();
                        String address = addressField.getText();
                        int age;
                        try {
                            age = Integer.parseInt(ageField.getText());
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(addProcuratorFrame, "Invalid age. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        String gender = genderField.getText();
                        String phone = phoneField.getText();
                        String licenseNumber = licenseField.getText();

                        // Validate inputs
                        if (name.isEmpty() || ssn.isEmpty() || address.isEmpty() || gender.isEmpty() || phone.isEmpty() || licenseNumber.isEmpty()) {
                            JOptionPane.showMessageDialog(addProcuratorFrame, "All fields must be filled.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        // Add procurator to deputy
                        Procurator newProcurator = new Procurator(name, ssn, address, age, gender, phone, licenseNumber);
                        deputy.getProcurators().add(newProcurator);
                        JOptionPane.showMessageDialog(addProcuratorFrame, "Procurator added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        addProcuratorFrame.dispose();
                    }
                });

                // Cancel Button Action
                cancelButton.addActionListener(e1 -> addProcuratorFrame.dispose());

                addProcuratorFrame.setVisible(true);
            }
        });


        addLawyerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame addLawyerFrame = new JFrame("Add Lawyer");
                addLawyerFrame.setSize(400, 500);
                addLawyerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                addLawyerFrame.setLayout(new GridLayout(7, 2, 10, 10));

                JLabel nameLabel = new JLabel("Name:");
                JTextField nameField = new JTextField();
                addLawyerFrame.add(nameLabel);
                addLawyerFrame.add(nameField);

                JLabel ssnLabel = new JLabel("SSN:");
                JTextField ssnField = new JTextField();
                addLawyerFrame.add(ssnLabel);
                addLawyerFrame.add(ssnField);

                JLabel addressLabel = new JLabel("Address:");
                JTextField addressField = new JTextField();
                addLawyerFrame.add(addressLabel);
                addLawyerFrame.add(addressField);

                JLabel ageLabel = new JLabel("Age:");
                JTextField ageField = new JTextField();
                addLawyerFrame.add(ageLabel);
                addLawyerFrame.add(ageField);

                JLabel genderLabel = new JLabel("Gender:");
                JTextField genderField = new JTextField();
                addLawyerFrame.add(genderLabel);
                addLawyerFrame.add(genderField);

                JLabel phoneLabel = new JLabel("Phone:");
                JTextField phoneField = new JTextField();
                addLawyerFrame.add(phoneLabel);
                addLawyerFrame.add(phoneField);

                JButton submitButton = new JButton("Submit");
                JButton cancelButton = new JButton("Cancel");
                addLawyerFrame.add(submitButton);
                addLawyerFrame.add(cancelButton);

                // Submit Button Action
                submitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String name = nameField.getText();
                        String ssn = ssnField.getText();
                        String address = addressField.getText();
                        int age;
                        try {
                            age = Integer.parseInt(ageField.getText());
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(addLawyerFrame, "Invalid age. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        String gender = genderField.getText();
                        String phone = phoneField.getText();

                        // Validate inputs
                        if (name.isEmpty() || ssn.isEmpty() || address.isEmpty() || gender.isEmpty() || phone.isEmpty()) {
                            JOptionPane.showMessageDialog(addLawyerFrame, "All fields must be filled.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        // Add lawyer to deputy
                        Lawyer newLawyer = new Lawyer(name, ssn, address, age, gender, phone);
                        deputy.getLawyers().add(newLawyer);
                        JOptionPane.showMessageDialog(addLawyerFrame, "Lawyer added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        addLawyerFrame.dispose();
                    }
                });

                // Cancel Button Action
                cancelButton.addActionListener(e1 -> addLawyerFrame.dispose());

                addLawyerFrame.setVisible(true);
            }
        });


        createFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame createFileFrame = new JFrame("Create File");
                createFileFrame.setSize(800, 600);
                createFileFrame.setLayout(null);

                JLabel reportLabel = new JLabel("Select Report:");
                reportLabel.setBounds(50, 50, 200, 30);
                createFileFrame.add(reportLabel);

                JComboBox<String> reportComboBox = new JComboBox<>();
                for (Report report : deputy.getReports()) {
                    reportComboBox.addItem("Report #" + report.getReportNumber() + " - " + report.getCrimeDetails());
                }
                reportComboBox.setBounds(200, 50, 400, 30);
                createFileFrame.add(reportComboBox);

                JLabel procuratorLabel = new JLabel("Select Procurator:");
                procuratorLabel.setBounds(50, 100, 200, 30);
                createFileFrame.add(procuratorLabel);

                JComboBox<String> procuratorComboBox = new JComboBox<>();
                for (Procurator procurator : deputy.getProcurators()) {
                    procuratorComboBox.addItem(procurator.getName() + " (License: " + procurator.getLicenseNumber() + ")");
                }
                procuratorComboBox.setBounds(200, 100, 400, 30);
                createFileFrame.add(procuratorComboBox);

                JLabel suspectLawyerLabel = new JLabel("Select Suspect Lawyer:");
                suspectLawyerLabel.setBounds(50, 150, 200, 30);
                createFileFrame.add(suspectLawyerLabel);

                JComboBox<String> suspectLawyerComboBox = new JComboBox<>();
                for (Lawyer lawyer : deputy.getLawyers()) {
                    suspectLawyerComboBox.addItem(lawyer.getName() + " (ID: " + lawyer.getLawyerId() + ")");
                }
                suspectLawyerComboBox.setBounds(200, 150, 400, 30);
                createFileFrame.add(suspectLawyerComboBox);

                JLabel victimLawyerLabel = new JLabel("Select Victim Lawyer:");
                victimLawyerLabel.setBounds(50, 200, 200, 30);
                createFileFrame.add(victimLawyerLabel);

                JComboBox<String> victimLawyerComboBox = new JComboBox<>();
                for (Lawyer lawyer : deputy.getLawyers()) {
                    victimLawyerComboBox.addItem(lawyer.getName() + " (ID: " + lawyer.getLawyerId() + ")");
                }
                victimLawyerComboBox.setBounds(200, 200, 400, 30);
                createFileFrame.add(victimLawyerComboBox);

                JButton saveButton = new JButton("Create File");
                saveButton.setBounds(300, 300, 150, 30);
                createFileFrame.add(saveButton);

                saveButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Retrieve selected details
                        int selectedReportIndex = reportComboBox.getSelectedIndex();
                        int selectedProcuratorIndex = procuratorComboBox.getSelectedIndex();
                        int selectedSuspectLawyerIndex = suspectLawyerComboBox.getSelectedIndex();
                        int selectedVictimLawyerIndex = victimLawyerComboBox.getSelectedIndex();

                        if (selectedReportIndex == -1 || selectedProcuratorIndex == -1 ||
                                selectedSuspectLawyerIndex == -1 || selectedVictimLawyerIndex == -1) {
                            JOptionPane.showMessageDialog(createFileFrame, "Please select all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        // Get the selected entities
                        Report selectedReport = deputy.getReports().get(selectedReportIndex);
                        Procurator selectedProcurator = deputy.getProcurators().get(selectedProcuratorIndex);
                        Lawyer selectedSuspectLawyer = deputy.getLawyers().get(selectedSuspectLawyerIndex);
                        Lawyer selectedVictimLawyer = deputy.getLawyers().get(selectedVictimLawyerIndex);

                        // Create the file
                        FilesDeputy newFile = new FilesDeputy(selectedReport, selectedProcurator, selectedSuspectLawyer, selectedVictimLawyer);
                        deputy.getFilesDeputy().add(newFile);

                        JOptionPane.showMessageDialog(createFileFrame, "File created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        createFileFrame.dispose();
                    }
                });

                createFileFrame.setVisible(true);
            }
        });
        updateFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame viewFilesFrame = new JFrame("View All Files");
                viewFilesFrame.setSize(800, 500);
                viewFilesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                viewFilesFrame.setLayout(new BorderLayout());

                String[] columnNames = {"File Number", "Status", "Opened Date", "Closed Date", "Report", "Procurator", "Suspect Lawyer", "Victim Lawyer"};
                DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
                JTable filesTable = new JTable(tableModel);

                // Populate the table with file data
                for (FilesDeputy file : deputy.getFilesDeputy()) {
                    tableModel.addRow(new Object[]{
                            file.getFileNumber(),
                            file.getStatus() != null ? file.getStatus().toString() : "N/A",
                            file.getFileOpenedDate(),
                            file.getFileClosedDate(),
                            file.getReport().getCrimeDetails(),
                            file.getProcurator().getName(),
                            file.getSuspectLawyer().getName(),
                            file.getVictimLawyer().getName()
                    });
                }

                JScrollPane scrollPane = new JScrollPane(filesTable);
                viewFilesFrame.add(scrollPane, BorderLayout.CENTER);

                JButton updateButton = new JButton("Update Selected File");
                viewFilesFrame.add(updateButton, BorderLayout.SOUTH);

                // Update button action
                updateButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int selectedRow = filesTable.getSelectedRow();
                        if (selectedRow == -1) {
                            JOptionPane.showMessageDialog(viewFilesFrame, "Please select a file to update.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        // Get the selected file
                        FilesDeputy selectedFile = deputy.getFilesDeputy().get(selectedRow);
                        openUpdateFileForm(deputy, selectedFile, viewFilesFrame);
                    }
                });

                viewFilesFrame.setVisible(true);
            }
        });
        removeProcuratorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame viewProcuratorsFrame = new JFrame("Remove Procurator");
                viewProcuratorsFrame.setSize(800, 500);
                viewProcuratorsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                viewProcuratorsFrame.setLayout(new BorderLayout());

                String[] columnNames = {"Name", "SSN", "Address", "Age", "Gender", "Phone", "License Number"};
                DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
                JTable procuratorsTable = new JTable(tableModel);

                // Populate the table with procurator data
                for (Procurator procurator : deputy.getProcurators()) {
                    tableModel.addRow(new Object[]{
                            procurator.getName(),
                            procurator.getSSN(),
                            procurator.getAddress(),
                            procurator.getAge(),
                            procurator.getGender(),
                            procurator.getPhone(),
                            procurator.getLicenseNumber()
                    });
                }

                JScrollPane scrollPane = new JScrollPane(procuratorsTable);
                viewProcuratorsFrame.add(scrollPane, BorderLayout.CENTER);

                JButton removeButton = new JButton("Remove Selected Procurator");
                viewProcuratorsFrame.add(removeButton, BorderLayout.SOUTH);

                // Remove button action
                removeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int selectedRow = procuratorsTable.getSelectedRow();
                        if (selectedRow == -1) {
                            JOptionPane.showMessageDialog(viewProcuratorsFrame, "Please select a procurator to remove.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        // Confirm removal
                        int confirm = JOptionPane.showConfirmDialog(
                                viewProcuratorsFrame,
                                "Are you sure you want to remove this procurator?",
                                "Confirm Removal",
                                JOptionPane.YES_NO_OPTION
                        );

                        if (confirm == JOptionPane.YES_OPTION) {
                            // Remove the selected procurator
                            deputy.getProcurators().remove(selectedRow);
                            tableModel.removeRow(selectedRow); // Update table view
                            JOptionPane.showMessageDialog(viewProcuratorsFrame, "Procurator removed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                });

                viewProcuratorsFrame.setVisible(true);
            }
        });
        removeLawyerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame viewLawyersFrame = new JFrame("Remove Lawyer");
                viewLawyersFrame.setSize(800, 500);
                viewLawyersFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                viewLawyersFrame.setLayout(new BorderLayout());

                String[] columnNames = {"Name", "SSN", "Address", "Age", "Gender", "Phone", "Lawyer ID"};
                DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
                JTable lawyersTable = new JTable(tableModel);

                // Populate the table with lawyer data
                for (Lawyer lawyer : deputy.getLawyers()) {
                    tableModel.addRow(new Object[]{
                            lawyer.getName(),
                            lawyer.getSSN(),
                            lawyer.getAddress(),
                            lawyer.getAge(),
                            lawyer.getGender(),
                            lawyer.getPhone(),
                            lawyer.getLawyerId()
                    });
                }

                JScrollPane scrollPane = new JScrollPane(lawyersTable);
                viewLawyersFrame.add(scrollPane, BorderLayout.CENTER);

                JButton removeButton = new JButton("Remove Selected Lawyer");
                viewLawyersFrame.add(removeButton, BorderLayout.SOUTH);

                // Remove button action
                removeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int selectedRow = lawyersTable.getSelectedRow();
                        if (selectedRow == -1) {
                            JOptionPane.showMessageDialog(viewLawyersFrame, "Please select a lawyer to remove.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        // Confirm removal
                        int confirm = JOptionPane.showConfirmDialog(
                                viewLawyersFrame,
                                "Are you sure you want to remove this lawyer?",
                                "Confirm Removal",
                                JOptionPane.YES_NO_OPTION
                        );

                        if (confirm == JOptionPane.YES_OPTION) {
                            // Remove the selected lawyer
                            deputy.getLawyers().remove(selectedRow);
                            tableModel.removeRow(selectedRow); // Update table view
                            JOptionPane.showMessageDialog(viewLawyersFrame, "Lawyer removed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                });

                viewLawyersFrame.setVisible(true);
            }
        });
        archiveFilesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame viewFilesFrame = new JFrame("Archive Files");
                viewFilesFrame.setSize(800, 500);
                viewFilesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                viewFilesFrame.setLayout(new BorderLayout());

                String[] columnNames = {"File Number", "Status", "Opened Date", "Closed Date", "Report", "Procurator", "Suspect Lawyer", "Victim Lawyer"};
                DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
                JTable filesTable = new JTable(tableModel);

                // Populate the table with active files
                for (FilesDeputy file : deputy.getFilesDeputy()) {
                    tableModel.addRow(new Object[]{
                            file.getFileNumber(),
                            file.getStatus() != null ? file.getStatus().toString() : "N/A",
                            file.getFileOpenedDate(),
                            file.getFileClosedDate(),
                            file.getReport().getCrimeDetails(),
                            file.getProcurator().getName(),
                            file.getSuspectLawyer().getName(),
                            file.getVictimLawyer().getName()
                    });
                }

                JScrollPane scrollPane = new JScrollPane(filesTable);
                viewFilesFrame.add(scrollPane, BorderLayout.CENTER);

                JButton archiveButton = new JButton("Archive Selected File");
                viewFilesFrame.add(archiveButton, BorderLayout.SOUTH);

                // Archive button action
                archiveButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int selectedRow = filesTable.getSelectedRow();
                        if (selectedRow == -1) {
                            JOptionPane.showMessageDialog(viewFilesFrame, "Please select a file to archive.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        // Confirm archival
                        int confirm = JOptionPane.showConfirmDialog(
                                viewFilesFrame,
                                "Are you sure you want to archive this file?",
                                "Confirm Archival",
                                JOptionPane.YES_NO_OPTION
                        );

                        if (confirm == JOptionPane.YES_OPTION) {
                            // Move the selected file to archived files
                            FilesDeputy selectedFile = deputy.getFilesDeputy().get(selectedRow);
                            deputy.getArchivedFiles().add(selectedFile);
                            deputy.getFilesDeputy().remove(selectedRow);
                            tableModel.removeRow(selectedRow); // Update table view

                            JOptionPane.showMessageDialog(viewFilesFrame, "File archived successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                });

                viewFilesFrame.setVisible(true);
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new CriminalManagementSystemGUI();
            }
        });

        frame.setVisible(true);

    }

    private void openUpdateProcuratorForm(Deputy deputy, Procurator procurator, JFrame parentFrame) {
        parentFrame.dispose(); // Close the previous frame

        JFrame formFrame = new JFrame("Update Procurator");
        formFrame.setSize(600, 500);
        formFrame.setLayout(null);

        // Fields for procurator details
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 50, 100, 30);
        formFrame.add(nameLabel);

        JTextField nameField = new JTextField(procurator.getName());
        nameField.setBounds(150, 50, 200, 30);
        formFrame.add(nameField);

        JLabel ssnLabel = new JLabel("SSN:");
        ssnLabel.setBounds(50, 100, 100, 30);
        formFrame.add(ssnLabel);

        JTextField ssnField = new JTextField(procurator.getSSN());
        ssnField.setBounds(150, 100, 200, 30);
        formFrame.add(ssnField);

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(50, 150, 100, 30);
        formFrame.add(addressLabel);

        JTextField addressField = new JTextField(procurator.getAddress());
        addressField.setBounds(150, 150, 200, 30);
        formFrame.add(addressField);

        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setBounds(50, 200, 100, 30);
        formFrame.add(ageLabel);

        JTextField ageField = new JTextField(String.valueOf(procurator.getAge()));
        ageField.setBounds(150, 200, 200, 30);
        formFrame.add(ageField);

        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(50, 250, 100, 30);
        formFrame.add(genderLabel);

        JTextField genderField = new JTextField(procurator.getGender());
        genderField.setBounds(150, 250, 200, 30);
        formFrame.add(genderField);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(50, 300, 100, 30);
        formFrame.add(phoneLabel);

        JTextField phoneField = new JTextField(procurator.getPhone());
        phoneField.setBounds(150, 300, 200, 30);
        formFrame.add(phoneField);

        JLabel licenseLabel = new JLabel("License Number:");
        licenseLabel.setBounds(50, 350, 150, 30);
        formFrame.add(licenseLabel);

        JTextField licenseField = new JTextField(procurator.getLicenseNumber());
        licenseField.setBounds(200, 350, 200, 30);
        formFrame.add(licenseField);

        // Save button
        JButton saveButton = new JButton("Save Changes");
        saveButton.setBounds(200, 400, 150, 30);
        formFrame.add(saveButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Update the procurator details
                    procurator.setName(nameField.getText());
                    procurator.setSSN(ssnField.getText());
                    procurator.setAddress(addressField.getText());
                    procurator.setAge(Integer.parseInt(ageField.getText()));
                    procurator.setGender(genderField.getText());
                    procurator.setPhone(phoneField.getText());
                    procurator.setLicenseNumber(licenseField.getText());

                    JOptionPane.showMessageDialog(formFrame, "Procurator updated successfully!");

                    formFrame.dispose(); // Close the update form
                    new DeputyMenuGUI(deputy); // Return to Deputy menu
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(formFrame, "Invalid age. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        formFrame.setVisible(true);
    }

    private void openUpdateFileForm(Deputy deputy, FilesDeputy file, JFrame parentFrame) {
        parentFrame.dispose(); // Close the previous frame

        JFrame formFrame = new JFrame("Update File");
        formFrame.setSize(800, 600);
        formFrame.setLayout(null);

        JLabel statusLabel = new JLabel("File Status:");
        statusLabel.setBounds(50, 50, 100, 30);
        formFrame.add(statusLabel);

        JComboBox<StatusFile> statusComboBox = new JComboBox<>(StatusFile.values());
        statusComboBox.setSelectedItem(file.getStatus());
        statusComboBox.setBounds(200, 50, 200, 30);
        formFrame.add(statusComboBox);

        JLabel closedDateLabel = new JLabel("Closed Date:");
        closedDateLabel.setBounds(50, 100, 100, 30);
        formFrame.add(closedDateLabel);

        JTextField closedDateField = new JTextField(file.getFileClosedDate().toString());
        closedDateField.setBounds(200, 100, 200, 30);
        formFrame.add(closedDateField);

        JLabel suspectLawyerLabel = new JLabel("Suspect Lawyer:");
        suspectLawyerLabel.setBounds(50, 150, 150, 30);
        formFrame.add(suspectLawyerLabel);

        JComboBox<String> suspectLawyerComboBox = new JComboBox<>();
        for (Lawyer lawyer : deputy.getLawyers()) {
            suspectLawyerComboBox.addItem(lawyer.getName() + " (ID: " + lawyer.getLawyerId() + ")");
        }
        suspectLawyerComboBox.setSelectedItem(file.getSuspectLawyer().getName());
        suspectLawyerComboBox.setBounds(200, 150, 400, 30);
        formFrame.add(suspectLawyerComboBox);

        JLabel victimLawyerLabel = new JLabel("Victim Lawyer:");
        victimLawyerLabel.setBounds(50, 200, 150, 30);
        formFrame.add(victimLawyerLabel);

        JComboBox<String> victimLawyerComboBox = new JComboBox<>();
        for (Lawyer lawyer : deputy.getLawyers()) {
            victimLawyerComboBox.addItem(lawyer.getName() + " (ID: " + lawyer.getLawyerId() + ")");
        }
        victimLawyerComboBox.setSelectedItem(file.getVictimLawyer().getName());
        victimLawyerComboBox.setBounds(200, 200, 400, 30);
        formFrame.add(victimLawyerComboBox);

        JLabel investigatingStatementLabel = new JLabel("Investigating Statement:");
        investigatingStatementLabel.setBounds(50, 250, 200, 30);
        formFrame.add(investigatingStatementLabel);

        JTextArea investigatingStatementArea = new JTextArea(file.getInvestigatingStatement());
        investigatingStatementArea.setBounds(200, 250, 400, 100);
        formFrame.add(investigatingStatementArea);

        JButton saveButton = new JButton("Save Changes");
        saveButton.setBounds(300, 400, 150, 30);
        formFrame.add(saveButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Update file details
                    file.setStatus((StatusFile) statusComboBox.getSelectedItem());
                    file.setFileClosedDate(LocalDate.parse(closedDateField.getText()));
                    file.setSuspectLawyer(deputy.getLawyers().get(suspectLawyerComboBox.getSelectedIndex()));
                    file.setVictimLawyer(deputy.getLawyers().get(victimLawyerComboBox.getSelectedIndex()));
                    file.setInvestigatingStatement(investigatingStatementArea.getText());

                    JOptionPane.showMessageDialog(formFrame, "File updated successfully!");

                    formFrame.dispose(); // Close the update form
                    new DeputyMenuGUI(deputy); // Return to Deputy menu
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(formFrame, "Invalid date format. Please use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        formFrame.setVisible(true);
    }

    private void openUpdateLawyerForm(Deputy deputy, Lawyer lawyer, JFrame parentFrame) {
        parentFrame.dispose(); // Close the previous frame

        JFrame formFrame = new JFrame("Update Lawyer");
        formFrame.setSize(600, 500);
        formFrame.setLayout(null);

        // Fields for lawyer details
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 50, 100, 30);
        formFrame.add(nameLabel);

        JTextField nameField = new JTextField(lawyer.getName());
        nameField.setBounds(150, 50, 200, 30);
        formFrame.add(nameField);

        JLabel ssnLabel = new JLabel("SSN:");
        ssnLabel.setBounds(50, 100, 100, 30);
        formFrame.add(ssnLabel);

        JTextField ssnField = new JTextField(lawyer.getSSN());
        ssnField.setBounds(150, 100, 200, 30);
        formFrame.add(ssnField);

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(50, 150, 100, 30);
        formFrame.add(addressLabel);

        JTextField addressField = new JTextField(lawyer.getAddress());
        addressField.setBounds(150, 150, 200, 30);
        formFrame.add(addressField);

        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setBounds(50, 200, 100, 30);
        formFrame.add(ageLabel);

        JTextField ageField = new JTextField(String.valueOf(lawyer.getAge()));
        ageField.setBounds(150, 200, 200, 30);
        formFrame.add(ageField);

        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(50, 250, 100, 30);
        formFrame.add(genderLabel);

        JTextField genderField = new JTextField(lawyer.getGender());
        genderField.setBounds(150, 250, 200, 30);
        formFrame.add(genderField);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(50, 300, 100, 30);
        formFrame.add(phoneLabel);

        JTextField phoneField = new JTextField(lawyer.getPhone());
        phoneField.setBounds(150, 300, 200, 30);
        formFrame.add(phoneField);

        // Save button
        JButton saveButton = new JButton("Save Changes");
        saveButton.setBounds(200, 400, 150, 30);
        formFrame.add(saveButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Update the lawyer details
                    lawyer.setName(nameField.getText());
                    lawyer.setSSN(ssnField.getText());
                    lawyer.setAddress(addressField.getText());
                    lawyer.setAge(Integer.parseInt(ageField.getText()));
                    lawyer.setGender(genderField.getText());
                    lawyer.setPhone(phoneField.getText());

                    JOptionPane.showMessageDialog(formFrame, "Lawyer updated successfully!");

                    formFrame.dispose(); // Close the update form
                    new DeputyMenuGUI(deputy); // Return to Deputy menu
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(formFrame, "Invalid age. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        formFrame.setVisible(true);
    }

}
