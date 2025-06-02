package Court;

import Deputy.FilesDeputy;
import Persons.Judge;

import java.time.LocalDate;

public class Hearing implements Comparable<Hearing> {
    private int hearingId;
    private static int nextId = 1;
    private LocalDate hearingDate;
    private Judge judge;
    private FilesDeputy files;
    private SuspectStatus suspectStatus;
    private String penaltyDuration;

    public Hearing(LocalDate hearingDate, Judge judge, FilesDeputy files) {
        this.hearingId = nextId++;
        this.hearingDate = hearingDate;
        this.judge = judge;
        this.suspectStatus = SuspectStatus.UNDER_INVESTIGATION;
        this.penaltyDuration = "0";
        this.files = files;
    }

    public static void resetId() {
        Hearing.nextId = 1;
    }

    public void displayDetails() {
        System.out.println("Hearing ID: " + hearingId);
        System.out.println("File Number: " + files.getFileNumber());
        System.out.println("Hearing Date: " + hearingDate);
        System.out.println("Judge: " + judge.getName());
        System.out.println("file status: " + files.getStatus());
        System.out.println("Hearing decision: " + files.getHearingDecision());
        System.out.println("Suspect Status: " + suspectStatus);
        System.out.println("Penalty Duration: " + penaltyDuration);
        System.out.println("\n");
    }

    public LocalDate getHearingDate() {
        return hearingDate;
    }

    public Judge getJudge() {
        return judge;
    }

    public int getHearingId() {
        return hearingId;
    }

    public void addFile(FilesDeputy file) {
        this.files = file;

    }

    public FilesDeputy getFiles() {
        return files;
    }

    @Override
    public int compareTo(Hearing other) {
        return this.hearingDate.compareTo(other.hearingDate);
    }

    public void addJudge(Judge judge) {
        this.judge = judge;

    }

    public void setJudge(Judge judge) {
        this.judge = judge;
    }

    public void setHearingDate(LocalDate hearingDate) {
        this.hearingDate = hearingDate;
    }

    public void setSuspectStatus(SuspectStatus suspectStatus) {
        this.suspectStatus = suspectStatus;
    }

    public SuspectStatus getSuspectStatus() {
        return suspectStatus;
    }

    public void setPenaltyDuration(String penaltyDuration) {
        this.penaltyDuration = penaltyDuration;
    }

    public String getPenaltyDuration() {
        return penaltyDuration;
    }

    @Override
    public String toString() {
        return hearingDate +
                "\n" +
                files.toString() +
                judge.toString() +
                "\n" +
                suspectStatus +
                "\n" +
                penaltyDuration;
    }
}
