package user;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.sql.*;
import java.time.LocalDate;

public class ModifyUsers {

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
    private Pane modifyUsers;
    private String oldBirthDate;
    private String oldJoinDate;

    DisplayUsers displayUsers = new DisplayUsers();

    public void initialize(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/restaurant?useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "root", ""
            );
            PreparedStatement ps = con.prepareStatement("select * from users where id=?");
            ps.setInt(1, DisplayUsers.userModifyId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                name.setText(rs.getString("name"));
                username.setText(rs.getString("username"));
                password.setText(rs.getString("password"));
                placeOfBirth.setText(rs.getString("placeOfBirth"));
                oldBirthDate = String.valueOf(rs.getDate("dateOfBirth"));
                oldJoinDate = String.valueOf(rs.getDate("joinOfDate"));
                dateOfBirth.getEditor().setText(String.valueOf(rs.getDate("dateOfBirth")));
                dateOfJoin.getEditor().setText(String.valueOf(rs.getDate("joinOfDate")));
                secQ.setText(rs.getString("secQ"));
                secA.setText(rs.getString("secA"));
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(),
                    ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    public void update(){
        if(name.getText().length() > 0 && username.getText().length() > 0 && password.getText().length() > 0
                && dateOfBirth.getEditor().toString().length() > 0 && dateOfJoin.getEditor().getText().length() > 0 &&
                placeOfBirth.getText().length() > 0){

            LocalDate dateBirth = dateOfBirth.getValue();
            LocalDate joinDate = dateOfJoin.getValue();
            try{

                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/restaurant?useLegacyDatetimeCode=false&serverTimezone=UTC",
                        "root", ""
                );
                PreparedStatement ps;
                if(oldBirthDate.equals(dateOfBirth.getEditor().getText()) && oldJoinDate.equals(dateOfJoin.getEditor().getText())){
                    ps = con.prepareStatement(
                            "update users set name=?, username=?, password=?, placeOfBirth=?, secQ=?, secA=? where id=?");
                    ps.setString(1, name.getText());
                    ps.setString(2, username.getText());
                    ps.setString(3, password.getText());
                    ps.setString(4, placeOfBirth.getText());
                    ps.setString(5, secA.getText());
                    ps.setString(6, secQ.getText());
                    ps.setInt(7, DisplayUsers.userModifyId);
                }else if(oldBirthDate.equals(dateOfBirth.getEditor().getText())){
                    ps = con.prepareStatement(
                            "update users set name=?, username=?, password=?, placeOfBirth=?, joinOfDate=?, secQ=?, secA=? where id=?");
                    ps.setString(1, name.getText());
                    ps.setString(2, username.getText());
                    ps.setString(3, password.getText());
                    ps.setString(4, placeOfBirth.getText());
                    ps.setString(5, String.valueOf(Date.valueOf(joinDate)));
                    ps.setString(6, secA.getText());
                    ps.setString(7, secQ.getText());
                    ps.setInt(8, DisplayUsers.userModifyId);
                }else if(oldJoinDate.equals(dateOfJoin.getEditor().getText())){
                    ps = con.prepareStatement(
                            "update users set name=?, username=?, password=?, placeOfBirth=?, dateOfBirth=?, secQ=?, secA=? where id=?");
                    ps.setString(1, name.getText());
                    ps.setString(2, username.getText());
                    ps.setString(3, password.getText());
                    ps.setString(4, placeOfBirth.getText());
                    ps.setString(5, String.valueOf(Date.valueOf(dateBirth)));
                    ps.setString(6, secA.getText());
                    ps.setString(7, secQ.getText());
                    ps.setInt(8, DisplayUsers.userModifyId);
                }else{
                    ps = con.prepareStatement(
                            "update users set name=?, username=?, password=?, placeOfBirth=?, dateOfBirth=?, joinOfDate=?, secQ=?, secA=? where id=?");
                    ps.setString(1, name.getText());
                    ps.setString(2, username.getText());
                    ps.setString(3, password.getText());
                    ps.setString(4, placeOfBirth.getText());
                    ps.setString(5, String.valueOf(Date.valueOf(dateBirth)));
                    ps.setString(6, String.valueOf(Date.valueOf(joinDate)));
                    ps.setString(7, secA.getText());
                    ps.setString(8, secQ.getText());
                    ps.setInt(9, DisplayUsers.userModifyId);
                }

                int i = ps.executeUpdate();
                if(i > 0){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have successfully updated " + name.getText(),
                            ButtonType.OK);
                    alert.showAndWait();

                    if(alert.getResult() == ButtonType.OK){
                        displayUsers.resetStartUsers();
                        modifyUsers.getScene().getWindow().hide();
                    }
                }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(),
                        ButtonType.OK);
                alert.showAndWait();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "Fill all the fields", ButtonType.OK);
            alert.showAndWait();
        }

    }

    @FXML
    public void delete(){
        try{
            Alert alerts = new Alert(Alert.AlertType.WARNING, "Are you sure that you want to delete: "+ name.getText() + "?" ,
                     ButtonType.NO, ButtonType.YES);
            alerts.showAndWait();
            if(alerts.getResult() == ButtonType.YES){
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/restaurant?useLegacyDatetimeCode=false&serverTimezone=UTC",
                        "root", ""
                );
                PreparedStatement ps = con.prepareStatement("delete from users where id=?");
                ps.setInt(1, DisplayUsers.userModifyId);
                int i = ps.executeUpdate();
                if(i > 0){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have successfully deleted: " + name.getText(),
                            ButtonType.OK);
                    alert.showAndWait();

                    if(alert.getResult() == ButtonType.OK){
                        displayUsers.resetStartUsers();
                        modifyUsers.getScene().getWindow().hide();
                    }
                }else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have failed to delete: " + name.getText() +
                            " try again another time.",
                            ButtonType.OK);
                    alert.showAndWait();

                    if(alert.getResult() == ButtonType.OK){
                        displayUsers.resetStartUsers();
                        modifyUsers.getScene().getWindow().hide();
                    }
                }
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(),
                    ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    public void cancel(){
        modifyUsers.getScene().getWindow().hide();
        displayUsers.resetStartUsers();
    }


}
