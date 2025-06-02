package Persons;

public class Victim extends Person {
    private String statement;

    public Victim(String name, String SSN, String address, int age, String gender, String phone, String statement) {
        super(name, SSN, address, age, gender, phone);
        this.statement = statement;
    }

    @Override
    public String toString() {
        return super.toString() + "\n" + statement;
    }
}