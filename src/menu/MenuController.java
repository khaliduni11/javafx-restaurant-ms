package menu;

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
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class MenuController {

    @FXML
    private Pane createMenu;
    @FXML
    private TextField name;
    @FXML
    private  TextField price;
    private String image;
    @FXML
    private ComboBox type;
    @FXML
    private ImageView showImage;

    private ObservableList typeCB = FXCollections.observableArrayList("Breakfast", "Lunch", "Dinner", "Seafood",
            "Drinks", "Take Away", "Desserts", "All menus");

    public void initialize(){
        type.setItems(typeCB);
    }

    @FXML
    public void addMenu(){
        if(name.getText().length() > 0 && price.getText().length() > 0 &&
                type.getSelectionModel().getSelectedItem().toString().length() > 0 && image.length() > 0){
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/restaurant?useLegacyDatetimeCode=false&serverTimezone=UTC",
                        "root", "");
                PreparedStatement ps = con.prepareStatement("insert into menu(name, price, type, image) values(?, ?, ?, ?)");
                ps.setString(1, name.getText());
                ps.setDouble(2, Double.parseDouble(price.getText()));
                ps.setString(3, type.getSelectionModel().getSelectedItem().toString());
                FileInputStream img = new FileInputStream(image);
                ps.setBinaryStream(4, img);
                int i = ps.executeUpdate();
                if(i > 0 ){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "created new file " + name.getText() + ".", ButtonType.OK);
                    alert.showAndWait();
                    if (alert.getResult() == ButtonType.OK) {
                        name.setText("");
                        price.setText("");
                        type.setValue("");
                        showImage.setImage(null);
                    }

                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR,
                            "You have entered inappropriate information please try again.", ButtonType.OK);
                    alert.showAndWait();
                    if (alert.getResult() == ButtonType.OK) {
                        name.setText("");
                        price.setText("");
                        type.setValue("");
                        System.out.println(image);
                        showImage.setImage(null);
                    }
                }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(),
                        ButtonType.OK);
                alert.showAndWait();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "Fill all inputs with valid information.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    public void closeCreateMenu(){
        createMenu.getScene().getWindow().hide();
    }
    @FXML
    public void browseImage(){
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All images", "*.jpg", "*.png", "*.jpeg")
        );
        File selectedFile = fc.showOpenDialog(null);
        if(selectedFile != null){
            try{
                FileInputStream imageInput = new FileInputStream(selectedFile);
                Image img = new Image(imageInput);
                showImage.setImage(img);
                image = selectedFile.toString();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(),
                        ButtonType.OK);
                alert.showAndWait();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please choose a valid image.", ButtonType.OK);
            alert.showAndWait();

            if(alert.getResult() == ButtonType.OK){
                browseImage();
            }
        }
    }
}
