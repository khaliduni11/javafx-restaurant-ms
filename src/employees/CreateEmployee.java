package employees;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDate;

public class CreateEmployee {

    @FXML
    private TextField name;
    @FXML
    private DatePicker dateOfBirth;
    @FXML
    private TextField placeOfBirth;
    @FXML
    private ComboBox gender;
    @FXML
    private TextField address;
    @FXML
    private TextField contact;
    @FXML
    private TextField wage;
    @FXML
    private ComboBox role;
    @FXML
    private TextField specialization;
    @FXML
    private Pane createEmployee;

    private ObservableList roleCB = FXCollections.observableArrayList("Cooker", "Cashier", "Cleaner", "Waiter", "Deliver Boy");
    private ObservableList genderCB = FXCollections.observableArrayList("Male", "Female");


    public void initialize(){
        gender.setItems(genderCB);
        role.setItems(roleCB);
    }

    public void createEmployee(){
        if(name.getText().length() > 0 && dateOfBirth.getEditor().getText().length() > 0 && placeOfBirth.getText().length() > 0
        && !gender.getSelectionModel().getSelectedItem().equals(null) && address.getText().length() > 0 &&
                contact.getText().length() > 0 && wage.getText().length() > 0 &&
                !role.getSelectionModel().getSelectedItem().equals(null)){
            LocalDate dateBirth = dateOfBirth.getValue();
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/restaurant?useLegacyDatetimeCode=false&serverTimezone=UTC",
                        "root", "");
                PreparedStatement ps = con.prepareStatement(
                        "insert into employees (name, dateOfBirth, placeOfBirth, gender, address, contact, wage, role, "+
                                "specialization) values(?,?,?,?,?,?,?,?,?)");
                ps.setString(1, name.getText());
                ps.setString(2, String.valueOf(Date.valueOf(dateBirth)));
                ps.setString(3, placeOfBirth.getText());
                ps.setString(4, gender.getSelectionModel().getSelectedItem().toString());
                ps.setString(5, address.getText());
                ps.setString(6, contact.getText());
                ps.setString(7, wage.getText());
                ps.setString(8, role.getSelectionModel().getSelectedItem().toString());
                ps.setString(9, specialization.getText());
                int i = ps.executeUpdate();
                System.out.println("i = " + i);
                if(i > 0){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "you have successfully registered " + name.getText() +
                            " as an employee.", ButtonType.OK);
                    alert.showAndWait();
                    if(alert.getResult() == ButtonType.OK){
                        name.setText("");
                        contact.setText("");
                        placeOfBirth.setText("");
                        dateOfBirth.getEditor().setText("");
                        role.getSelectionModel().clearSelection();
                        gender.getSelectionModel().clearSelection();
                        specialization.setText("");
                        wage.setText("");
                        address.setText("");
                    }
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR, "please try again, this function did work well",
                            ButtonType.OK);
                    alert.showAndWait();
                }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(),
                        ButtonType.OK);
                alert.showAndWait();
            }

        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill all essential fields", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void cancel(){
        createEmployee.getScene().getWindow().hide();
    }
}
