package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.sql.*;

public class Controller {

    @FXML
    private Pane loginMenu;
    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;

    @FXML
    public void login(){
        if(userName.getText().length() > 0 && password.getText().length() > 0){
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant?useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                PreparedStatement ps = con.prepareStatement("select *from users where userName=? and password=?");
                ps.setString(1, userName.getText());
                ps.setString(2, password.getText());
                ps.executeQuery();

                if(ps.getResultSet().next()){
                    loginMenu.getScene().getWindow().hide();
                    try{
                        Pane login = FXMLLoader.load(getClass().getResource("/home/homePage.fxml"));
                        Stage stage = new Stage();
                        stage.setScene(new Scene(login));
                        stage.setMaximized(true);
                        stage.show();
                        con.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR, "invalid email/password.", ButtonType.OK);
                    alert.showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(userName.getText().length() <= 0 || password.getText().length() <= 0 ){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Enter your email and password.", ButtonType.OK);
            alert.showAndWait();

        }
    }

    @FXML
    public void forgetPassword(){
        try{
            Pane forgetPassword = FXMLLoader.load(getClass().getResource("/forgetPassword/forgetPassword.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Forget Password");
            stage.setScene(new Scene(forgetPassword));
            stage.setMaxHeight(500);
            stage.setMaxWidth(600);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void closeLogin(){
        Platform.exit();
    }
}
