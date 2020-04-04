package orders;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GetTable {

    public static int table;
    public static int receipt;

    @FXML
    private Pane tableSearch;
    @FXML
    private TextField tableNo;

    public void search(){
        if(tableNo.getText().length() > 0 && tableNo.getText().matches("[\\d\\.]+")){
            table = Integer.parseInt(tableNo.getText());
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/restaurant?useLegacyDatetimeCode=false&serverTimezone=UTC",
                        "root", "");
                PreparedStatement ps = con.prepareStatement("select max(receipt) from orders where tableNo=?");
                ps.setInt(1, Integer.parseInt(tableNo.getText()));
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    System.out.println(rs.getInt(1));
                    receipt = rs.getInt(1);
                    displayReceipt();
                    tableSearch.getScene().getWindow().hide();
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid tableNo", ButtonType.OK);
                    alert.showAndWait();
                }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                alert.showAndWait();
            }
        }
    }

    public void displayReceipt(){
        try{
            Pane displayOrder = FXMLLoader.load(getClass().getResource("/orders/displayOrders.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(displayOrder, 493, 587));
            stage.setMaxWidth(493);
            stage.setMaxHeight(587);
            stage.setTitle("Receipt");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancel(){
        tableSearch.getScene().getWindow().hide();
    }
}
