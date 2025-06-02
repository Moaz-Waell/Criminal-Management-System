package Deputy;

import Persons.Lawyer;
import Persons.Procurator;
import Station.Report;
import java.time.LocalDate;

public class FilesDeputy {
    private static int nextFileNumber = 1;
    private int fileNumber;
    private LocalDate fileOpenedDate;
    private LocalDate fileClosedDate;
    private StatusFile status;
    private Report report;
    private Procurator procurator;
    private Lawyer suspectLawyer;
    private Lawyer victimLawyer;
    private String investigatingStatement;
    private String hearingDecision;

    public FilesDeputy(Report report, Procurator procurator, Lawyer suspectLawyer, Lawyer victimLawyer) {
        this.fileNumber = nextFileNumber++;
        this.fileOpenedDate = LocalDate.now();
        this.fileClosedDate = this.fileOpenedDate.plusDays(10); // Set fileClosedDate to fileOpenedDate + 10 days
        this.status = null;
        this.report = report;
        this.procurator = procurator;
        this.suspectLawyer = suspectLawyer;
        this.victimLawyer = victimLawyer;
        this.investigatingStatement = "No Investigating Statement";
        this.hearingDecision = "No Hearing Decision";
    }

    public static void resetId() {
        nextFileNumber = 1;
    }

    public String getInvestigatingStatement() {
        return investigatingStatement;
    }

    public void setHearingDecision(String hearingDecision) {
        this.hearingDecision = hearingDecision;
    }

    public int getFileNumber() {
        return fileNumber;
    }

    public void setFileOpenedDate(LocalDate fileOpenedDate) {
        this.fileOpenedDate = fileOpenedDate;
    }

    public void setFileClosedDate(LocalDate fileClosedDate) {
        this.fileClosedDate = fileClosedDate;
    }

    public Report getReport() {
        return report;
    }

    public Lawyer getSuspectLawyer() {
        return suspectLawyer;
    }

    public Lawyer getVictimLawyer() {
        return victimLawyer;
    }

    public void setInvestigatingStatement(String investigatingStatement) {
        this.investigatingStatement = investigatingStatement;
    }

    public LocalDate getFileClosedDate() {
        return fileClosedDate;
    }

    public Procurator getProcurator() {
        return procurator;
    }

    public LocalDate getFileOpenedDate() {
        return fileOpenedDate;
    }

    public void setSuspectLawyer(Lawyer suspectLawyer) {
        this.suspectLawyer = suspectLawyer;
    }

    public void setVictimLawyer(Lawyer victimLawyer) {
        this.victimLawyer = victimLawyer;
    }

    public void setProcurator(Procurator procurator) {
        this.procurator = procurator;
    }

    public void setStatus(StatusFile status) {
        this.status = status;
    }

    public StatusFile getStatus() {
        return status;
    }

    public String getHearingDecision() {
        return hearingDecision;
    }

    public void displayDetails() {
        System.out.println("File Number: " + fileNumber);
        System.out.println("File Opened Date: " + fileOpenedDate);
        System.out.println("File Closed Date: " + fileClosedDate);
        System.out.println("File Status: " + status);
        System.out.println("Investigating Statement: " + investigatingStatement);
        System.out.println("Procurator Assigned: " + procurator.getName());
        System.out.println("Suspect Lawyer: " + suspectLawyer.getName());
        System.out.println("Victim Lawyer: " + victimLawyer.getName());
        System.out.println("hearing Decision: " + hearingDecision);
        System.out.println("\n");
    }

    @Override
    public String toString() {
        return report.toString() +
                "\n" + procurator.toString() +
                "\n" + suspectLawyer.toString() +
                "\n" + victimLawyer.toString() +
                "\n" + fileOpenedDate +
                "\n" + fileClosedDate +
                "\n" + investigatingStatement +
                "\n" + hearingDecision +
                "\n" + status + "\n";

    }
}
