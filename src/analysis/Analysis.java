package analysis;

import Court.Hearing;
import Court.SuspectStatus;
import Deputy.FilesDeputy;
import Deputy.StatusFile;
import Persons.*;
import Station.CrimeType;
import Station.Report;
import Station.ReportType;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Analysis {
    ArrayList<Report> reports;
    ArrayList<Hearing> hearings;
    ArrayList<FilesDeputy> files;

    public Analysis() {
        reports = new ArrayList<>();
        hearings = new ArrayList<>();
        files = new ArrayList<>();
        loadReports();
        loadHearings();
        loadFiles();
    }

    public void runMenu() {
        loadReports();
        loadHearings();
        loadFiles();
        Scanner sc = new Scanner(System.in);

        int option;
        do {
            System.out.println("\n******************************");
            System.out.println("*      Analysis.Analysis Main Menu    *");
            System.out.println("******************************");
            System.out.println("1. Prioritizing Female Files ");
            System.out.println("2. Sort Hearings by Date ");
            System.out.println("3. Back to Main Menu");

            System.out.print("Enter your choice: ");
            option = sc.nextInt();
            sc.nextLine();
            switch (option) {
                case 1:
                    System.out.println("\n");
                    PrioritizingFemale();
                    break;
                case 2:
                    System.out.println("\n");
                    sortHearingsByDate();
                    break;
                case 3:
                    System.out.println("Returning to Main Menu...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (option != 3);
    }

    public void PrioritizingFemale() {
        int TotalFiles = 0;
        for (int i = 0; i < reports.size(); i++) {
            if (reports.get(i).getVictim().getGender().equalsIgnoreCase("Female")) {
                TotalFiles++;
                reports.get(i).displayDetails();
            }
        }
        System.out.println("Total Files: " + TotalFiles);
    }

    public void sortHearingsByDate() {
        if (hearings.isEmpty()) {
            System.out.println("No hearings available to sort.");
            return;
        }
        Collections.sort(hearings); // Sorts based on the `compareTo` method in `Hearing`
        System.out.println("Hearings sorted by date:");
        for (int i = 0; i < hearings.size(); i++) {
            hearings.get(i).displayDetails();
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

                    // Persons.Judge
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

    public String[][] getFemaleVictimFiles() {
        ArrayList<String[]> result = new ArrayList<>();
        for (Report report : reports) {
            if (report.getVictim().getGender().equalsIgnoreCase("Female")) {
                result.add(new String[] {
                        String.valueOf(report.getReportNumber()),
                        report.getCrimeType().toString(),
                        report.getCrimeDetails(),
                        report.getVictim().getName(),
                        report.getReportDate().toString()
                });
            }
        }
        return result.toArray(new String[0][0]);
    }

    public String[][] getSortedHearings() {
        ArrayList<String[]> result = new ArrayList<>();
        Collections.sort(hearings); // Assuming Hearing implements Comparable

        for (Hearing hearing : hearings) {
            result.add(new String[] {
                    hearing.getHearingDate().toString(),
                    hearing.getFiles().getReport().getSuspect().getName(),
                    hearing.getSuspectStatus().toString(),
                    hearing.getFiles().getHearingDecision()
            });
        }
        return result.toArray(new String[0][0]);
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

                    FilesDeputy newFile = new FilesDeputy(NewReport, proc, suspectLawyer, victimLawyer);
                    newFile.setFileOpenedDate(fileOpenedDate);
                    newFile.setHearingDecision(hearingDecision);
                    newFile.setInvestigatingStatement(investigationStatement);
                    newFile.setStatus(statusfile);
                    newFile.setFileClosedDate(fileClosedDate);
                    files.add(newFile);
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
}
