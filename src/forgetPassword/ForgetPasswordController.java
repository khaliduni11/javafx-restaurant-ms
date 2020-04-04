package forgetPassword;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import java.sql.*;

public class ForgetPasswordController {

    @FXML
    private Pane forgetPasswordMenu;
    @FXML
    private TextField userName;
    @FXML
    private TextField password;
    @FXML
    private TextField secQ;
    @FXML
    private TextField secA;

    @FXML
    public void check(){
        if(userName.getText().length() > 0){
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/restaurant?useLegacyDatetimeCode=false&serverTimezone=UTC",
                        "root", "");
                PreparedStatement ps = con.prepareStatement("select secQ from users where username=?");
                ps.setString(1, userName.getText());
                ResultSet rs = ps.executeQuery();

                if(rs.next()){
                    secQ.setText(rs.getString(1));
                    con.close();
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid username.", ButtonType.OK);
                    alert.showAndWait();
                    con.close();
                }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(),
                        ButtonType.OK);
                alert.showAndWait();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter your username and check it.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void forgetPassword(){
        if(userName.getText().length() > 0 && secQ.getText().length() > 0 && secA.getText().length() > 0){
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/restaurant?useLegacyDatetimeCode=false&serverTimezone=UTC",
                        "root", "");
                PreparedStatement ps = con.prepareStatement("select password from users where username=? and secA=?");
                ps.setString(1, userName.getText());
                ps.setString(2, secA.getText());
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    password.setText(rs.getString(1));
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid answer please try again.", ButtonType.OK);
                    alert.showAndWait();
                    alert.setWidth(500);
                }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(),
                        ButtonType.OK);
                alert.showAndWait();
            }
        }
    }

    public void closeForgetPassword(){
        forgetPasswordMenu.getScene().getWindow().hide();
    }
}
