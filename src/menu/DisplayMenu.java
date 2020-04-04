package menu;

import home.HomeController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class DisplayMenu {

    public static int idMenuModify;
    @FXML
    private VBox displayMenu;
    @FXML
    private TableView<TableModel> tableView;
    @FXML
    private TableColumn<TableModel, Integer> id_col;
    @FXML
    private TableColumn<TableModel, String> name_col;
    @FXML
    private TableColumn<TableModel, Double> price_col;
    @FXML
    private TableColumn<TableModel, String> type_col;
    @FXML
    private TableColumn<TableModel, ImageView> image_col;
    @FXML
    private TextField searchItem;

    ObservableList<TableModel> displayTable = FXCollections.observableArrayList();
    ObservableList<TableModel> searchTable = FXCollections.observableArrayList();

    HomeController homeController = new HomeController();

    public void initialize() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/restaurant?useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "root", "");
            PreparedStatement ps = con.prepareStatement("select *from menu where type=?");
            ps.setString(1, HomeController.checkType);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                displayTable.add(new TableModel(rs.getInt("id"), rs.getString("name"),
                        rs.getDouble("price"), rs.getString("type"),
                        new ImageView(new Image(rs.getBlob("image").getBinaryStream(),
                                160, 120, false, false))));
            }

            id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
            name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
            price_col.setCellValueFactory(new PropertyValueFactory<>("price"));
            type_col.setCellValueFactory(new PropertyValueFactory<>("type"));
            image_col.setCellValueFactory(new PropertyValueFactory<>("image"));
            tableView.setPrefSize(500, 500);
            tableView.setItems(displayTable);

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(),
                    ButtonType.OK);
            alert.showAndWait();
        }
    }
    @FXML
    public void modifyMenu(){
        idMenuModify = tableView.getSelectionModel().getSelectedItem().getId();
        if(idMenuModify > 0){
            try{
                Pane modifyMenu = FXMLLoader.load(getClass().getResource("updateMenu.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(modifyMenu));
                stage.setMaxHeight(400);
                stage.setMaxWidth(700);
                stage.initOwner(displayMenu.getScene().getWindow());
                displayMenu.getScene().getWindow().hide();
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
                PreparedStatement ps = con.prepareStatement("select *from menu where type=? and name like ? or price like ?");
                ps.setString(1, HomeController.checkType);
                ps.setString(2, "%"+searchItem.getText()+"%");
                ps.setString(3, "%"+searchItem.getText()+"%");
                ResultSet rs = ps.executeQuery();
                searchTable.clear();
                while(rs.next()){
                    searchTable.add(new TableModel(rs.getInt("id"), rs.getString("name"),
                            rs.getDouble("price"), rs.getString("type"),
                            new ImageView(new Image(rs.getBlob("image").getBinaryStream(),
                                    160, 120, false, false))));
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

    public void restartMenuApps(){

        switch (HomeController.checkType){
            case "Breakfast":
                homeController.breakfast();
                break;
            case "Lunch":
                homeController.lunch();
                break;
            case "Dinner":
                homeController.dinner();
                break;
            case "Seafood":
                homeController.seafood();
                break;
            case "Drinks":
                homeController.drink();
                break;
            case "Desserts":
                homeController.desserts();
                break;
            case "All menus":
                homeController.allMenus();
                break;
        }
    }
}
