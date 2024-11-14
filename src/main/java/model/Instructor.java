package model;

public class Instructor {
    private String name;
    private String phoneNumber;
    private String specialization;
    private String availability;

    public Instructor(String name, String phoneNumber, String specialization, String availability) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.specialization = specialization;
        this.availability = availability;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
}
