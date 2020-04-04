package orders;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CreateOrder {

    @FXML
    private Pane addQty;
    @FXML
    private Pane table;
    @FXML
    private TextField tableNo;
    @FXML
    private TextField qty;
    private static int receipt;
    private static int tableNoStore;


    public void cancel(){
        table.getScene().getWindow().hide();
        addQty.getScene().getWindow().hide();
    }

    public void createOrder(){
        if(tableNo.getText().length() > 0 ){
            tableNoStore = Integer.parseInt(tableNo.getText());
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/restaurant?useLegacyDatetimeCode=false&serverTimezone=UTC",
                        "root", "");
                PreparedStatement ps = con.prepareStatement("select max(receipt) from orders");
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    receipt = rs.getInt(1);
                    receipt++;
                    table.getScene().getWindow().hide();
                    displayProducts();
                }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CANCEL);
                alert.showAndWait();
                e.printStackTrace();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "please first enter table number", ButtonType.CANCEL);
            alert.showAndWait();
        }
    }

    public void addProduct(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/restaurant?useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "root", "");
            PreparedStatement ps = con.prepareStatement(
                    "insert into orders(description, quantity, price, amount, receipt, tableNo) values(?,?,?,?,?,?)");
            ps.setString(1, DisplayProduct.product);
            ps.setInt(2, Integer.parseInt(qty.getText()));
            ps.setDouble(3, DisplayProduct.price);
            ps.setDouble(4, Integer.parseInt(qty.getText()) * DisplayProduct.price);
            ps.setInt(5, receipt);
            ps.setInt(6, tableNoStore);
            int i = ps.executeUpdate();
            if(i > 0 ){
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "you have successfully added new item", ButtonType.OK);
                alert.showAndWait();
                if(alert.getResult() == ButtonType.OK){
                    addQty.getScene().getWindow().hide();
                }
            }
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CANCEL);
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    public void displayProducts(){
        try{
            VBox allMenus = FXMLLoader.load(getClass().getResource("/orders/displayProduct.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(allMenus));
            stage.setMaximized(true);
            stage.setTitle("Products that are available for costumers");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
