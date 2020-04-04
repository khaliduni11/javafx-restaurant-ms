package employees;

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
import java.time.LocalDate;

public class DisplayEmployees {

    @FXML
    private TableView<EmployeeModel> tableView;
    @FXML
    private TableColumn<EmployeeModel, Integer> id_col;
    @FXML
    private TableColumn<EmployeeModel, String> name_col;
    @FXML
    private TableColumn<EmployeeModel, String> contact_col;
    @FXML
    private TableColumn<EmployeeModel, String> address_col;
    @FXML
    private TableColumn<EmployeeModel, Double> wage_col;
    @FXML
    private TableColumn<EmployeeModel, String> gender_col;
    @FXML
    private TableColumn<EmployeeModel, Date> dateOfBirth_col;
    @FXML
    private TableColumn<EmployeeModel, String> placeOfBirth_col;
    @FXML
    private TableColumn<EmployeeModel, String> role_col;
    @FXML
    private TableColumn<EmployeeModel, String> specialization_col;
    @FXML
    private VBox displayEmployee;
    @FXML
    private TextField searchItem;

    public static int idModifyEmployee;

    ObservableList displayTable = FXCollections.observableArrayList();
    ObservableList searchTable = FXCollections.observableArrayList();

    public void initialize(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/restaurant?useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "root", "");
            PreparedStatement ps = con.prepareStatement("select * from employees");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                displayTable.add(new EmployeeModel(rs.getInt("id"), rs.getString("name"),
                        rs.getDate("dateOfBirth"), rs.getString("placeOfBirth"),
                        rs.getString("gender"), rs.getString("address"),
                        rs.getString("contact"), rs.getDouble("wage"),
                        rs.getString("role"), rs.getString("specialization")));
            }

            id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
            name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
            dateOfBirth_col.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
            placeOfBirth_col.setCellValueFactory(new PropertyValueFactory<>("placeOfBirth"));
            gender_col.setCellValueFactory(new PropertyValueFactory<>("gender"));
            address_col.setCellValueFactory(new PropertyValueFactory<>("address"));
            contact_col.setCellValueFactory(new PropertyValueFactory<>("contact"));
            wage_col.setCellValueFactory(new PropertyValueFactory<>("wage"));
            role_col.setCellValueFactory(new PropertyValueFactory<>("role"));
            specialization_col.setCellValueFactory(new PropertyValueFactory<>("specialization"));

            tableView.setItems(displayTable);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(),
                    ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void modifyEmployee(){
        if(tableView.getSelectionModel().getSelectedItem().getId() > 0){
            idModifyEmployee = tableView.getSelectionModel().getSelectedItem().getId();
            try{
                Pane displayUser = FXMLLoader.load(getClass().getResource("/employees/modifyEmployee.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(displayUser));
                stage.setMaxWidth(800);
                stage.setMaxHeight(600);
                stage.setTitle("displaying users");
                displayEmployee.getScene().getWindow().hide();
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
                        "select * from employees where name like ? or placeOfBirth like ? or gender like ? "+
                                " or address like ? or contact like ? or role like ? or specialization like ? "+
                                " or dateOfBirth like ? or wage like ?");
                ps.setString(1, "%"+searchItem.getText()+"%");
                ps.setString(2, "%"+searchItem.getText()+"%");
                ps.setString(3, "%"+searchItem.getText()+"%");
                ps.setString(4, "%"+searchItem.getText()+"%");
                ps.setString(5, "%"+searchItem.getText()+"%");
                ps.setString(6, "%"+searchItem.getText()+"%");
                ps.setString(7, "%"+searchItem.getText()+"%");
                ps.setString(8, "%"+searchItem.getText()+"%");
                ps.setString(9, "%"+searchItem.getText()+"%");
                ResultSet rs = ps.executeQuery();
                searchTable.clear();
                while(rs.next()){
                    searchTable.add(new EmployeeModel(rs.getInt("id"), rs.getString("name"),
                            rs.getDate("dateOfBirth"), rs.getString("placeOfBirth"),
                            rs.getString("gender"), rs.getString("address"),
                            rs.getString("contact"), rs.getDouble("wage"),
                            rs.getString("role"), rs.getString("specialization")));
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
