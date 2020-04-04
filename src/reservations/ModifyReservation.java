package reservations;

import home.HomeController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.sql.*;
import java.time.LocalDate;

public class ModifyReservation {

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
    private Pane modifyReservation;

    private String oldDate;

    HomeController homeController = new HomeController();

    ObservableList typeCB = FXCollections.observableArrayList("happy birthday", "far wall", "couple dinner", "friendship",
            "family dinner", "others");

    public void initialize(){
        type.setItems(typeCB);

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/restaurant?useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "root", "");
            PreparedStatement ps = con.prepareStatement("select *from reservations where id=?");
            ps.setInt(1, DisplayReservations.modifyReservationId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String start[] = rs.getString("startTime").split(":");
                String end[] = rs.getString("endTime").split(":");
                name.setText(rs.getString("name"));
                startHour.setText(start[0]);
                startMinute.setText(start[1]);
                endHour.setText(end[0]);
                endMinute.setText(end[1]);
                date.getEditor().setText(String.valueOf(rs.getDate("date")));
                price.setText(String.valueOf(rs.getDouble("price")));
                type.getSelectionModel().select(rs.getString("type"));
                contact.setText(rs.getString("contact"));
                oldDate = String.valueOf(rs.getDate("date"));
                String changePrice = String.valueOf(rs.getDouble("price"));
                System.out.println(changePrice);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(),
                    ButtonType.OK);
            alert.showAndWait();
        }
    }


    public void updateReservation(){
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
                PreparedStatement ps;
                if(!oldDate.equals(date.getEditor().getText())){
                    ps = con.prepareStatement(
                            "update reservations set name=?, startTime=?, endTime=?, date=?, price=?, type=?, contact=?"+
                                    " where id=?");
                    ps.setString(1, name.getText());
                    ps.setString(2, startTime);
                    ps.setString(3, endTime);
                    ps.setString(4, String.valueOf(Date.valueOf(dateOfReservation)));
                    ps.setDouble(5, Double.parseDouble(price.getText()));
                    ps.setString(6, type.getSelectionModel().getSelectedItem().toString());
                    ps.setString(7, contact.getText());
                    ps.setInt(8, DisplayReservations.modifyReservationId);
                }else{
                    ps = con.prepareStatement(
                            "update reservations set name=?, startTime=?, endTime=?, price=?, type=?, contact=? where id=?");
                    ps.setString(1, name.getText());
                    ps.setString(2, startTime);
                    ps.setString(3, endTime);
                    ps.setDouble(4, Double.parseDouble(price.getText()));
                    ps.setString(5, type.getSelectionModel().getSelectedItem().toString());
                    ps.setString(6, contact.getText());
                    ps.setInt(7, DisplayReservations.modifyReservationId);
                }
                int i = ps.executeUpdate();
                if(i > 0){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "you have successfully updated " + name.getText(),
                            ButtonType.OK);
                    alert.showAndWait();
                    if(alert.getResult() == ButtonType.OK){
                        modifyReservation.getScene().getWindow().hide();
                        homeController.displayReservations();
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

    public void deleteReservation(){
        Alert alerts = new Alert(Alert.AlertType.WARNING, "Are you sure you want to delete "+ name.getText() + "?",
                ButtonType.YES, ButtonType.CANCEL);
        alerts.showAndWait();
        if(alerts.getResult() == ButtonType.YES){
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/restaurant?useLegacyDatetimeCode=false&serverTimezone=UTC",
                        "root", "");
                PreparedStatement ps = con.prepareStatement("delete from reservations where id=?");
                ps.setInt(1, DisplayReservations.modifyReservationId);
                int i = ps.executeUpdate();
                if(i > 0){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "you have successfully deleted " + name.getText() +
                            " from your list" + ButtonType.OK);
                    alert.showAndWait();
                    if(alert.getResult() == ButtonType.OK){
                        modifyReservation.getScene().getWindow().hide();
                        homeController.displayReservations();
                    }
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR, "you have failed to delete " + name.getText() +
                            " from your list, try again", ButtonType.OK);
                    alert.showAndWait();
                }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(),
                        ButtonType.OK);
                alert.showAndWait();
            }
        }
    }

    public void cancel(){
        modifyReservation.getScene().getWindow().hide();
        homeController.displayReservations();
    }
}
