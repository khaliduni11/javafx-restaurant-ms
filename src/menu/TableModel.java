package menu;

import javafx.scene.image.ImageView;

import java.sql.Blob;

public class TableModel {

    private int id;
    private String name;
    private double price;
    private String type;
    private ImageView image;

    public TableModel(int id, String name, double price, String type, ImageView image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.type = type;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public ImageView getImage() {
        return image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }
}
