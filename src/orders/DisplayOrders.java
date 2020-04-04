package orders;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DisplayOrders {

    @FXML
    private Pane receiptPane;
    @FXML
    private Label receiptNo;
    @FXML
    private Label tableNo;
    @FXML
    private Label date;
    @FXML
    private Label total;
    @FXML
    private TableColumn<OrdersModel, Integer> id_col;
    @FXML
    private TableColumn<OrdersModel, String> description_col;
    @FXML
    private TableColumn<OrdersModel, Double> price_col;
    @FXML
    private TableColumn<OrdersModel, Integer> qty_col;
    @FXML
    private TableColumn<OrdersModel, Double> amount_col;
    @FXML
    private TableView<OrdersModel> tableView;

    ObservableList<OrdersModel> addTable = FXCollections.observableArrayList();

    GetTable getTable = new GetTable();

    public void initialize(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/restaurant?useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "root", "");
            PreparedStatement ps = con.prepareStatement("select *from orders where receipt=? and tableNo=?");
            ps.setInt(1, GetTable.receipt);
            ps.setInt(2, GetTable.table);
            ResultSet rs = ps.executeQuery();
            int sn = 1;
            double getTotal = 0;
            while(rs.next()){
                addTable.add(new OrdersModel(sn, rs.getString("description"), rs.getDouble("price"),
                        rs.getInt("quantity"), rs.getDouble("amount")));
                sn++;
                date.setText(String.valueOf(rs.getDate("date")));
                getTotal += rs.getDouble("amount");
                tableNo.setText(String.valueOf(rs.getInt("tableNo")));
                receiptNo.setText(String.valueOf(rs.getInt("receipt")));
            }
            total.setText(String.valueOf(getTotal) + "$");

            id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
            description_col.setCellValueFactory(new PropertyValueFactory<>("description"));
            price_col.setCellValueFactory(new PropertyValueFactory<>("price"));
            qty_col.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            amount_col.setCellValueFactory(new PropertyValueFactory<>("amount"));

            tableView.setItems(addTable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(){
        String description = tableView.getSelectionModel().getSelectedItem().getDescription();
        double price = tableView.getSelectionModel().getSelectedItem().getPrice();
        double amount = tableView.getSelectionModel().getSelectedItem().getAmount();
        int quantity = tableView.getSelectionModel().getSelectedItem().getQuantity();

        if(description.length() > 0 && price > 0 && amount > 0 && quantity > 0){
            Alert alerts = new Alert(Alert.AlertType.WARNING, "Are you sure that you want to delete" + description + "?",
                    ButtonType.YES, ButtonType.NO);
            alerts.showAndWait();
            if(alerts.getResult() == ButtonType.YES){
                try{
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/restaurant?useLegacyDatetimeCode=false&serverTimezone=UTC",
                            "root", "");
                    PreparedStatement ps = con.prepareStatement(
                            "delete from orders where description=? and price=? and amount=? and quantity=?");
                    ps.setString(1, description);
                    ps.setDouble(2, price);
                    ps.setDouble(3, amount);
                    ps.setInt(4, quantity);
                    int i = ps.executeUpdate();
                    if(i > 0){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have successfully deleted "+ description,
                                ButtonType.OK);
                        alert.showAndWait();
                        if(alert.getResult() == ButtonType.OK){
                            receiptPane.getScene().getWindow().hide();
                            getTable.displayReceipt();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void print(){
        PrinterJob job = PrinterJob.createPrinterJob();
        if(job != null && job.showPrintDialog(receiptPane.getScene().getWindow())){
            boolean success = job.printPage(receiptPane);
            if(success){
                job.endJob();
            }
        }
    }

}
