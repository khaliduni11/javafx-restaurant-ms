package orders;

import java.sql.Date;

public class OrdersModel {

    private int id;
    private String description;
    private double price;
    private int quantity;
    private double amount;

    public OrdersModel(int id, String description, double price, int quantity, double amount) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.amount = amount;
    }


    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getAmount() {
        return amount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
