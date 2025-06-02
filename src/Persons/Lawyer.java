package Persons;

public class Lawyer extends Person {
    private static int idCounter = 1;
    private int lawyerId;

    public Lawyer(String name, String SSN, String address, int age, String gender, String phone) {
        super(name, SSN, address, age, gender, phone);
        this.lawyerId = idCounter++;
    }

    public int getLawyerId() {
        return lawyerId;
    }


    public static void resetId() {
        Lawyer.idCounter = 1;
    }


    @Override
    public String toString() {
        return super.toString();
    }

    public void displayDetails() {
        System.out.println("lawyer ID: " + lawyerId + "\nLawyer name : " + getName() + "\nLawyer SSN: " + getSSN() + "\nLawyer address: " + getAddress() + "\nLawyer age: "
                + getAge() + "\nLawyer gender: " + getGender() + "\nLawyer phone: " + getPhone() + "\n");
    }


}
