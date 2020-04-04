package reservations;

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

public class CreateReservation {

    @FXML
    private TextField name;
    @FXML
    private TextField startHour;
    @FXML
    private TextField startMinute;
    @FXML
    private TextField endHour;
    @FXML
    private TextField endMinute;
    @FXML
    private DatePicker date;
    @FXML
    private TextField price;
    @FXML
    private ComboBox type;
    @FXML
    private TextField contact;
    @FXML
    private Pane createReservation;

    ObservableList typeCB = FXCollections.observableArrayList("happy birthday", "fare wall", "couple dinner", "friendship",
            "family dinner", "others");

    public void initialize(){
        type.setItems(typeCB);
    }


    public void createReservation(){
        if( name.getText().length() > 0 &&  startHour.getText().length() > 0 && startMinute.getText().length() > 0 &&
                endHour.getText().length() > 0 && endMinute.getText().length() > 0 &&
                date.getEditor().getText().length() > 0 && price.getText().length() > 0
        && !type.getSelectionModel().getSelectedItem().equals(null) && contact.getText().length() > 0){

            LocalDate dateOfReservation = date.getValue();
            String startTime = startHour.getText() + ":" + startMinute.getText();
            String endTime = endHour.getText() + ":" + endMinute.getText();

            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/restaurant?useLegacyDatetimeCode=false&serverTimezone=UTC",
                        "root", "");
                PreparedStatement ps = con.prepareStatement(
                        "insert into reservations(name, startTime, endTime, date, price, type, contact) values(?,?,?,?,?,?,?)");
                ps.setString(1, name.getText());
                ps.setString(2, startTime);
                ps.setString(3, endTime);
                ps.setString(4, String.valueOf(Date.valueOf(dateOfReservation)));
                ps.setDouble(5, Double.parseDouble(price.getText()));
                ps.setString(6, type.getSelectionModel().getSelectedItem().toString());
                ps.setString(7, contact.getText());
                int i = ps.executeUpdate();
                if(i > 0){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "you have successfully created new reservation",
                            ButtonType.OK);
                    alert.showAndWait();
                    if(alert.getResult() == ButtonType.OK){
                        name.setText("");
                        startMinute.setText("");
                        startHour.setText("");
                        endMinute.setText("");
                        endHour.setText("");
                        date.getEditor().setText("");
                        price.setText("");
                        type.getSelectionModel().select(-1);
                        contact.setText("");
                    }
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill all inputs with appropriate information",
                            ButtonType.OK);
                    alert.showAndWait();
                }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(),
                        ButtonType.OK);
                alert.showAndWait();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill all inputs with appropriate information",
                    ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void cancel(){
        createReservation.getScene().getWindow().hide();
    }
}
