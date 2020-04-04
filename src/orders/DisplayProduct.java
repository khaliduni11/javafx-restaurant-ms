package orders;

import home.HomeController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import menu.TableModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DisplayProduct {

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

    public static double price;
    public static String product;

    ObservableList<TableModel> displayTable = FXCollections.observableArrayList();
    ObservableList<TableModel> searchTable = FXCollections.observableArrayList();

    public void initialize() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/restaurant?useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "root", "");
            PreparedStatement ps = con.prepareStatement("select *from menu");
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

    public void search(){
        if(searchItem.getText().length() > 0){
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/restaurant?useLegacyDatetimeCode=false&serverTimezone=UTC",
                        "root", "");
                PreparedStatement ps = con.prepareStatement("select *from menu where type like ? or name like ?"+
                        " or price like ?");
                ps.setString(1, "%"+searchItem.getText()+"%");
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


    public void addOrder(){
        product = tableView.getSelectionModel().getSelectedItem().getName();
        price = tableView.getSelectionModel().getSelectedItem().getPrice();
        if(product.length() > 0 && price > 0){
            try{
                Pane displayReservations = FXMLLoader.load(getClass().getResource("/orders/addQty.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(displayReservations, 550, 200));
                stage.setMaxWidth(550);
                stage.setMaxHeight(200);
                stage.setTitle("Add Table No");
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("something went wrong");
        }
    }
}
