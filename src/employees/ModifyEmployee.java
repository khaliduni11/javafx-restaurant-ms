package employees;

import home.HomeController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.sql.*;
import java.time.LocalDate;

public class ModifyEmployee {

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
    private Pane modifyEmployee;

    private String oldDate;

    HomeController homeController = new HomeController();


    private ObservableList roleCB = FXCollections.observableArrayList("Cooker", "Cashier", "Cleaner", "Waiter", "Deliver Boy");
    private ObservableList genderCB = FXCollections.observableArrayList("Male", "Female");

    public void initialize(){
        try{

            gender.setItems(genderCB);
            role.setItems(roleCB);
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/restaurant?useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "root", "");
            PreparedStatement ps = con.prepareStatement("select * from employees where id=?");
            ps.setInt(1, DisplayEmployees.idModifyEmployee);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                name.setText(rs.getString("name"));
                contact.setText(rs.getString("contact"));
                address.setText(rs.getString("address"));
                wage.setText(String.valueOf(rs.getDouble("wage")));
                gender.getSelectionModel().select(rs.getString("gender"));
                dateOfBirth.getEditor().setText(String.valueOf(rs.getDate("dateOfBirth")));
                oldDate = String.valueOf(rs.getDate("dateOfBirth"));
                placeOfBirth.setText(rs.getString("placeOfBirth"));
                role.getSelectionModel().select(rs.getString("role"));
                specialization.setText(rs.getString("specialization"));
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(),
                    ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void updateEmployee(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/restaurant?useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "root", "");
            PreparedStatement ps;
            if(!oldDate.equals(dateOfBirth.getEditor().getText())){
                LocalDate dateBirth = dateOfBirth.getValue();
                ps = con.prepareStatement(
                        "update employees set name=?, dateOfBirth=?, placeOfBirth=?, gender=?, address=?, contact=?, wage=?, role=?, specialization=? where id=?");
                ps.setString(1, name.getText());
                ps.setString(2, String.valueOf(Date.valueOf(dateBirth)));
                ps.setString(3, placeOfBirth.getText());
                ps.setString(4, gender.getSelectionModel().getSelectedItem().toString());
                ps.setString(5, address.getText());
                ps.setString(6, contact.getText());
                ps.setString(7, wage.getText());
                ps.setString(8, role.getSelectionModel().getSelectedItem().toString());
                ps.setString(9, specialization.getText());
                ps.setInt(10, DisplayEmployees.idModifyEmployee);
            }else{
                ps = con.prepareStatement(
                        "update employees set name=?, placeOfBirth=?, gender=?, address=?, contact=?, wage=?, role=?, specialization=? where id=?");
                ps.setString(1, name.getText());
                ps.setString(2, placeOfBirth.getText());
                ps.setString(3, gender.getSelectionModel().getSelectedItem().toString());
                ps.setString(4, address.getText());
                ps.setString(5, contact.getText());
                ps.setString(6, wage.getText());
                ps.setString(7, role.getSelectionModel().getSelectedItem().toString());
                ps.setString(8, specialization.getText());
                ps.setInt(9, DisplayEmployees.idModifyEmployee);
            }
            int i = ps.executeUpdate();
            if(i > 0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "you have successfully updated " + name.getText() +
                        ".", ButtonType.OK);
                alert.showAndWait();
                if(alert.getResult() == ButtonType.OK){
                    modifyEmployee.getScene().getWindow().hide();
                    homeController.displayEmployees();
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
    }

    public void deleteEmployee(){
        try{
            Alert alerts = new Alert(Alert.AlertType.WARNING, "Are you sure that you want to delete "+ name.getText() +
                    " from the list of the employees", ButtonType.YES, ButtonType.NO);
            alerts.showAndWait();
            if(alerts.getResult() == ButtonType.YES){
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/restaurant?useLegacyDatetimeCode=false&serverTimezone=UTC",
                        "root", "");
                PreparedStatement ps = con.prepareStatement("delete from employees where id=?");
                ps.setInt(1, DisplayEmployees.idModifyEmployee);
                int i = ps.executeUpdate();
                if(i > 0){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have successfully deleted "+ name.getText() +
                            " from the employee list.", ButtonType.OK);
                    alert.showAndWait();

                    if(alert.getResult() == ButtonType.OK){
                        modifyEmployee.getScene().getWindow().hide();
                        homeController.displayEmployees();
                    }
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR, "please try again, this function did work well",
                            ButtonType.OK);
                    alert.showAndWait();
                }
            }else{
                modifyEmployee.getScene().getWindow().hide();
                homeController.displayEmployees();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(),
                    ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void cancel(){
        modifyEmployee.getScene().getWindow().hide();
        homeController.displayEmployees();
    }
}
