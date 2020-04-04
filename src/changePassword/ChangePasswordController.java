package changePassword;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ChangePasswordController {

    @FXML
    private Pane changePasswordMenu;
    @FXML
    private TextField userName;
    @FXML
    private PasswordField oldPassword;
    @FXML
    private PasswordField newPassword;
    @FXML
    private PasswordField confirmPassword;

    public void changePassword(){
        if(userName.getText().length() > 0 && oldPassword.getText().length() > 0 && newPassword.getText().length() > 0 &&
                newPassword.getText().equals(confirmPassword.getText())){
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/restaurant?useLegacyDatetimeCode=false&serverTimezone=UTC",
                        "root", "");
                PreparedStatement ps = con.prepareStatement("update users set password=? where userName=? and password=?");
                ps.setString(1, newPassword.getText());
                ps.setString(2, userName.getText());
                ps.setString(3, oldPassword.getText());
                int i = ps.executeUpdate();
                if(i > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Successfully changed your password.", ButtonType.OK);
                    alert.showAndWait();
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid email/password.", ButtonType.OK);
                    alert.showAndWait();
                }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(),
                        ButtonType.OK);
                alert.showAndWait();
            }

        }else if(!newPassword.getText().equals(confirmPassword.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR, "New password and Confirm password must be same.",
                    ButtonType.OK);
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "Fill all inputs with appropriate values.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void closeChangePassword(){
        changePasswordMenu.getScene().getWindow().hide();
    }
}
