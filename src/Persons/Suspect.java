package Persons;

public class Suspect extends Person {
    private String description;

    public Suspect(String name, String SSN, String address, int age, String gender, String phone, String description) {
        super(name, SSN, address, age, gender, phone);
        this.description = description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void displayDetails() {
        System.out.println("Suspect Name: " + getName());
        System.out.println("Suspect SSN: " + getSSN());
        System.out.println("Suspect Address: " + getAddress());
        System.out.println("Suspect Age: " + getAge());
        System.out.println("Suspect Gender: " + getGender());
        System.out.println("Suspect Phone: " + getPhone());
        System.out.println("Description: " + description);
        System.out.println("-------------------");
    }

    @Override
    public String toString() {
        return super.toString() + "\n" + description;
    }
}