package user;

import home.HomeController;
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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

public class DisplayUsers {

    @FXML
    private TableView<UserTableModel> tableView;
    @FXML
    private TableColumn<UserTableModel, Integer> id_col;
    @FXML
    private TableColumn<UserTableModel, String> name_col;
    @FXML
    private TableColumn<UserTableModel, String> username_col;
    @FXML
    private TableColumn<UserTableModel, String> password_col;
    @FXML
    private TableColumn<UserTableModel, String> placeOfBirth_col;
    @FXML
    private TableColumn<UserTableModel, Date> dateOfBirth_col;
    @FXML
    private TableColumn<UserTableModel, Date> dateOfJoin_col;
    @FXML
    private TableColumn<UserTableModel, String> secQ_col;
    @FXML
    private TableColumn<UserTableModel, String> secA_col;
    @FXML
    private VBox displayUsers;
    @FXML
    private TextField searchItem;

    public static int userModifyId;

    HomeController homeController = new HomeController();

    ObservableList<UserTableModel> displayTable = FXCollections.observableArrayList();
    ObservableList<UserTableModel> searchTable = FXCollections.observableArrayList();

    public void initialize(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/restaurant?useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "root", ""
            );
            PreparedStatement ps = con.prepareStatement("select * from users");
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    displayTable.add(new UserTableModel(rs.getInt("id"), rs.getString("name"),
                        rs.getString("username"), rs.getString("password"),
                        rs.getString("placeOfBirth"), rs.getDate("dateOfBirth"),
                        rs.getDate("joinOfDate"), rs.getString("secQ"), rs.getString("secA")));
            }

            id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
            name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
            username_col.setCellValueFactory(new PropertyValueFactory<>("username"));
            password_col.setCellValueFactory(new PropertyValueFactory<>("password"));
            placeOfBirth_col.setCellValueFactory(new PropertyValueFactory<>("placeOfBirth"));
            dateOfBirth_col.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
            dateOfJoin_col.setCellValueFactory(new PropertyValueFactory<>("dateOfjoin"));
            secQ_col.setCellValueFactory(new PropertyValueFactory<>("secQ"));
            secA_col.setCellValueFactory(new PropertyValueFactory<>("secA"));
            tableView.setItems(displayTable);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(),
                    ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void modifyTableItem(){
        if(tableView.getSelectionModel().getSelectedItem().getId() > 0){
            userModifyId = tableView.getSelectionModel().getSelectedItem().getId();
            try{
                Pane displayUser = FXMLLoader.load(getClass().getResource("/user/modifyUsers.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(displayUser));
                stage.setMaxWidth(800);
                stage.setMaxHeight(600);
                stage.setTitle("displaying users");
                displayUsers.getScene().getWindow().hide();
                stage.show();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(),
                        ButtonType.OK);
                alert.showAndWait();
            }
        }
    }

    public void resetStartUsers(){

        homeController.displayUsers();
    }

    public void search(){
        if(searchItem.getText().length() > 0){
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/restaurant?useLegacyDatetimeCode=false&serverTimezone=UTC",
                        "root", "");
                PreparedStatement ps = con.prepareStatement(
                        "select * from users where name like ? or placeOfBirth like ? or dateOfBirth like ? "+
                                " or joinOfDate like ? or username like ?");
                ps.setString(1, "%"+searchItem.getText()+"%");
                ps.setString(2, "%"+searchItem.getText()+"%");
                ps.setString(3, "%"+searchItem.getText()+"%");
                ps.setString(4, "%"+searchItem.getText()+"%");
                ps.setString(5, "%"+searchItem.getText()+"%");
                ResultSet rs = ps.executeQuery();
                searchTable.clear();
                while(rs.next()){
                    searchTable.add(new UserTableModel(rs.getInt("id"), rs.getString("name"),
                            rs.getString("username"), rs.getString("password"),
                            rs.getString("placeOfBirth"), rs.getDate("dateOfBirth"),
                            rs.getDate("joinOfDate"), rs.getString("secQ"), rs.getString("secA")));
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
