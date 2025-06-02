package Station;

import CRUD.CRUD;
import Court.Hearing;
import Court.SuspectStatus;
import Deputy.FilesDeputy;
import Deputy.StatusFile;
import Persons.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Station implements CRUD {
    private ArrayList<Report> reports;
    private ArrayList<Officer> officers;
    private ArrayList<Hearing> hearings;
    private ArrayList<CriminalHistories> criminalHistories;
    private boolean dataLoaded = false;
    private Prison prison;
    Scanner scanner = new Scanner(System.in);

    public Station() {
        this.reports = new ArrayList<>();
        loadReports();
        Report.resetId();
        this.officers = new ArrayList<>();
        loadOfficers();
        Officer.resetId();
        this.hearings = new ArrayList<>();
        loadHearings();
        Hearing.resetId();
        this.criminalHistories = new ArrayList<>();
        loadCriminalHistory();
        this.prison = new Prison();
        loadPrisoners(prison);
    }

    public void runMenu() {
        loadOfficers();
        loadReports();
        loadCriminalHistory();
        loadPrisoners(prison);
        loadHearings();
        dataLoaded = true;
        int option;
        do {
            System.out.println("\n******************************");
            System.out.println("*      Station Main Menu      *");
            System.out.println("******************************");
            System.out.println("1. View all reports");
            System.out.println("2. Add report");
            System.out.println("3. Update report");
            System.out.println("4. View all officers");
            System.out.println("5. Add officer");
            System.out.println("6. Remove officer");
            System.out.println("7. Update officer");
            System.out.println("8. Search in criminal history");
            System.out.println("9. add criminal to prison");
            System.out.println("10. Back to Main Menu");
            System.out.print("Enter your choice: ");
            option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
                    System.out.println("\n");
                    viewAll("report");
                    break;
                case 2:
                    System.out.println("\n");
                    add("report");
                    break;
                case 3:
                    System.out.println("\n");
                    update("report");
                    break;
                case 4:
                    System.out.println("\n");
                    viewAll("officer");
                    break;
                case 5:
                    System.out.println("\n");
                    add("officer");
                    break;
                case 6:
                    System.out.println("\n");
                    remove("officer");
                    break;
                case 7:
                    System.out.println("\n");
                    update("officer");
                    break;
                case 8:
                    System.out.println("\n");
                    searchCriminalHistory();
                    break;
                case 9:
                    add("toPrison");
                    break;
                case 10:
                    saveReports();
                    saveOfficers();
                    saveCriminalHistory();
                    savePrisoners(prison);
                    clearData();
                    System.out.println("Returning to Main Menu...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (option != 10);
    }

    private void clearData() {
        reports.clear();
        officers.clear();
        hearings.clear();
        criminalHistories.clear();
        Hearing.resetId();
        Report.resetId();
        Officer.resetId();
    }

    @Override
    public void viewAll(String viewType) {
        switch (viewType) {
            case "report":
                viewAllReports();
                break;
            case "officer":
                viewAllOfficers();
                break;
        }
    }

    @Override
    public void add(String viewType) {
        switch (viewType) {
            case "report":
                createReport();
                break;
            case "officer":
                addOfficer();
                break;
            case "toPrison":
                // addToPrison();
                break;
        }
    }

    @Override
    public void update(String viewType) {
        switch (viewType) {
            case "report":
                updateReport();
                break;
            case "officer":
                updateOfficer();
                break;
        }
    }

    @Override
    public void remove(String viewType) {
        removeOfficer();
    }

    public void viewAllReports() {
        if (reports.isEmpty()) {
            System.out.println("No reports available.");
        } else {
            for (int i = 0; i < reports.size(); i++) {
                reports.get(i).displayDetails();
            }
        }
    }

    public void createReport() {
        System.out.println("Creating a new report...");
        ReportType reportType;
        Report newReport = null;
        System.out.println("Enter report details:");
        reportType = reportType();

        System.out.println("Enter the crime date (YYYY-MM-DD):");
        String dateStr = scanner.nextLine();
        LocalDate crimeDate = LocalDate.parse(dateStr);

        System.out.println(
                "Choose the crime type:\n1.Drug\n2.Money Laundering\n3.fraud\n4.child Abuse\n5.rape\n6.kidnapping\n7.assault\n8.robbery\n9.murder");
        int crimeTypeIndex = scanner.nextInt() - 1;
        scanner.nextLine();
        CrimeType crimeType = CrimeType.values()[crimeTypeIndex];

        System.out.println("Enter crime details:");
        String crimeDetails = scanner.nextLine();

        int BadgeNo;
        Officer officerInCharge = null;
        viewAllOfficers();
        System.out.print("\nChoose which officer to assign to the report by selecting his badge number:");
        BadgeNo = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < officers.size(); i++) {
            if (BadgeNo == officers.get(i).getBadgeNumber()) {
                officerInCharge = officers.get(i);
                break;
            }
        }
        newReport = new Report(reportType, crimeDate, crimeType, crimeDetails, officerInCharge);
        reports.add(newReport);
        reportedBy(newReport);
        isSuspectDetails(newReport);
        System.out.println("Report added successfully.");
    }

    public ReportType reportType() {
        ReportType reportType = null;
        boolean invalidInput = true;

        do {
            System.out.println("Choose a report type:");
            System.out.println("1: Misdemeanor");
            System.out.println("2: Felony");
            System.out.println("3: Return to Menu");

            int type = scanner.nextInt();
            scanner.nextLine();

            switch (type) {
                case 1:
                    reportType = ReportType.MISDEMEANOR;
                    invalidInput = false;
                    break;
                case 2:
                    reportType = ReportType.FELONY;
                    invalidInput = false;
                    break;
                case 3:
                    runMenu();
                    return null;
                default:
                    System.out.println("Invalid report type. Please try again.");
            }
        } while (invalidInput);

        return reportType;
    }

    public void reportedBy(Report newReport) {
        System.out.println("Enter Victim's Details:");
        System.out.println("Victim Name:");
        String name = scanner.nextLine();

        System.out.println("Victim SSN:");
        String SSN = scanner.nextLine();

        System.out.println("Victim Address:");
        String address = scanner.nextLine();

        System.out.println("Victim Age:");
        int age = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Victim Gender:");
        String gender = scanner.nextLine();

        System.out.println("Victim Phone:");
        String phone = scanner.nextLine();

        System.out.println("Victim Statement:");
        String statement = scanner.nextLine();

        Victim v = new Victim(name, SSN, address, age, gender, phone, statement);
        newReport.setVictim(v);
        System.out.println("Victim added successfully.");

        System.out.println("Enter Witness's Details:");
        System.out.println("Witness Name:");
        name = scanner.nextLine();

        System.out.println("Witness SSN:");
        SSN = scanner.nextLine();

        System.out.println("Witness Address:");
        address = scanner.nextLine();

        System.out.println("Witness Age:");
        age = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Witness Gender:");
        gender = scanner.nextLine();

        System.out.println("Witness Phone:");
        phone = scanner.nextLine();

        System.out.println("Witness Statement:");
        statement = scanner.nextLine();

        Witness w = new Witness(name, SSN, address, age, gender, phone, statement);
        newReport.setWitness(w);
        System.out.println("Witness added successfully.");
    }

    public void isSuspectDetails(Report newReport) {
        System.out.println("Enter the suspect details");
        System.out.println("Enter Suspect's Name:");
        String name = scanner.nextLine();
        System.out.println("Enter Suspect SSN :");
        String ssn = scanner.nextLine();
        System.out.println("Enter Suspect Address :");
        String address = scanner.nextLine();
        System.out.println("Enter Suspect Age :");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter Suspect's gender:");
        String gender = scanner.nextLine();
        System.out.println("Enter Suspect Phone:");
        String phone = scanner.nextLine();
        System.out.println("Enter Suspect's description:");
        String description = scanner.nextLine();
        Suspect suspect = new Suspect(name, ssn, address, age, gender, phone, description);
        newReport.setSuspect(suspect);
        System.out.println("Suspect's name added successfully");
    }

    public void addOfficer() {
        System.out.println("Enter Officer details:");
        System.out.println("Officer Name:");
        String name = scanner.nextLine();

        System.out.println("Officer SSN:");
        String SSN = scanner.nextLine();

        System.out.println("Officer Address:");
        String address = scanner.nextLine();

        System.out.println("Officer Age:");
        int age = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Officer Gender:");
        String gender = scanner.nextLine();

        System.out.println("Officer Phone:");
        String phone = scanner.nextLine();

        System.out.println("Officer Rank:");
        String rank = scanner.nextLine();

        Officer newOfficer = new Officer(name, SSN, address, age, gender, phone, rank);
        officers.add(newOfficer);

        System.out.println("Officer added successfully:");
    }

    public void removeOfficer() {
        if (officers.isEmpty()) {
            System.out.println("Officers not found.");
        } else {
            System.out.println("Listing all Officers...\n");
            viewAllOfficers();
            System.out.println("\nChoose badge Number to remove officer: ");
            int badgeNo = scanner.nextInt();
            for (int i = 0; i < officers.size(); i++) {
                if (officers.get(i).getBadgeNumber() == badgeNo) {
                    officers.remove(i);
                    System.out.println("Officer removed successfully.");
                }
            }
        }
    }

    public void viewAllOfficers() {
        if (officers.isEmpty()) {
            System.out.println("\nNo officers available.");
        } else {
            for (int i = 0; i < officers.size(); i++) {
                officers.get(i).displayDetails();
            }
        }
    }

    public void updateOfficer() {
        if (officers.isEmpty()) {
            System.out.println("No Officers available.");
            return;
        } else {
            viewAllOfficers();
            System.out.println("\nEnter the Officer Badge Number to update: ");
            int BadgeNumber = scanner.nextInt();
            for (int i = 0; i < officers.size(); i++) {
                Officer officer = officers.get(i);
                if (officer.getBadgeNumber() == BadgeNumber) {
                    System.out.println("\nExisting officer details:");
                    officer.displayDetails();
                    System.out.println("\nSelect details to update: ");
                    System.out.println("1. Rank");
                    System.out.println("2. Address");
                    System.out.println("3. Phone");
                    System.out.println("4. Return to Station menu");
                    int choice = scanner.nextInt();
                    scanner.nextLine();
                    switch (choice) {
                        case 1:
                            System.out.println("Enter new rank:");
                            String newRank = scanner.nextLine();
                            officer.setRank(newRank);
                            break;
                        case 2:
                            System.out.println("Enter new address:");
                            String newAddress = scanner.nextLine();
                            officer.setAddress(newAddress);
                            break;
                        case 3:
                            System.out.println("Enter new phone:");
                            String newPhone = scanner.nextLine();
                            officer.setPhone(newPhone);
                            break;
                        case 4:
                            runMenu();
                            break;
                        default:
                            System.out.println("Invalid option selected.");
                            break;
                    }
                }
                System.out.println("officer updated successfully.");
            }
        }
    }

    public void updateReport() {
        System.out.println("Listing reports: ");
        for (int i = 0; i < reports.size(); i++) {
            reports.get(i).displayDetails();
        }
        if (reports.isEmpty()) {
            System.out.println("No reports available to update.");
        } else {
            System.out.println("\nEnter the report number to update: ");
            int reportNumber = scanner.nextInt();
            scanner.nextLine();
            for (int i = 0; i < reports.size(); i++) {
                Report report = reports.get(i);
                if (report.getReportNumber() == reportNumber) {
                    System.out.println("\nExisting report details:");
                    report.displayDetails();
                    System.out.println("\nSelect details to update: ");
                    System.out.println("1. Crime Details");
                    System.out.println("2. Crime Type");
                    System.out.println("3. Return to Station menu");
                    int choice = scanner.nextInt();
                    scanner.nextLine();
                    switch (choice) {
                        case 1:
                            System.out.println("\nEnter new crime details:");
                            String newDetails = scanner.nextLine();
                            report.setCrimeDetails(newDetails);
                            System.out.println("crime details updated successfully.");
                            break;
                        case 2:
                            System.out.println("\nSelect new crime type:");
                            CrimeType[] values = CrimeType.values();
                            for (int j = 0; j < values.length; j++) {
                                CrimeType type = values[j];
                                System.out.println(type.ordinal() + 1 + ". " + type);
                            }
                            int typeIndex = scanner.nextInt() - 1;
                            scanner.nextLine();
                            CrimeType newType = CrimeType.values()[typeIndex];
                            report.setCrimeType(newType);
                            break;
                        case 3:
                            runMenu();
                            break;
                        default:
                            System.out.println("Invalid option selected.");
                            break;
                    }
                    System.out.println("Report updated successfully.");
                    return;
                }
            }
        }
    }

    public void searchCriminalHistory() {
        boolean found = false;
        System.out.println("Enter SSN to search:");
        String ssn = scanner.nextLine();
        for (int i = 0; i < criminalHistories.size(); i++) {
            if (criminalHistories.get(i).getSSN().equals(ssn)) {
                criminalHistories.get(i).displayDetails();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No criminal record found for the SSN: " + ssn);
        }
    }

    public boolean addToPrison(String ssn) {
        for (Hearing hearing : hearings) {
            if (hearing.getSuspectStatus() == SuspectStatus.GUILTY) {
                Suspect suspect = hearing.getFiles().getReport().getSuspect();
                if (suspect.getSSN().equals(ssn)) {
                    prison.addPrisoner(suspect);
                    updateCriminalHistory(hearing.getFiles().getReport(), hearing);
                    return true;
                }
            }
        }
        return false;
    }

    private void updateCriminalHistory(Report report, Hearing hearing) {
        criminalHistories.add(new CriminalHistories(report.getReportNumber(), report.getSuspect().getSSN(),
                report.getSuspect().getName(), report.getCrimeType(), report.getCrimeDetails(),
                hearing.getPenaltyDuration()));
    }

    // File operations
    public void loadReports() {
        try {
            Scanner sc = new Scanner(new File("FilesTXT\\Stations\\Reports.txt"));
            while (sc.hasNextLine()) {
                try {
                    ReportType reportTypeStr = ReportType.valueOf(sc.nextLine().toUpperCase());
                    LocalDate dateStr = LocalDate.parse(sc.nextLine());
                    CrimeType crimeTypeStr = CrimeType.valueOf(sc.nextLine().toUpperCase());
                    String crimeDetails = sc.nextLine();

                    String name = sc.nextLine();
                    String SSN = sc.nextLine();
                    String address = sc.nextLine();
                    int age = Integer.parseInt(sc.nextLine());
                    String gender = sc.nextLine();
                    String phone = sc.nextLine();
                    String rank = sc.nextLine();

                    Officer assignedOfficer = new Officer(name, SSN, address, age, gender, phone, rank);
                    Report NewReport = new Report(reportTypeStr, dateStr, crimeTypeStr, crimeDetails, assignedOfficer);

                    name = sc.nextLine();
                    SSN = sc.nextLine();
                    address = sc.nextLine();
                    age = Integer.parseInt(sc.nextLine());
                    gender = sc.nextLine();
                    phone = sc.nextLine();
                    String statement = sc.nextLine();

                    NewReport.setVictim(new Victim(name, SSN, address, age, gender, phone, statement));

                    name = sc.nextLine();
                    SSN = sc.nextLine();
                    address = sc.nextLine();
                    age = Integer.parseInt(sc.nextLine());
                    gender = sc.nextLine();
                    phone = sc.nextLine();
                    statement = sc.nextLine();

                    NewReport.setWitness(new Witness(name, SSN, address, age, gender, phone, statement));

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

    public void saveReports() {
        try (FileWriter writer = new FileWriter("FilesTXT\\Stations\\Reports.txt", false)) {
            for (int i = 0; i < reports.size(); i++) {
                writer.write(reports.get(i).toString());
                writer.write("\n");
            }
            writer.close();
            System.out.println("Reports saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving reports: " + e.getMessage());
        }
    }

    public void loadOfficers() {
        Officer.resetId();
        try {
            Scanner sc = new Scanner(new File("FilesTXT\\Stations\\Officers.txt"));
            while (sc.hasNextLine()) {
                String name = sc.nextLine();
                String SSN = sc.nextLine();
                String address = sc.nextLine();
                int age = Integer.parseInt(sc.nextLine());
                String gender = sc.nextLine();
                String phone = sc.nextLine();
                String rank = sc.nextLine();

                Officer officer = new Officer(name, SSN, address, age, gender, phone, rank);
                officers.add(officer);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: Officers.txt");
        }
        System.out.println("Officers loaded successfully.");
    }

    public void saveOfficers() {
        try {
            FileWriter writer = new FileWriter("FilesTXT\\Stations\\Officers.txt", false);
            for (int i = 0; i < officers.size(); i++) {
                writer.write(officers.get(i).toString());
                writer.write("\n");
            }
            writer.close();
            System.out.println("Officers saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving officers: " + e.getMessage());
        }
    }

    public void loadCriminalHistory() {
        try {
            Scanner sc = new Scanner(new File("FilesTXT\\Stations\\CriminalHistory.txt"));
            while (sc.hasNextLine()) {
                int reportNum = Integer.parseInt(sc.nextLine());
                String SSN = sc.nextLine();
                String suspectName = sc.nextLine();
                CrimeType crimeTypeStr = CrimeType.valueOf(sc.nextLine().toUpperCase());
                String crimedetails = sc.nextLine();
                String penalty = sc.nextLine();
                criminalHistories
                        .add(new CriminalHistories(reportNum, SSN, suspectName, crimeTypeStr, crimedetails, penalty));
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("CriminalHistory not found: CriminalHistory.txt");
        }
        System.out.println("CriminalHistory loaded successfully.");
    }

    public void saveCriminalHistory() {
        try {
            FileWriter writer = new FileWriter("FilesTXT\\Stations\\CriminalHistory.txt", false);
            for (int i = 0; i < criminalHistories.size(); i++) {
                writer.write(criminalHistories.get(i).toString());
            }
            writer.close();
            System.out.println("CriminalHistory saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving CriminalHistory: " + e.getMessage());
        }
    }

    public void loadPrisoners(Prison prison) {
        try {
            Scanner sc = new Scanner(new File("FilesTXT\\Stations\\Prisoners.txt"));
            while (sc.hasNextLine()) {
                String name = sc.nextLine();
                String SSN = sc.nextLine();
                String address = sc.nextLine();
                int age = Integer.parseInt(sc.nextLine());
                String gender = sc.nextLine();
                String phone = sc.nextLine();
                String description = sc.nextLine();
                Suspect prisoner = new Suspect(name, SSN, address, age, gender, phone, description);
                prison.addPrisoner(prisoner);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: Prisoners.txt");
        }
        System.out.println("Prisoners loaded successfully.");
    }

    public void savePrisoners(Prison prison) {
        try {
            FileWriter writer = new FileWriter("FilesTXT\\Stations\\Prisoners.txt", false);
            for (int i = 0; i < prison.getPrisoners().size(); i++) {
                writer.write(prison.getPrisoners().get(i).toString());
            }
            writer.close();
            System.out.println("Prisoners saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving Prisoners: " + e.getMessage());
        }
    }

    public void loadHearings() {
        try {
            Scanner sc = new Scanner(new File("FilesTXT\\Court\\Hearing.txt"));
            while (sc.hasNextLine()) {
                try {
                    LocalDate hearingDate = LocalDate.parse(sc.nextLine());

                    ReportType reportTypeStr = ReportType.valueOf(sc.nextLine().toUpperCase());
                    LocalDate dateStr = LocalDate.parse(sc.nextLine());
                    CrimeType crimeTypeStr = CrimeType.valueOf(sc.nextLine().toUpperCase());
                    String crimeDetails = sc.nextLine();

                    String name = sc.nextLine();
                    String SSN = sc.nextLine();
                    String address = sc.nextLine();
                    int age = Integer.parseInt(sc.nextLine());
                    String gender = sc.nextLine();
                    String phone = sc.nextLine();
                    String rank = sc.nextLine();

                    Officer assignedOfficer = new Officer(name, SSN, address, age, gender, phone, rank);
                    Report NewReport = new Report(reportTypeStr, dateStr, crimeTypeStr, crimeDetails, assignedOfficer);

                    name = sc.nextLine();
                    SSN = sc.nextLine();
                    address = sc.nextLine();
                    age = Integer.parseInt(sc.nextLine());
                    gender = sc.nextLine();
                    phone = sc.nextLine();
                    String statement = sc.nextLine();

                    NewReport.setVictim(new Victim(name, SSN, address, age, gender, phone, statement));

                    name = sc.nextLine();
                    SSN = sc.nextLine();
                    address = sc.nextLine();
                    age = Integer.parseInt(sc.nextLine());
                    gender = sc.nextLine();
                    phone = sc.nextLine();
                    statement = sc.nextLine();

                    NewReport.setWitness(new Witness(name, SSN, address, age, gender, phone, statement));

                    name = sc.nextLine();
                    SSN = sc.nextLine();
                    address = sc.nextLine();
                    age = Integer.parseInt(sc.nextLine());
                    gender = sc.nextLine();
                    phone = sc.nextLine();
                    String description = sc.nextLine();

                    Suspect suspect = new Suspect(name, SSN, address, age, gender, phone, description);
                    NewReport.setSuspect(suspect);

                    name = sc.nextLine();
                    SSN = sc.nextLine();
                    address = sc.nextLine();
                    age = Integer.parseInt(sc.nextLine());
                    gender = sc.nextLine();
                    phone = sc.nextLine();
                    String licenseNumber = sc.nextLine();
                    Procurator proc = new Procurator(name, SSN, address, age, gender, phone, licenseNumber);

                    name = sc.nextLine();
                    SSN = sc.nextLine();
                    address = sc.nextLine();
                    age = Integer.parseInt(sc.nextLine());
                    gender = sc.nextLine();
                    phone = sc.nextLine();
                    Lawyer suspectLawyer = new Lawyer(name, SSN, address, age, gender, phone);

                    name = sc.nextLine();
                    SSN = sc.nextLine();
                    address = sc.nextLine();
                    age = Integer.parseInt(sc.nextLine());
                    gender = sc.nextLine();
                    phone = sc.nextLine();
                    Lawyer victimLawyer = new Lawyer(name, SSN, address, age, gender, phone);

                    LocalDate fileOpenedDate = LocalDate.parse(sc.nextLine());
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

                    name = sc.nextLine();
                    SSN = sc.nextLine();
                    address = sc.nextLine();
                    age = Integer.parseInt(sc.nextLine());
                    gender = sc.nextLine();
                    phone = sc.nextLine();
                    Judge j = new Judge(name, SSN, address, age, gender, phone);

                    SuspectStatus suspectStatus = SuspectStatus.valueOf(sc.nextLine().toUpperCase());
                    String penalty = sc.nextLine();

                    Hearing h = new Hearing(hearingDate, j, files);
                    h.setSuspectStatus(suspectStatus);
                    h.setPenaltyDuration(penalty);
                    hearings.add(h);
                } catch (DateTimeParseException e) {
                    System.out.println("Error");
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: FilesTXT\\Court\\Hearing.txt");
        }
        System.out.println("hearing loaded successfully.");
    }

    // GUI Helper methods
    public Report getReportById(int id) {
        for (Report report : reports) {
            if (report.getReportNumber() == id) {
                return report;
            }
        }
        return null;
    }

    public void updateReport(Report updatedReport) {
        for (int i = 0; i < reports.size(); i++) {
            if (reports.get(i).getReportNumber() == updatedReport.getReportNumber()) {
                reports.set(i, updatedReport);
                return;
            }
        }
    }

    public Officer getOfficerByBadge(int badgeNumber) {
        for (Officer officer : officers) {
            if (officer.getBadgeNumber() == badgeNumber) {
                return officer;
            }
        }
        return null;
    }

    public void updateOfficer(Officer updatedOfficer) {
        for (int i = 0; i < officers.size(); i++) {
            if (officers.get(i).getBadgeNumber() == updatedOfficer.getBadgeNumber()) {
                officers.set(i, updatedOfficer);
                return;
            }
        }
    }

    public String[] getOfficerNames() {
        return officers.stream().map(Officer::getName).toArray(String[]::new);
    }

    public Officer getOfficerByName(String name) {
        for (Officer officer : officers) {
            if (officer.getName().equals(name)) {
                return officer;
            }
        }
        return null;
    }

    public ArrayList<CriminalHistories> getCriminalHistories() {
        return criminalHistories;
    }

    public boolean removeOfficer(String officerName) {
        for (int i = 0; i < officers.size(); i++) {
            if (officers.get(i).getName().equals(officerName)) {
                officers.remove(i);
                return true;
            }
        }
        return false;
    }

    public void addReport(Report newReport) {
        reports.add(newReport);
    }

    public void displayAllCriminal() {
        for (int j = 0; j < criminalHistories.size(); j++) {
            criminalHistories.get(j).displayDetails();
        }
    }

    public String[][] getReportsAsArray() {
        String[][] data = new String[reports.size()][8];
        for (int i = 0; i < reports.size(); i++) {
            Report report = reports.get(i);
            data[i][0] = String.valueOf(report.getReportNumber());
            data[i][1] = report.getCrimeType().toString();
            data[i][2] = report.getCrimeDetails();
            data[i][3] = report.getOfficerInCharge().getName();
            data[i][4] = report.getReportDate().toString();
            data[i][5] = (report.getSuspect() != null) ? report.getSuspect().getName() : "N/A";
            data[i][6] = (report.getVictim() != null) ? report.getVictim().getName() : "N/A";
            data[i][7] = (report.getWitness() != null) ? report.getWitness().getName() : "N/A";
        }
        return data;
    }

    public String[][] getOfficersAsArray() {
        String[][] data = new String[officers.size()][5];
        for (int i = 0; i < officers.size(); i++) {
            Officer officer = officers.get(i);
            data[i][0] = String.valueOf(officer.getBadgeNumber());
            data[i][1] = officer.getName();
            data[i][2] = officer.getRank();
            data[i][3] = officer.getPhone();
            data[i][4] = officer.getAddress();
        }
        return data;
    }

    public void addOfficer(Officer newOfficer) {
        officers.add(newOfficer);
    }
}