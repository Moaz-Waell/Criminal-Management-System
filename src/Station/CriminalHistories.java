package Station;

public class CriminalHistories {
    private int reportNumber;
    private String SSN;
    private String suspectName;
    private CrimeType crimeType;
    private String crimeDetails;
    private String penaltyDuration;

    public CriminalHistories(int reportNumber, String SSN, String suspectName, CrimeType crimeType, String crimeDetails, String penaltyDuration) {
        this.reportNumber = reportNumber;
        this.SSN = SSN;
        this.suspectName = suspectName;
        this.crimeType = crimeType;
        this.crimeDetails = crimeDetails;
        this.penaltyDuration = penaltyDuration;
    }

    // Getters
    public int getReportNumber() { return reportNumber; }
    public String getSSN() { return SSN; }
    public String getSuspectName() { return suspectName; }
    public CrimeType getCrimeType() { return crimeType; }
    public String getCrimeDetails() { return crimeDetails; }
    public String getPenaltyDuration() { return penaltyDuration; }

    // Setters
    public void setReportNumber(int reportNumber) { this.reportNumber = reportNumber; }
    public void setSSN(String SSN) { this.SSN = SSN; }
    public void setSuspectName(String suspectName) { this.suspectName = suspectName; }
    public void setCrimeType(CrimeType crimeType) { this.crimeType = crimeType; }
    public void setCrimeDetails(String crimeDetails) { this.crimeDetails = crimeDetails; }
    public void setPenaltyDuration(String penaltyDuration) { this.penaltyDuration = penaltyDuration; }

    public void displayDetails() {
        System.out.println("Report Number: " + reportNumber);
        System.out.println("SSN: " + SSN);
        System.out.println("Suspect Name: " + suspectName);
        System.out.println("Crime Type: " + crimeType);
        System.out.println("Crime Details: " + crimeDetails);
        System.out.println("Penalty Duration: " + penaltyDuration);
        System.out.println("-------------------");
    }

    @Override
    public String toString() {
        return reportNumber + "\n" + SSN + "\n" + suspectName + "\n" + crimeType + "\n" + crimeDetails + "\n" + penaltyDuration + "\n";
    }
} 