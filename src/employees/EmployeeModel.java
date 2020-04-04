package employees;

import javafx.scene.control.DatePicker;

import java.sql.Date;

public class EmployeeModel {
    private int id;
    private String name;
    private Date dateOfBirth;
    private String placeOfBirth;
    private String gender;
    private String address;
    private String contact;
    private double wage;
    private String role;
    private String specialization;

    public EmployeeModel(int id, String name, Date dateOfBirth, String placeOfBirth, String gender, String address,
                         String contact, double wage, String role, String specialization) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.placeOfBirth = placeOfBirth;
        this.gender = gender;
        this.address = address;
        this.contact = contact;
        this.wage = wage;
        this.role = role;
        this.specialization = specialization;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public String getContact() {
        return contact;
    }

    public double getWage() {
        return wage;
    }

    public String getRole() {
        return role;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setWage(double wage) {
        this.wage = wage;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
