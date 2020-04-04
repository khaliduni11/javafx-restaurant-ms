package reservations;

import java.sql.Date;

public class ReservationModel {

    private int id;
    private String name;
    private String startTime;
    private String endTime;
    private Date date;
    private double price;
    private String type;
    private String contact;

    public ReservationModel(int id, String name, String startTime, String endTime, Date date, double price, String type, String contact) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.price = price;
        this.type = type;
        this.contact = contact;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public Date getDate() {
        return date;
    }

    public double getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public String getContact() {
        return contact;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
