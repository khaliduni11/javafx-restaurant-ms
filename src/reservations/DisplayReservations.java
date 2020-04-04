package reservations;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;

public class DisplayReservations {

    @FXML
    private TableView<ReservationModel> tableView;
    @FXML
    private TableColumn<ReservationModel, Integer> id_col;
    @FXML
    private TableColumn<ReservationModel, String> name_col;
    @FXML
    private TableColumn<ReservationModel, String> start_col;
    @FXML
    private TableColumn<ReservationModel, String> end_col;
    @FXML
    private TableColumn<ReservationModel, Date> date_col;
    @FXML
    private TableColumn<ReservationModel, Double> price_col;
    @FXML
    private TableColumn<ReservationModel, String> type_col;
    @FXML
    private  TableColumn<ReservationModel, String> contact_col;
    @FXML
    private VBox displayReservations;
    @FXML
    private TextField searchItem;

    public static int modifyReservationId;

    ObservableList<ReservationModel> displayTable = FXCollections.observableArrayList();
    ObservableList<ReservationModel> searchTable = FXCollections.observableArrayList();

    public void initialize(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/restaurant?useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "root", "");
            PreparedStatement ps = con.prepareStatement("select *from reservations");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                displayTable.add(new ReservationModel(rs.getInt("id"), rs.getString("name"),
                        rs.getString("startTime"), rs.getString("endTime"), rs.getDate("date"),
                        rs.getDouble("price"), rs.getString("type"),
                        rs.getString("contact")));
            }

            id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
            name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
            start_col.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            end_col.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            date_col.setCellValueFactory(new PropertyValueFactory<>("date"));
            price_col.setCellValueFactory(new PropertyValueFactory<>("price"));
            type_col.setCellValueFactory(new PropertyValueFactory<>("type"));
            contact_col.setCellValueFactory(new PropertyValueFactory<>("contact"));

            tableView.setItems(displayTable);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(),
                    ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void modifyReservation(){
        modifyReservationId = tableView.getSelectionModel().getSelectedItem().getId();
        if(modifyReservationId > 0){
            displayReservations.getScene().getWindow().hide();
            try{
                Pane modifyReservation = FXMLLoader.load(getClass().getResource("/reservations/modifyReservation.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(modifyReservation, 560, 560));
                stage.setMaxWidth(560);
                stage.setMaxHeight(560);
                stage.setTitle("Modify Reservation");
                stage.show();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(),
                        ButtonType.OK);
                alert.showAndWait();
            }
        }
    }

    public void search(){
        if(searchItem.getText().length() > 0){
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/restaurant?useLegacyDatetimeCode=false&serverTimezone=UTC",
                        "root", "");
                PreparedStatement ps = con.prepareStatement(
                        "select * from reservations where name like ? or startTime like ? or endTime like ? "+
                                " or date like ? or price like ? or type like ? or contact like ?");
                ps.setString(1, "%"+searchItem.getText()+"%");
                ps.setString(2, "%"+searchItem.getText()+"%");
                ps.setString(3, "%"+searchItem.getText()+"%");
                ps.setString(4, "%"+searchItem.getText()+"%");
                ps.setString(5, "%"+searchItem.getText()+"%");
                ps.setString(6, "%"+searchItem.getText()+"%");
                ps.setString(7, "%"+searchItem.getText()+"%");
                ResultSet rs = ps.executeQuery();
                searchTable.clear();
                while(rs.next()){
                    searchTable.add(new ReservationModel(rs.getInt("id"), rs.getString("name"),
                            rs.getString("startTime"), rs.getString("endTime"), rs.getDate("date"),
                            rs.getDouble("price"), rs.getString("type"),
                            rs.getString("contact")));
                }
                tableView.setItems(searchTable);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(),
                        ButtonType.OK);
                alert.showAndWait();
                e.printStackTrace();
            }
        }else{
            tableView.setItems(displayTable);
        }
    }
}
