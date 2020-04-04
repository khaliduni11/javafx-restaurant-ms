package user;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDate;

public class CreateUser {

    @FXML
    private TextField name;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private TextField placeOfBirth;
    @FXML
    private DatePicker dateOfBirth;
    @FXML
    private DatePicker dateOfJoin;
    @FXML
    private TextField secQ;
    @FXML
    private TextField secA;



    @FXML
    public void createUser(){
        if(name.getText().length() > 0 && username.getText().length() > 0 && password.getText().length() > 0
        && dateOfBirth.getEditor().toString().length() > 0 && dateOfJoin.getEditor().getText().length() > 0 &&
        placeOfBirth.getText().length() > 0){

            LocalDate dateBirth = dateOfBirth.getValue();
            LocalDate joinDate = dateOfJoin.getValue();
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/restaurant?useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "root", "");
                PreparedStatement ps = con.prepareStatement(
                    "insert into users (name, username, password, placeOfBirth, dateOfBirth, joinOfDate, secQ, secA) values(?,?,?,?,?,?,?,?)");
                ps.setString(1, name.getText());
                ps.setString(2, username.getText());
                ps.setString(3, password.getText());
                ps.setString(4, placeOfBirth.getText());
                ps.setString(5, String.valueOf(Date.valueOf(dateBirth)));
                ps.setString(6, String.valueOf(Date.valueOf(joinDate)));
                ps.setString(7, secQ.getText());
                ps.setString(8, secA.getText());
                int i = ps.executeUpdate();
                if(i > 0){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "you have successfully added" + name.getText() +
                            " in your users", ButtonType.OK);
                    alert.showAndWait();

                    if(alert.getResult() == ButtonType.OK){
                        name.setText("");
                        username.setText("");
                        password.setText("");
                        placeOfBirth.setText("");
                        dateOfJoin.getEditor().setText("");
                        dateOfBirth.getEditor().setText("");
                        secA.setText("");
                        secQ.setText("");
                    }
                }else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Invalid information try again", ButtonType.OK);
                    alert.showAndWait();

                    if(alert.getResult() == ButtonType.OK){
                        name.setText("");
                        username.setText("");
                        password.setText("");
                        placeOfBirth.setText("");
                        dateOfJoin.getEditor().setText("");
                        dateOfBirth.getEditor().setText("");
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

    }
}
