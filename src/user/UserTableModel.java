package user;

import java.util.Date;

public class UserTableModel {

    private int id;
    private String name;
    private String username;
    private String password;
    private String placeOfBirth;
    private Date dateOfBirth;
    private Date dateOfjoin;
    private String SecQ;
    private String SecA;

    public UserTableModel(int id, String name, String username, String password, String placeOfBirth, Date dateOfBirth, Date dateOfjoin, String secQ, String secA) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.placeOfBirth = placeOfBirth;
        this.dateOfBirth = dateOfBirth;
        this.dateOfjoin = dateOfjoin;
        SecQ = secQ;
        SecA = secA;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public Date getDateOfjoin() {
        return dateOfjoin;
    }

    public String getSecQ() {
        return SecQ;
    }

    public String getSecA() {
        return SecA;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setDateOfjoin(Date dateOfjoin) {
        this.dateOfjoin = dateOfjoin;
    }

    public void setSecQ(String secQ) {
        SecQ = secQ;
    }

    public void setSecA(String secA) {
        SecA = secA;
    }
}
