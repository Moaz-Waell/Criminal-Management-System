package Persons;

import Station.Report;

public class Officer extends Person {
    private String rank;
    private static int O_idCounter = 1;
    private int badgeNumber;

    public Officer(String name, String SSN, String address, int age, String gender, String phone, String rank) {
        super(name, SSN, address, age, gender, phone);
        this.rank = rank;
        this.badgeNumber = O_idCounter++;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getRank() {
        return rank;
    }

    public int getBadgeNumber() {
        return badgeNumber;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\n" +
                rank;
    }

    public static void resetId() {
        Officer.O_idCounter = 1;
    }

    public void displayDetails() {
        System.out.println("\nOfficer badgeNumber: " + badgeNumber + "\nOfficer name : " + getName() + "\nOfficer SSN: " + getSSN() + "\nOfficer address: " + getAddress() + "\nOfficer age: "
                + getAge() + "\nOfficer gender: " + getGender() + "\nOfficer phone: " + getPhone());
    }


}