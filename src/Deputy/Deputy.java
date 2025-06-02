package Deputy;

import CRUD.CRUD;
import Persons.*;
import Station.CrimeType;
import Station.Report;
import Station.ReportType;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Deputy implements CRUD {
    private ArrayList<FilesDeputy> filesDeputy;
    private ArrayList<Report> reports;
    private ArrayList<Lawyer> lawyers;
    private ArrayList<Procurator> procurators;
    private ArrayList<FilesDeputy> archivedFiles;
    Scanner scanner = new Scanner(System.in);

    public Deputy() {
        filesDeputy = new ArrayList<>();
        reports = new ArrayList<>();
        lawyers = new ArrayList<>();
        procurators = new ArrayList<>();
        archivedFiles = new ArrayList<>();
        loadReports();
        loadLawyers();
        loadProcurators();
        loadFiles();
        loadArchiveFiles();
        Report.resetId();
        FilesDeputy.resetId();
        Lawyer.resetId();
    }

    public void runMenu() {
        loadReports();
        loadLawyers();
        loadProcurators();
        loadFiles();
        loadArchiveFiles();
        int option;
        do {
            System.out.println("\n******************************");
            System.out.println("*  Deputy Main Menu  *");
            System.out.println("******************************");
            System.out.println("1. View all files");
            System.out.println("2. Create new file");
            System.out.println("3. Update file");
            System.out.println("4. View all procurators");
            System.out.println("5. Add procurators");
            System.out.println("6. Remove procurators");
            System.out.println("7. Update procurators");
            System.out.println("8. View all lawyers");
            System.out.println("9. Add lawyers");
            System.out.println("10. Remove lawyers");
            System.out.println("11. Update lawyers");
            System.out.println("12. Archive file");
            System.out.println("13. View all archived files");
            System.out.println("14. Back to Main Menu");
            System.out.print("Enter your choice: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    System.out.println("\n");
                    viewAll("files");
                    break;
                case 2:
                    System.out.println("\n");
                    add("files");
                    break;
                case 3:
                    System.out.println("\n");
                    update("files");
                    break;
                case 4:
                    System.out.println("\n");
                    viewAll("procurators");
                    break;
                case 5:
                    System.out.println("\n");
                    add("procurators");
                    break;
                case 6:
                    System.out.println("\n");
                    remove("procurators");
                    break;
                case 7:
                    System.out.println("\n");
                    update("procurators");
                    break;
                case 8:
                    System.out.println("\n");
                    viewAll("lawyers");
                    break;
                case 9:
                    System.out.println("\n");
                    add("lawyers");
                    break;
                case 10:
                    System.out.println("\n");
                    remove("lawyers");
                    break;
                case 11:
                    System.out.println("\n");
                    update("lawyers");
                    break;
                case 12:
                    System.out.println("\n");
                    add("archivedFiles");
                    break;
                case 13:
                    System.out.println("\n");
                    viewAll("archivedFiles");
                    break;
                case 14:
                    saveLawyers();
                    saveProcurators();
                    saveFiles();
                    saveArchiveFiles();
                    clearData();
                    System.out.println("Returning to Main Menu...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (option != 14);
    }

    private void clearData() {
        reports.clear();
        filesDeputy.clear();
        lawyers.clear();
        procurators.clear();
        archivedFiles.clear();
        Report.resetId();
        FilesDeputy.resetId();
        Lawyer.resetId();
    }

    @Override
    public void viewAll(String viewType) {
        switch (viewType) {
            case "files":
                viewAllFiles();
                break;
            case "procurators":
                viewAllProcurators();
                break;
            case "lawyers":
                viewAllLawyers();
                break;
            case "archivedFiles":
                viewAllArchivedFiles();
                break;
        }
    }

    @Override
    public void add(String viewType) {
        switch (viewType) {
            case "procurators":
                addProcurators();
                break;
            case "lawyers":
                addLawyers();
                break;
            case "archivedFiles":
                archiveFile();
            case "files":
                createFile();
        }
    }

    @Override
    public void update(String viewType) {
        switch (viewType) {
            case "files":
                updateFile();
                break;
            case "procurators":
                updateProcurators();
                break;
            case "lawyers":
                updateLawyers();
                break;
        }

    }

    @Override
    public void remove(String viewType) {
        switch (viewType) {
            case "procurators":
                removeProcurators();
                break;
            case "lawyers":
                removeLawyers();
                break;
        }

    }

    // -----------------------------------------------------------------------------------
    public void viewAllFiles() {
        if (filesDeputy.isEmpty()) {
            System.out.println("No files available.");
        } else {
            for (int i = 0; i < filesDeputy.size(); i++) {
                filesDeputy.get(i).displayDetails();
            }
        }
    }

    public void createFile() {
        System.out.println("Creating a new file...");
        if (reports.isEmpty()) {
            System.out.println("No reports available to create a file.");
            return;
        }
        for (int i = 0; i < reports.size(); i++) {
            reports.get(i).displayDetails();
        }
        System.out.println("Select a report by number:");
        int reportNumber = scanner.nextInt();
        scanner.nextLine();
        Report selectedReport = null;

        for (int i = 0; i < reports.size(); i++) {
            if (reports.get(i).getReportNumber() == reportNumber) {
                selectedReport = reports.get(i);
                break;

            }
        }
        if (selectedReport == null) {
            System.out.println("No valid report selected.");
            return;
        }

        Procurator procuratorInCharge = null;
        viewAllProcurators();
        System.out.println("Choose which procurator to assign to the file by entering their license number:");
        String selectedSSN = scanner.nextLine();
        for (int i = 0; i < procurators.size(); i++) {
            Procurator procurator = procurators.get(i);
            if (procurator.getLicenseNumber().equals(selectedSSN)) {
                procuratorInCharge = procurator;
                break;
            }
        }
        if (procuratorInCharge == null) {
            System.out.println("No valid procurator selected.");
            return;
        }

        Lawyer lawyerSuspectInCharge = null;
        viewAllLawyers();
        System.out.println("Choose which lawyer for the suspect to assign to the file by entering their ID number:");
        int selectedSuspectLawyerID = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < lawyers.size(); i++) {
            Lawyer lawyer = lawyers.get(i);
            if (lawyer.getLawyerId() == selectedSuspectLawyerID) {
                lawyerSuspectInCharge = lawyer;
                break;
            }
        }
        if (lawyerSuspectInCharge == null) {
            System.out.println("No valid lawyer selected for the suspect.");
            return;
        }

        Lawyer lawyerVictimInCharge = null;
        viewAllLawyers();
        System.out.println("Choose which lawyer for the victim to assign to the file by entering their ID number:");
        int selectedVictimLawyerID = scanner.nextInt();
        for (int i = 0; i < lawyers.size(); i++) {
            Lawyer lawyer = lawyers.get(i);
            if (lawyer.getLawyerId() == selectedVictimLawyerID) {
                lawyerVictimInCharge = lawyer;
                break;
            }
        }
        if (lawyerVictimInCharge == null) {
            System.out.println("No valid lawyer selected for the victim.");
            return;
        }

        FilesDeputy newFile = new FilesDeputy(selectedReport, procuratorInCharge, lawyerSuspectInCharge,
                lawyerVictimInCharge);
        filesDeputy.add(newFile);
        System.out.println("File created successfully.");
    }

    public void updateFile() {
        if (filesDeputy.isEmpty()) {
            System.out.println("No files available.");
        } else {
            System.out.println("\nlisting all the files....:\n");
            for (int i = 0; i < filesDeputy.size(); i++) {
                filesDeputy.get(i).displayDetails();
            }
            System.out.println("\nselect what to update by choosing file number:");
            int fileNumber = scanner.nextInt();
            scanner.nextLine();
            for (int i = 0; i < filesDeputy.size(); i++) {
                FilesDeputy file = filesDeputy.get(i);
                if (file.getFileNumber() == fileNumber) {
                    System.out.println("\nExisting report details:");
                    file.displayDetails();
                    System.out.println("\nSelect details to update: ");
                    System.out.println("1. fileClosedDate");
                    System.out.println("2. suspectLawyer");
                    System.out.println("3. victimLawyer");
                    System.out.println("4. investigatingStatement");
                    System.out.println("5. file status");
                    System.out.println("6. Return to Deputy menu");
                    int choice = scanner.nextInt();
                    scanner.nextLine();
                    switch (choice) {
                        case 1:
                            System.out.println("\nEnter fileClosedDate :");
                            LocalDate newDetails = LocalDate.parse(scanner.nextLine());
                            file.setFileClosedDate(newDetails);
                            break;
                        case 2:
                            viewAllLawyers();
                            System.out.println("\nChoose new lawyer id :");
                            int newId = scanner.nextInt();
                            for (int j = 0; j < lawyers.size(); j++) {
                                if (newId == lawyers.get(j).getLawyerId()) {
                                    filesDeputy.get(i).setSuspectLawyer(lawyers.get(i));
                                    break;
                                }
                            }
                            System.out.println("SuspectLawyer details updated successfully.");
                            break;
                        case 3:
                            System.out.println("\nChoose  new lawyer id  : ");
                            int newId2 = scanner.nextInt();
                            for (int j = 0; j < lawyers.size(); j++) {
                                if (newId2 == lawyers.get(j).getLawyerId()) {
                                    filesDeputy.get(i).setSuspectLawyer(lawyers.get(i));
                                    break;
                                }
                            }
                            System.out.println("VictimLawyer details updated successfully.");
                            break;
                        case 4:
                            System.out.println("\nEnter InvestigatingStatement:");
                            String statement = scanner.nextLine();
                            file.setInvestigatingStatement(statement);
                            break;
                        case 5:
                            System.out.println("\nchoose what you want to update the file status to");
                            System.out.println("1 for UNDER_INVESTIGATION");
                            System.out.println("2 for RESOLVED");
                            System.out.println("3 for DISMISSED");
                            int fileStatusIndex = scanner.nextInt() - 1;
                            scanner.nextLine();
                            StatusFile statusFile = StatusFile.values()[fileStatusIndex];
                            filesDeputy.get(i).setStatus(statusFile);
                            break;
                        case 6:
                            runMenu();
                            break;
                        default:
                            System.out.println("Invalid option selected.");
                            break;
                    }
                    System.out.println("File updated successfully.");
                    return;
                }
            }
            System.out.println("File not found.");
        }

    }

    public void viewAllProcurators() {
        if (procurators.isEmpty()) {
            System.out.println("No procurators available.");
        } else {
            System.out.println("List of all procurators:\n");
            for (int i = 0; i < procurators.size(); i++) {
                procurators.get(i).displayDetails();
            }
        }
    }

    public void addProcurators() {
        System.out.println("Enter Procurator details:");
        System.out.println("Procurator Name:");
        String name = scanner.nextLine();
        System.out.println("Procurator SSN:");
        String SSN = scanner.nextLine();

        System.out.println("Procurator Address:");
        String address = scanner.nextLine();

        System.out.println("Procurator Age:");
        int age = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Procurator Gender:");
        String gender = scanner.nextLine();

        System.out.println("Procurator Phone:");
        String phone = scanner.nextLine();

        System.out.println("License Number:");
        String licenseNumber = scanner.nextLine();
        Procurator newProcurators = new Procurator(name, SSN, address, age, gender, phone, licenseNumber);
        procurators.add(newProcurators);
        System.out.println("Procurator added successfully:");
    }

    public void removeProcurators() {
        if (procurators.isEmpty()) {
            System.out.println("No procurators available.");
        } else {
            System.out.println("Listing all Procurators...\n");
            viewAllProcurators();
            System.out.println("\nChoose Procurator License Number to remove Procurator: ");
            String licenseNumber = scanner.nextLine();
            for (int i = 0; i < procurators.size(); i++) {
                if (procurators.get(i).getLicenseNumber().equals(licenseNumber)) {
                    procurators.remove(i);
                    break;
                }
            }

            System.out.println("Procurator removed successfully.");
        }

    }

    public void updateProcurators() {
        viewAllProcurators();
        System.out.println("\nEnter the Procurator license Number to update: ");
        String licenseNumber = scanner.nextLine();
        for (int i = 0; i < procurators.size(); i++) {
            Procurator procurator = procurators.get(i);
            if (procurator.getLicenseNumber().equals(licenseNumber)) {
                System.out.println("\nExisting Procurator details:");
                procurator.displayDetails();
                System.out.println("Select Procurator details to update: ");
                System.out.println("1. Procurator Address");
                System.out.println("2. Procurator Phone");
                System.out.println("3. Return to Deputy menu");
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        System.out.println("\nEnter new address:");
                        String newAddress = scanner.nextLine();
                        procurator.setAddress(newAddress);
                        break;
                    case 2:
                        System.out.println("\nEnter new phone:");
                        String newPhone = scanner.nextLine();
                        procurator.setPhone(newPhone);
                        break;
                    case 3:
                        runMenu();
                    default:
                        System.out.println("Invalid option selected.");
                        break;

                }

            }
        }

    }

    public void viewAllLawyers() {
        if (lawyers.isEmpty()) {
            System.out.println("No lawyers available.");
        } else {
            for (int i = 0; i < lawyers.size(); i++) {
                lawyers.get(i).displayDetails();
            }
        }
    }

    public void addLawyers() {
        System.out.println("Enter Lawyer details:");
        System.out.println("Lawyer Name:");
        String name = scanner.nextLine();

        System.out.println("Lawyer SSN:");
        String SSN = scanner.nextLine();

        System.out.println("Lawyer Address:");
        String address = scanner.nextLine();

        System.out.println("Lawyer Age:");
        int age = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Lawyer Gender:");
        String gender = scanner.nextLine();

        System.out.println("Lawyer Phone:");
        String phone = scanner.nextLine();

        Lawyer newLawyers = new Lawyer(name, SSN, address, age, gender, phone);
        lawyers.add(newLawyers);

        System.out.println("Lawyer added successfully:");
    }

    public void removeLawyers() {
        System.out.println("Listing all Lawyers...\n");
        viewAllLawyers();
        System.out.println("\nChoose LawyerId to remove Lawyer: ");
        int lawyerId = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < lawyers.size(); i++) {
            if (lawyers.get(i).getLawyerId() == lawyerId) {
                lawyers.remove(i);
                break;
            }
        }
        System.out.println("Lawyer removed successfully.");
    }

    public void updateLawyers() {
        viewAllLawyers();
        System.out.println("\nEnter the lawyer id to update: ");
        int lawyerId = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < lawyers.size(); i++) {
            Lawyer lawyer = lawyers.get(i);
            if (lawyer.getLawyerId() == lawyerId) {
                System.out.println("\nExisting Lawyer details:");
                lawyer.displayDetails();
                System.out.println("\nSelect details to update: ");
                System.out.println("1. Address");
                System.out.println("2. Phone");
                System.out.println("3. Return to Deputy menu");
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        System.out.println("\nEnter new address:");
                        String newAddress = scanner.nextLine();
                        lawyer.setAddress(newAddress);
                        break;
                    case 2:
                        System.out.println("\nEnter new phone:");
                        String newPhone = scanner.nextLine();
                        lawyer.setPhone(newPhone);
                        break;
                    case 3:
                        runMenu();
                    default:
                        System.out.println("Invalid option selected.");
                        break;

                }

            }

        }
    }

    public void archiveFile() {
        viewAllFiles();
        System.out.println("\nEnter the file number to archive:");
        int fileNumber = scanner.nextInt();
        scanner.nextLine();
        FilesDeputy fileToArchive = null;
        for (int i = 0; i < filesDeputy.size(); i++) {
            FilesDeputy file = filesDeputy.get(i);
            if (file.getFileNumber() == fileNumber) {
                fileToArchive = file;
                break;
            }
        }
        if (fileToArchive != null) {
            archivedFiles.add(fileToArchive);
            filesDeputy.remove(fileToArchive);
            System.out.println("File archived successfully.");
        } else {
            System.out.println("File not found.");
        }
    }

    public void viewAllArchivedFiles() {
        if (archivedFiles.isEmpty()) {
            System.out.println("No archived files.");
        } else {
            for (int i = 0; i < archivedFiles.size(); i++) {
                archivedFiles.get(i).displayDetails();
            }
        }
    }

    // -----------------------------------------------------------------------------------
    public void loadProcurators() {

        try {
            Scanner sc = new Scanner(new File("FilesTXT\\Deputy\\Procurator.txt"));
            while (sc.hasNextLine()) {

                String name = sc.nextLine();
                String SSN = sc.nextLine();
                String address = sc.nextLine();
                int age = Integer.parseInt(sc.nextLine());
                String gender = sc.nextLine();
                String phone = sc.nextLine();
                String licenseNumber = sc.nextLine();

                Procurator procurator = new Procurator(name, SSN, address, age, gender, phone, licenseNumber);
                procurators.add(procurator);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: FilesTXT\\Deputy\\Procurator.txt");
        }
        System.out.println("Procurator loaded successfully.");

    }

    public void saveProcurators() {
        try {
            FileWriter writer = new FileWriter("FilesTXT\\Deputy\\Procurator.txt", false);
            for (int i = 0; i < procurators.size(); i++) {
                writer.write(procurators.get(i).toString());
                writer.write("\n");
            }
            writer.close();
            System.out.println("Procurators saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving Procurator: " + e.getMessage());
        }
    }

    public void loadLawyers() {
        try {
            Scanner sc = new Scanner(new File("FilesTXT\\Deputy\\lawyer.txt"));
            while (sc.hasNextLine()) {

                String name = sc.nextLine();
                String SSN = sc.nextLine();
                String address = sc.nextLine();
                int age = Integer.parseInt(sc.nextLine());
                String gender = sc.nextLine();
                String phone = sc.nextLine();

                Lawyer lawyer = new Lawyer(name, SSN, address, age, gender, phone);
                lawyers.add(lawyer);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: FilesTXT\\Deputy\\lawyer.txt");
        }
        System.out.println("Lawyer loaded successfully.");
    }

    public void saveLawyers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("FilesTXT\\Deputy\\lawyer.txt"))) {
            for (int i = 0; i < lawyers.size(); i++) {
                writer.write(lawyers.get(i).toString());
                writer.write("\n");
            }
            writer.flush();
            System.out.println("lawyers saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving lawyers: " + e.getMessage());
        }
    }

    public void loadReports() {
        try {
            Scanner sc = new Scanner(new File("FilesTXT\\Stations\\Reports.txt"));

            while (sc.hasNextLine()) {
                try {
                    // Report
                    ReportType reportTypeStr = ReportType.valueOf(sc.nextLine().toUpperCase());
                    LocalDate dateStr = LocalDate.parse(sc.nextLine());
                    CrimeType crimeTypeStr = CrimeType.valueOf(sc.nextLine().toUpperCase());
                    String crimeDetails = sc.nextLine();

                    // Assigned Officers
                    String name = sc.nextLine();
                    String SSN = sc.nextLine();
                    String address = sc.nextLine();
                    int age = Integer.parseInt(sc.nextLine());
                    String gender = sc.nextLine();
                    String phone = sc.nextLine();
                    String rank = sc.nextLine();

                    Officer assignedOfficer = new Officer(name, SSN, address, age, gender, phone, rank);
                    Report NewReport = new Report(reportTypeStr, dateStr, crimeTypeStr, crimeDetails, assignedOfficer);

                    // Victim
                    name = sc.nextLine();
                    SSN = sc.nextLine();
                    address = sc.nextLine();
                    age = Integer.parseInt(sc.nextLine());
                    gender = sc.nextLine();
                    phone = sc.nextLine();
                    String statement = sc.nextLine();

                    NewReport.setVictim(new Victim(name, SSN, address, age, gender, phone, statement));

                    // Witness
                    name = sc.nextLine();
                    SSN = sc.nextLine();
                    address = sc.nextLine();
                    age = Integer.parseInt(sc.nextLine());
                    gender = sc.nextLine();
                    phone = sc.nextLine();
                    statement = sc.nextLine();

                    NewReport.setWitness(new Witness(name, SSN, address, age, gender, phone, statement));

                    // Suspect
                    name = sc.nextLine();
                    SSN = sc.nextLine();
                    address = sc.nextLine();
                    age = Integer.parseInt(sc.nextLine());
                    gender = sc.nextLine();
                    phone = sc.nextLine();
                    String description = sc.nextLine();

                    Suspect suspect = new Suspect(name, SSN, address, age, gender, phone, description);
                    NewReport.setSuspect(suspect);
                    reports.add(NewReport);
                } catch (DateTimeParseException e) {
                    System.out.println("Error");
                }
            }

            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: Reports.txt");
        }
        System.out.println("Reports loaded successfully.");
    }

    public ArrayList<FilesDeputy> getFilesDeputy() {
        return filesDeputy;
    }

    public void loadFiles() {
        try {
            Scanner sc = new Scanner(new File("FilesTXT\\Deputy\\DeputyFiles.txt"));
            while (sc.hasNextLine()) {
                try {
                    // Station Report
                    ReportType reportTypeStr = ReportType.valueOf(sc.nextLine().toUpperCase());
                    LocalDate dateStr = LocalDate.parse(sc.nextLine());
                    CrimeType crimeTypeStr = CrimeType.valueOf(sc.nextLine().toUpperCase());
                    String crimeDetails = sc.nextLine();

                    // Assigned Officers
                    String name = sc.nextLine();
                    String SSN = sc.nextLine();
                    String address = sc.nextLine();
                    int age = Integer.parseInt(sc.nextLine());
                    String gender = sc.nextLine();
                    String phone = sc.nextLine();
                    String rank = sc.nextLine();

                    Officer assignedOfficer = new Officer(name, SSN, address, age, gender, phone, rank);
                    Report NewReport = new Report(reportTypeStr, dateStr, crimeTypeStr, crimeDetails, assignedOfficer);

                    // Persons.Victim
                    name = sc.nextLine();
                    SSN = sc.nextLine();
                    address = sc.nextLine();
                    age = Integer.parseInt(sc.nextLine());
                    gender = sc.nextLine();
                    phone = sc.nextLine();
                    String statement = sc.nextLine();

                    NewReport.setVictim(new Victim(name, SSN, address, age, gender, phone, statement));

                    // Persons.Witness
                    name = sc.nextLine();
                    SSN = sc.nextLine();
                    address = sc.nextLine();
                    age = Integer.parseInt(sc.nextLine());
                    gender = sc.nextLine();
                    phone = sc.nextLine();
                    statement = sc.nextLine();

                    NewReport.setWitness(new Witness(name, SSN, address, age, gender, phone, statement));
                    // Persons.Suspect
                    name = sc.nextLine();
                    SSN = sc.nextLine();
                    address = sc.nextLine();
                    age = Integer.parseInt(sc.nextLine());
                    gender = sc.nextLine();
                    phone = sc.nextLine();
                    String description = sc.nextLine();

                    Suspect suspect = new Suspect(name, SSN, address, age, gender, phone, description);
                    NewReport.setSuspect(suspect);

                    // Persons.Procurator
                    name = sc.nextLine();
                    SSN = sc.nextLine();
                    address = sc.nextLine();
                    age = Integer.parseInt(sc.nextLine());
                    gender = sc.nextLine();
                    phone = sc.nextLine();
                    String licenseNumber = sc.nextLine();
                    Procurator proc = new Procurator(name, SSN, address, age, gender, phone, licenseNumber);

                    // suspectLawyer
                    name = sc.nextLine();
                    SSN = sc.nextLine();
                    address = sc.nextLine();
                    age = Integer.parseInt(sc.nextLine());
                    gender = sc.nextLine();
                    phone = sc.nextLine();
                    Lawyer suspectLawyer = new Lawyer(name, SSN, address, age, gender, phone);

                    // victimLawyer
                    name = sc.nextLine();
                    SSN = sc.nextLine();
                    address = sc.nextLine();
                    age = Integer.parseInt(sc.nextLine());
                    gender = sc.nextLine();
                    phone = sc.nextLine();
                    Lawyer victimLawyer = new Lawyer(name, SSN, address, age, gender, phone);

                    // fileOpenedDate
                    LocalDate fileOpenedDate = LocalDate.parse(sc.nextLine());

                    // fileClosedDate
                    LocalDate fileClosedDate = LocalDate.parse(sc.nextLine());

                    String investigationStatement = sc.nextLine();
                    String hearingDecision = sc.nextLine();
                    StatusFile statusfile = StatusFile.valueOf(sc.nextLine().toUpperCase());

                    FilesDeputy files = new FilesDeputy(NewReport, proc, suspectLawyer, victimLawyer);
                    files.setFileOpenedDate(fileOpenedDate);
                    files.setHearingDecision(hearingDecision);
                    files.setInvestigatingStatement(investigationStatement);
                    files.setStatus(statusfile);
                    files.setFileClosedDate(fileClosedDate);
                    filesDeputy.add(files);
                } catch (DateTimeParseException e) {
                    System.out.println("DeputyFiles Error");
                }
            }

            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("DeputyFiles not found");
        }
        System.out.println("DeputyFiles loaded successfully.");

    }

    public void loadArchiveFiles() {
        try {
            Scanner sc = new Scanner(new File("FilesTXT\\Deputy\\archivedFiles.txt"));
            while (sc.hasNextLine()) {
                try {
                    // Report
                    ReportType reportTypeStr = ReportType.valueOf(sc.nextLine().toUpperCase());
                    LocalDate dateStr = LocalDate.parse(sc.nextLine());
                    CrimeType crimeTypeStr = CrimeType.valueOf(sc.nextLine().toUpperCase());
                    String crimeDetails = sc.nextLine();

                    // Assigned Officers
                    String name = sc.nextLine();
                    String SSN = sc.nextLine();
                    String address = sc.nextLine();
                    int age = Integer.parseInt(sc.nextLine());
                    String gender = sc.nextLine();
                    String phone = sc.nextLine();
                    String rank = sc.nextLine();

                    Officer assignedOfficer = new Officer(name, SSN, address, age, gender, phone, rank);
                    Report NewReport = new Report(reportTypeStr, dateStr, crimeTypeStr, crimeDetails, assignedOfficer);

                    // Victim
                    name = sc.nextLine();
                    SSN = sc.nextLine();
                    address = sc.nextLine();
                    age = Integer.parseInt(sc.nextLine());
                    gender = sc.nextLine();
                    phone = sc.nextLine();
                    String statement = sc.nextLine();

                    NewReport.setVictim(new Victim(name, SSN, address, age, gender, phone, statement));

                    // Witness
                    name = sc.nextLine();
                    SSN = sc.nextLine();
                    address = sc.nextLine();
                    age = Integer.parseInt(sc.nextLine());
                    gender = sc.nextLine();
                    phone = sc.nextLine();
                    statement = sc.nextLine();

                    NewReport.setWitness(new Witness(name, SSN, address, age, gender, phone, statement));

                    // Suspect
                    name = sc.nextLine();
                    SSN = sc.nextLine();
                    address = sc.nextLine();
                    age = Integer.parseInt(sc.nextLine());
                    gender = sc.nextLine();
                    phone = sc.nextLine();
                    String description = sc.nextLine();

                    Suspect suspect = new Suspect(name, SSN, address, age, gender, phone, description);
                    NewReport.setSuspect(suspect);
                    reports.add(NewReport);

                    // Procurator
                    name = sc.nextLine();
                    SSN = sc.nextLine();
                    address = sc.nextLine();
                    age = Integer.parseInt(sc.nextLine());
                    gender = sc.nextLine();
                    phone = sc.nextLine();
                    String licenseNumber = sc.nextLine();
                    Procurator proc = new Procurator(name, SSN, address, age, gender, phone, licenseNumber);

                    // suspectLawyer
                    name = sc.nextLine();
                    SSN = sc.nextLine();
                    address = sc.nextLine();
                    age = Integer.parseInt(sc.nextLine());
                    gender = sc.nextLine();
                    phone = sc.nextLine();
                    Lawyer suspectLawyer = new Lawyer(name, SSN, address, age, gender, phone);

                    // victimLawyer
                    name = sc.nextLine();
                    SSN = sc.nextLine();
                    address = sc.nextLine();
                    age = Integer.parseInt(sc.nextLine());
                    gender = sc.nextLine();
                    phone = sc.nextLine();
                    Lawyer victimLawyer = new Lawyer(name, SSN, address, age, gender, phone);

                    // fileOpenedDate
                    LocalDate fileOpenedDate = LocalDate.parse(sc.nextLine());

                    // fileClosedDate
                    LocalDate fileClosedDate = LocalDate.parse(sc.nextLine());

                    String investigationStatement = sc.nextLine();
                    String hearingDecision = sc.nextLine();
                    StatusFile statusfile = StatusFile.valueOf(sc.nextLine().toUpperCase());

                    FilesDeputy files = new FilesDeputy(NewReport, proc, suspectLawyer, victimLawyer);
                    files.setFileOpenedDate(fileOpenedDate);
                    files.setHearingDecision(hearingDecision);
                    files.setInvestigatingStatement(investigationStatement);
                    files.setStatus(statusfile);
                    files.setFileClosedDate(fileClosedDate);
                    filesDeputy.add(files);
                } catch (DateTimeParseException e) {
                    System.out.println("Error");
                }
            }

            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("archivedFiles not found");
        }
        System.out.println("Archived Files loaded successfully.");

    }

    public ArrayList<Procurator> getProcurators() {
        return procurators;
    }

    public ArrayList<FilesDeputy> getArchivedFiles() {
        return archivedFiles;
    }

    public ArrayList<Lawyer> getLawyers() {
        return lawyers;
    }

    public ArrayList<Report> getReports() {
        return reports;
    }

    public void saveFiles() {
        try {
            FileWriter writer = new FileWriter("FilesTXT\\Deputy\\DeputyFiles.txt", false);
            for (int i = 0; i < filesDeputy.size(); i++) {
                writer.write(filesDeputy.get(i).toString());
            }
            writer.close();
            System.out.println("DeputyFiles saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving DeputyFiles: " + e.getMessage());
        }
    }

    public void saveArchiveFiles() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("FilesTXT\\Deputy\\archivedFiles.txt"))) {
            for (int i = 0; i < archivedFiles.size(); i++) {
                writer.write(archivedFiles.get(i).toString());
            }
            writer.flush();
            System.out.println("Files archived successfully.");
        } catch (IOException e) {
            System.out.println("Error archiving the file: " + e.getMessage());
        }
    }
}
