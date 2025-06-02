package Court;

import CRUD.CRUD;
import Deputy.*;
import Persons.Judge;
import Persons.Lawyer;
import Persons.Officer;
import Persons.Procurator;
import Persons.Suspect;
import Persons.Victim;
import Persons.Witness;
import Station.CrimeType;
import Station.Report;
import Station.ReportType;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Court implements CRUD {
    private ArrayList<Hearing> hearings;
    private ArrayList<FilesDeputy> filesDeputy;
    private ArrayList<Judge> judges;
    Scanner scanner = new Scanner(System.in);
    private Hearing hearing;

    public Court() {
        this.hearings = new ArrayList<>();
        this.filesDeputy = new ArrayList<>();
        this.judges = new ArrayList<>();
        loadFiles();
        loadHearings();
        loadJudges();
        Court court = this;
        Hearing.resetId();
        FilesDeputy.resetId();
        Judge.resetId();
    }

    public void runMenu() {
        loadFiles();
        loadHearings();
        loadJudges();
        int option;
        do {
            System.out.println("\n******************************");
            System.out.println("*  Court Main Menu  *");
            System.out.println("******************************");
            System.out.println("1. View hearings");
            System.out.println("2. Schedule hearing");
            System.out.println("3. Update hearings");
            System.out.println("4. View files");
            System.out.println("5. View all judges");
            System.out.println("6. Add judge");
            System.out.println("7. remove judge");
            System.out.println("8. update judge");
            System.out.println("9. Back to Main Menu");
            System.out.print("Enter your choice: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    System.out.println("\n");
                    viewAll("hearings");
                    break;
                case 2:
                    System.out.println("\n");
                    add("hearings");
                    break;
                case 3:
                    System.out.println("\n");
                    update("hearings");
                    break;
                case 4:
                    System.out.println("\n");
                    viewAll("files");
                    break;
                case 5:
                    System.out.println("\n");
                    viewAll("judge");
                    break;
                case 6:
                    System.out.println("\n");
                    add("judge");
                    break;
                case 7:
                    System.out.println("\n");
                    remove("judge");
                    break;
                case 8:
                    System.out.println("\n");
                    update("judge");
                    break;
                case 9:
                    saveHearings();
                    saveFiles();
                    saveJudges();
                    clearData();
                    System.out.println("Returning to Main Menu...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (option != 9);
    }

    private void clearData() {
        filesDeputy.clear();
        hearings.clear();
        judges.clear();
        Hearing.resetId();
        FilesDeputy.resetId();
        Judge.resetId();
    }

    @Override
    public void viewAll(String viewType) {
        switch (viewType) {
            case "hearings":
                viewHearings();
                break;
            case "files":
                viewFiles();
                break;
            case "judge":
                ViewAllJudges();
                break;
        }
    }

    @Override
    public void add(String viewType) {
        switch (viewType) {
            case "hearings":
                scheduleHearing();
                break;
            case "judge":
                AddJudge();
                break;
        }
    }

    @Override
    public void update(String viewType) {
        switch (viewType) {
            case "hearings":
                updateHearing(hearing);
                break;
            case "judge":
                UpdateJudge();
                break;
        }
    }

    @Override
    public void remove(String viewType) {
        RemoveJudge();
    }

    // --------------------------------------------------------------------

    public void viewHearings() {
        if (hearings.isEmpty()) {
            System.out.println("No hearings scheduled.");
        } else {
            for (int i = 0; i < hearings.size(); i++) {
                hearings.get(i).displayDetails();
            }
        }
    }

    public void scheduleHearing() {
        System.out.println("hearing details:");
        System.out.println("Enter the date for the hearing (YYYY-MM-DD):");
        String dateStr = scanner.nextLine();
        LocalDate hearingDate = LocalDate.parse(dateStr);
        viewFiles();
        System.out.println("\nSelect a file for the hearing by file number:");
        int fileNumber = scanner.nextInt();
        scanner.nextLine();
        FilesDeputy selectedFile = null;
        for (int i = 0; i < filesDeputy.size(); i++) {
            if (filesDeputy.get(i).getFileNumber() == fileNumber) {
                selectedFile = filesDeputy.get(i);
                break;
            }
        }
        ViewAllJudges();
        System.out.println("\nSelect a judge for the hearing by judge ID:");
        int judgeId = scanner.nextInt();
        scanner.nextLine();
        Judge selectedJudge = null;
        for (int i = 0; i < judges.size(); i++) {
            if (judges.get(i).getJ_ID() == judgeId) {
                selectedJudge = judges.get(i);
                break;
            }
        }
        if (selectedFile != null && selectedJudge != null) {
            Hearing newHearing = new Hearing(hearingDate, selectedJudge, selectedFile);
            hearings.add(newHearing);
            System.out.println("Hearing scheduled successfully.");
        } else {
            System.out.println("Invalid file number or judge ID.");
        }
    }

    public void updateHearing(Hearing hearing) {
        viewHearings();
        System.out.println("\nEnter the hearing ID to update:");
        int hearingId = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < hearings.size(); i++) {
            if (hearings.get(i).getHearingId() == hearingId) {
                hearings.get(i).displayDetails();
                System.out.println("\nChoose what to update on:");
                System.out.println("1. hearing date:");
                System.out.println("2. file status:");
                System.out.println("3. penalty duration:");
                System.out.println("4. suspect status:");
                System.out.println("5. judge assigned for the case:");
                System.out.println("6. hearing decision");
                System.out.println("7. Return to Court menu");
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        System.out.println("\nEnter new hearing date:");
                        String dateStr = scanner.nextLine();
                        LocalDate newDetails = LocalDate.parse(dateStr);
                        hearings.get(i).setHearingDate(newDetails);
                        break;
                    case 2:
                        System.out.println("\nChoose from the list to add file status:");
                        System.out.println("1 for UNDER_INVESTIGATION");
                        System.out.println("2 for RESOLVED");
                        System.out.println("3 for DISMISSED");
                        hearings.get(i).getFiles().setStatus(StatusFile.values()[scanner.nextInt() - 1]);
                        scanner.nextLine();
                        break;
                    case 3:
                        System.out.println("\n Add penalty duration:");
                        String newPenaltyDuration = scanner.nextLine();
                        hearings.get(i).setPenaltyDuration(newPenaltyDuration);
                        break;
                    case 4:
                        System.out.println("\nEnter the new suspect status:");
                        System.out.println("1 for GUILTY");
                        System.out.println("2 for NOT GUILTY");
                        hearings.get(i).setSuspectStatus(SuspectStatus.values()[scanner.nextInt() - 1]);
                        scanner.nextLine();
                        break;
                    case 5:
                        ViewAllJudges();
                        System.out.println("\nEnter the new judge assigned for the case:");
                        int newJudgeId = scanner.nextInt();
                        scanner.nextLine();
                        for (int j = 0; j < judges.size(); j++) {
                            if (judges.get(j).getJ_ID() == newJudgeId) {
                                hearings.get(i).setJudge(judges.get(j));
                                break;
                            }
                        }
                        break;
                    case 6:
                        System.out.println("\n Update hearingDecision:");
                        String hearingDecision = scanner.nextLine();
                        filesDeputy.get(i).setHearingDecision(hearingDecision);
                        break;
                    case 7:
                        runMenu();
                        break;
                    default:
                        System.out.println("Invalid option selected.");
                        break;
                }
                System.out.println("Hearing updated successfully.");
                return;
            }
        }
        System.out.println("Hearing not found.");
    }

    public void viewFiles() {
        if (filesDeputy.isEmpty()) {
            System.out.println("No files available.");
        } else {
            for (int i = 0; i < filesDeputy.size(); i++) {
                filesDeputy.get(i).displayDetails();
            }
        }
    }

    public void ViewAllJudges() {
        if (judges.isEmpty()) {
            System.out.println("No judges available.");
        } else {
            for (int i = 0; i < judges.size(); i++) {
                judges.get(i).displayDetails();
            }
        }
    }

    public void AddJudge() {
        System.out.println("Enter the details of the new judge:");

        System.out.println("judge Name:");
        String name = scanner.nextLine();

        System.out.println("judge SSN:");
        String ssn = scanner.nextLine();

        System.out.println("judge address:");
        String address = scanner.nextLine();

        System.out.println("judge Age:");
        int age = scanner.nextInt();
        scanner.nextLine();

        System.out.println("judge gender:");
        String gender = scanner.nextLine();

        System.out.println("judge Phone:");
        String phone = scanner.nextLine();

        Judge newjudge = new Judge(name, ssn, address, age, gender, phone);
        judges.add(newjudge);
        System.out.println("Judge added successfully.");

    }

    public void RemoveJudge() {
        ViewAllJudges();
        System.out.println("\nEnter the ID of the judge to remove:");
        int J_ID = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < judges.size(); i++) {
            if (judges.get(i).getJ_ID() == J_ID) {
                judges.remove(i);
                System.out.println("Judge removed successfully.");
                return;
            }
        }
    }

    public void UpdateJudge() {
        ViewAllJudges();
        System.out.println("Enter the ID of the judge to update:");
        int J_ID = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < judges.size(); i++) {
            if (judges.get(i).getJ_ID() == J_ID) {
                System.out.println("Existing judge details:");
                judges.get(i).displayDetails();
                System.out.println("Select what do you want to update: ");
                System.out.println("1.address:");
                System.out.println("2.Phone:");
                System.out.println("3.back to Court menu");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        System.out.println("Enter new address:");
                        String newAddress = scanner.nextLine();
                        judges.get(i).setAddress(newAddress);
                        break;
                    case 2:
                        System.out.println("Enter new phone:");
                        String newPhone = scanner.nextLine();
                        judges.get(i).setPhone(newPhone);
                        break;
                    case 3:
                        runMenu();
                        break;
                    default:
                        System.out.println("Invalid option selected.");
                        break;
                }

            }

        }

    }

    // Files
    public void loadJudges() {
        try {
            Scanner sc = new Scanner(new File("FilesTXT\\Court\\Judge.txt"));
            while (sc.hasNextLine()) {

                String name = sc.nextLine();
                String SSN = sc.nextLine();
                String address = sc.nextLine();
                int age = Integer.parseInt(sc.nextLine());
                String gender = sc.nextLine();
                String phone = sc.nextLine();

                Judge judge = new Judge(name, SSN, address, age, gender, phone);
                judges.add(judge);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("File Judge not found");
        }
        System.out.println("Judges loaded successfully.");
    }

    public void saveJudges() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("FilesTXT\\Court\\Judge.txt"))) {
            for (int i = 0; i < judges.size(); i++) {
                writer.write(judges.get(i).toString());
                writer.write("\n");
            }
            writer.flush();
            System.out.println("Judges saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving judges: " + e.getMessage());
        }
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
                    System.out.println("File Reports not found");
                }
            }

            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("File Reports not found");
        }
        System.out.println("Files loaded successfully.");

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

                    // Judge
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

    public void saveHearings() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("FilesTXT\\Court\\Hearing.txt"))) {
            for (int i = 0; i < hearings.size(); i++) {
                writer.write(hearings.get(i).toString());
                writer.write("\n");
            }
            writer.flush();
            System.out.println("hearing saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving hearing : " + e.getMessage());
        }
    }

    public ArrayList<FilesDeputy> getFilesDeputy() {
        return filesDeputy;
    }

    public ArrayList<Hearing> getHearings() {
        return hearings;
    }

    public ArrayList<Judge> getJudges() {
        return judges;
    }

    public String[][] getHearingsAsArray() {
        String[][] data = new String[hearings.size()][6];
        for (int i = 0; i < hearings.size(); i++) {
            Hearing hearing = hearings.get(i);
            data[i][0] = String.valueOf(hearing.getHearingId());
            data[i][1] = hearing.getHearingDate().toString();
            data[i][2] = hearing.getJudge() != null ? hearing.getJudge().getName() : "N/A";
            data[i][3] = hearing.getFiles() != null ? String.valueOf(hearing.getFiles().getFileNumber()) : "N/A";
            data[i][4] = hearing.getSuspectStatus() != null ? hearing.getSuspectStatus().toString() : "N/A";
            data[i][5] = hearing.getPenaltyDuration();
        }
        return data;
    }

    public Hearing getHearingById(int hearingId) {
        for (Hearing hearing : hearings) {
            if (hearing.getHearingId() == hearingId) {
                return hearing;
            }
        }
        return null;
    }

    public void addJudge(Judge newJudge) {
        if (!judges.contains(newJudge)) {
            judges.add(newJudge); // Add judge to the list
            System.out.println("Judge added: " + newJudge.getName());
        } else {
            System.out.println("Judge already exists.");
        }
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
}
