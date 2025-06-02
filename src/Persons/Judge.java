package Persons;

import Court.Hearing;

public class Judge extends Person {
    private static int J_idCounter = 1;
    private int J_ID;

    public Judge(String name, String SSN, String address, int age, String gender, String phone) {
        super(name, SSN, address, age, gender, phone);
        this.J_ID = J_idCounter++;
    }

    public static void resetId() {
        J_idCounter = 1;
    }

    public int getJ_ID() {
        return J_ID;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public void displayDetails() {
        System.out.println("judge ID:" + J_ID + "\njudge name: " + getName() + "\njudge SSN: " + getSSN() + "\njudge address: " + getAddress() + "\njudge age: "
                + getAge() + "\njudge gender: " + getGender() + "\njudge phone: " + getPhone() + "\n");
    }

}
