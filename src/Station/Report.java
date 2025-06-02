package Station;

import Persons.*;
import java.time.LocalDate;

public class Report {
    private static int nextReportId = 1;
    private int reportNumber;
    private ReportType reportType;
    private LocalDate reportDate;
    private CrimeType crimeType;
    private String crimeDetails;
    private Officer officerInCharge;
    private Victim victim;
    private Witness witness;
    private Suspect suspect;

    public Report(ReportType reportType, LocalDate reportDate, CrimeType crimeType, String crimeDetails, Officer officerInCharge) {
        this.reportNumber = nextReportId++;
        this.reportType = reportType;
        this.reportDate = reportDate;
        this.crimeType = crimeType;
        this.crimeDetails = crimeDetails;
        this.officerInCharge = officerInCharge;
    }

    public static void resetId() {
        nextReportId = 1;
    }

    // Getters
    public int getReportNumber() { return reportNumber; }
    public ReportType getReportType() { return reportType; }
    public LocalDate getReportDate() { return reportDate; }
    public CrimeType getCrimeType() { return crimeType; }
    public String getCrimeDetails() { return crimeDetails; }
    public Officer getOfficerInCharge() { return officerInCharge; }
    public Victim getVictim() { return victim; }
    public Witness getWitness() { return witness; }
    public Suspect getSuspect() { return suspect; }

    // Setters
    public void setReportType(ReportType reportType) { this.reportType = reportType; }
    public void setReportDate(LocalDate reportDate) { this.reportDate = reportDate; }
    public void setCrimeType(CrimeType crimeType) { this.crimeType = crimeType; }
    public void setCrimeDetails(String crimeDetails) { this.crimeDetails = crimeDetails; }
    public void setOfficerInCharge(Officer officerInCharge) { this.officerInCharge = officerInCharge; }
    public void setVictim(Victim victim) { this.victim = victim; }
    public void setWitness(Witness witness) { this.witness = witness; }
    public void setSuspect(Suspect suspect) { this.suspect = suspect; }

    public void displayDetails() {
        System.out.println("Report #: " + reportNumber);
        System.out.println("Type: " + reportType);
        System.out.println("Date: " + reportDate);
        System.out.println("Crime Type: " + crimeType);
        System.out.println("Details: " + crimeDetails);
        System.out.println("Officer: " + (officerInCharge != null ? officerInCharge.getName() : "N/A"));
        System.out.println("Victim: " + (victim != null ? victim.getName() : "N/A"));
        System.out.println("Witness: " + (witness != null ? witness.getName() : "N/A"));
        System.out.println("Suspect: " + (suspect != null ? suspect.getName() : "N/A"));
        System.out.println("-------------------");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(reportType).append("\n");
        sb.append(reportDate).append("\n");
        sb.append(crimeType).append("\n");
        sb.append(crimeDetails).append("\n");
        
        if (officerInCharge != null) sb.append(officerInCharge.toString());
        if (victim != null) sb.append(victim.toString());
        if (witness != null) sb.append(witness.toString());
        if (suspect != null) sb.append(suspect.toString());
        
        return sb.toString();
    }
} 