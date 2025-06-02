package Persons;

public class Witness extends Person {
    private String statement;

    public Witness(String name, String SSN, String address, int age, String gender, String phone, String statement) {
        super(name, SSN, address, age, gender, phone);
        this.statement = statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    @Override
    public String toString() {
        return super.toString() + "\n" + statement;
    }
}