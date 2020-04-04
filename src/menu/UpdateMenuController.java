package menu;

import home.HomeController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UpdateMenuController {

    @FXML
    private TextField name;
    @FXML
    private TextField price;
    @FXML
    private ComboBox type;
    @FXML
    private ImageView imageView;
    @FXML
    private Pane updateMenu;
    private String image = "";


    DisplayMenu displayMenu = new DisplayMenu();

    private ObservableList typeCB = FXCollections.observableArrayList("Breakfast", "Lunch", "Dinner", "Seafood",
            "Drinks", "Take Away", "Desserts", "All menus");

    public void initialize(){
        type.setItems(typeCB);
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/restaurant?useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "root", "");
            PreparedStatement ps = con.prepareStatement("select *from menu where id=?");
            ps.setInt(1, DisplayMenu.idMenuModify);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                name.setText(rs.getString("name"));
                price.setText(rs.getString("price"));
                type.getSelectionModel().select(rs.getString("type"));
                imageView.setImage(new Image(rs.getBlob("image").getBinaryStream()));
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(),
                    ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void uploadImage(){
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);
        if(selectedFile != null){
            try{
                FileInputStream imageInput = new FileInputStream(selectedFile);
                Image img = new Image(imageInput);
                imageView.setImage(img);
                image = selectedFile.toString();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(),
                        ButtonType.OK);
                alert.showAndWait();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "This file isn't valid.", ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                uploadImage();
            }
        }
    }

    public void update(){
        if(name.getText().length() > 0 && price.getText().length() > 0 &&
                type.getSelectionModel().getSelectedItem().toString().length() > 0){
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/restaurant?useLegacyDatetimeCode=false&serverTimezone=UTC",
                        "root", "");

                if(image.length() > 0){
                    System.out.println("with image inside");
                    PreparedStatement ps = con.prepareStatement("update menu set name=?, price=?, type=?, image=? where id=?");

                    ps.setString(1, name.getText());
                    ps.setDouble(2, Double.parseDouble(price.getText()));
                    ps.setString(3, type.getSelectionModel().getSelectedItem().toString());
                    FileInputStream img = new FileInputStream(image);
                    ps.setBinaryStream(4, img);
                    ps.setInt(5, DisplayMenu.idMenuModify);
                    int i = ps.executeUpdate();
                    if(i > 0 ){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "updated file " + name.getText() + ".", ButtonType.OK);
                        alert.showAndWait();

                        if (alert.getResult() == ButtonType.OK) {
                            updateMenu.getScene().getWindow().hide();
                        }
                    }else{
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter valid information.", ButtonType.OK);
                        alert.showAndWait();

                    }
                }else{
                    PreparedStatement ps  = con.prepareStatement("update menu set name=?, price=?, type=? where id=?");
                    ps.setString(1, name.getText());
                    ps.setDouble(2, Double.parseDouble(price.getText()));
                    ps.setString(3, type.getSelectionModel().getSelectedItem().toString());
                    ps.setInt(4, DisplayMenu.idMenuModify);
                    int i = ps.executeUpdate();
                    if(i > 0 ){
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "updated file " + name.getText() + ".", ButtonType.OK);
                        alert.showAndWait();

                        if (alert.getResult() == ButtonType.OK) {
                            updateMenu.getScene().getWindow().hide();
                            displayMenu.restartMenuApps();
                        }
                    }else{
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "please enter valid information.", ButtonType.OK);
                        alert.showAndWait();
                    }
                }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(),
                        ButtonType.OK);
                alert.showAndWait();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Fill all the blank with appropriate values.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void delete(){
        Alert alerts = new Alert(Alert.AlertType.WARNING, "Are you sure that want to delete "+ name.getText() + "?",
                ButtonType.YES, ButtonType.CANCEL);
        alerts.showAndWait();
        if(alerts.getResult() == ButtonType.YES){
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/restaurant?useLegacyDatetimeCode=false&serverTimezone=UTC",
                        "root", "");
                PreparedStatement ps = con.prepareStatement("delete from menu where id=?");
                ps.setInt(1, DisplayMenu.idMenuModify);
                int i = ps.executeUpdate();
                if(i > 0){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "deleted " + name.getText() + ".", ButtonType.OK);
                    alert.showAndWait();

                    if (alert.getResult() == ButtonType.OK) {
                        updateMenu.getScene().getWindow().hide();
                        displayMenu.restartMenuApps();
                    }
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Cann't delete this item please try again.", ButtonType.OK);
                    alert.showAndWait();

                    if (alert.getResult() == ButtonType.OK) {
                        updateMenu.getScene().getWindow().hide();
                        displayMenu.restartMenuApps();
                    }
                }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(),
                        ButtonType.OK);
                alert.showAndWait();
            }
        }
    }

    public void cancel(){
        updateMenu.getScene().getWindow().hide();
        displayMenu.restartMenuApps();
    }

}
