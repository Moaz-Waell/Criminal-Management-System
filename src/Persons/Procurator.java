package Persons;

public class Procurator extends Person {
    private String licenseNumber;

    public Procurator(String name, String SSN, String address, int age, String gender, String phone, String licenseNumber) {
        super(name, SSN, address, age, gender, phone);
        this.licenseNumber = licenseNumber;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    @Override
    public String toString() {
        return super.toString() + "\n" + licenseNumber;
    }

    public void displayDetails() {
        System.out.println("Procurator name : " + getName() + "\nProcurator SSN: " + getSSN() + "\nProcurator address: " + getAddress() + "\nProcurator age: "
                + getAge() + "\nProcurator gender: " + getGender() + "\nProcurator phone: " + getPhone() + "\nlicenseNumber: " + licenseNumber + "\n");
    }


}
