package Persons;

abstract public class Person {
    private String name;
    private String SSN;
    private String address;
    private int age;
    private String gender;
    private String phone;

    public Person(String name, String SSN, String address, int age, String gender, String phone) {
        this.name = name;
        this.SSN = SSN;
        this.address = address;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSSN() {
        return SSN;
    }

    public void setSSN(String SSN) {
        this.SSN = SSN;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return name
                + "\n"
                + SSN
                + "\n"
                + address
                + "\n"
                + age
                + "\n"
                + gender
                + "\n"
                + phone;
    }


}
