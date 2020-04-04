package home;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomeController {
    public static String checkType;

    @FXML
    private Pane homePageMenu;
    @FXML
    public void changePassword(){
        try{
            Pane changePassword = FXMLLoader.load(getClass().getResource("/changePassword/changePassword.fxml"));

            Stage stage = new Stage();
            stage.setScene(new Scene(changePassword));
            stage.setMaxWidth(500);
            stage.setMaxHeight(500);
            stage.setTitle("Change Password");
            stage.initOwner(homePageMenu.getScene().getWindow());
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void createMenu(){
        try{
            Pane createMenu = FXMLLoader.load(getClass().getResource("/menu/createMenu.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(createMenu));
            stage.setMaxWidth(800);
            stage.setMaxHeight(600);
            stage.setTitle("Create Menu");
            stage.initOwner(homePageMenu.getScene().getWindow());
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void breakfast(){
        try{
            checkType = "Breakfast";
            VBox breakfast = FXMLLoader.load(getClass().getResource("/menu/displayMenu.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(breakfast));
            stage.setMaximized(true);
            stage.setTitle("Break fast");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void lunch(){
        try{
            checkType = "Lunch";
            VBox lunch = FXMLLoader.load(getClass().getResource("/menu/displayMenu.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(lunch));
            stage.setMaximized(true);
            stage.setTitle("Lunch");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void dinner(){
        try{
            checkType = "Dinner";
            VBox dinner = FXMLLoader.load(getClass().getResource("/menu/displayMenu.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(dinner));
            stage.setMaximized(true);
            stage.setTitle("Dinner");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void seafood(){
        try{
            checkType = "Seafood";
            VBox seafood = FXMLLoader.load(getClass().getResource("/menu/displayMenu.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(seafood));
            stage.setMaximized(true);
            stage.setTitle("Seafood");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void drink(){
        try{
            checkType = "Drinks";
            VBox drink = FXMLLoader.load(getClass().getResource("/menu/displayMenu.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(drink));
            stage.setMaximized(true);
            stage.setTitle("Drinks");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void takeAway(){
        try{
            checkType = "Take Away";
            VBox takeAway = FXMLLoader.load(getClass().getResource("/menu/displayMenu.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(takeAway));
            stage.setMaximized(true);
            stage.setTitle("Take Away");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void desserts(){
        try{
            checkType = "Desserts";
            VBox dessert = FXMLLoader.load(getClass().getResource("/menu/displayMenu.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(dessert));
            stage.setMaximized(true);
            stage.setTitle("Desserts");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void allMenus(){
        try{
            checkType = "All menus";
            VBox allMenus = FXMLLoader.load(getClass().getResource("/menu/displayMenu.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(allMenus));
            stage.setMaximized(true);
            stage.setTitle("All menus");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registerUser(){
        try{
            Pane userRegisteration = FXMLLoader.load(getClass().getResource("/user/createUser.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(userRegisteration));
            stage.setMaxWidth(800);
            stage.setMaxHeight(600);
            stage.setTitle("create User");
            stage.initOwner(homePageMenu.getScene().getWindow());
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayUsers(){
        try{
            VBox displayUser = FXMLLoader.load(getClass().getResource("/user/displayUsers.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(displayUser));
            stage.setMaximized(true);
            stage.setTitle("displaying users");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createEmployee(){
        try{
            Pane createEmployee = FXMLLoader.load(getClass().getResource("/employees/createEmployee.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(createEmployee, 650, 590));
            stage.setMaxWidth(650);
            stage.setMaxHeight(570);
            stage.setTitle("Add Employee");
            stage.initOwner(homePageMenu.getScene().getWindow());
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayEmployees(){
        try{
            Pane createEmployee = FXMLLoader.load(getClass().getResource("/employees/displayEmployees.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(createEmployee));
            stage.setMaximized(true);
            stage.setTitle("Add Employee");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void createCostumer(){
//        try{
//            Pane createCostumer = FXMLLoader.load(getClass().getResource("/costumers/addCustomer.fxml"));
//            Stage stage = new Stage();
//            stage.setScene(new Scene(createCostumer, 560, 340));
//            stage.setMaxWidth(560);
//            stage.setMaxHeight(340);
//            stage.setTitle("Add Costumer");
//            stage.initOwner(homePageMenu.getScene().getWindow());
//            stage.show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void displayCostumers(){
        try{
            Pane displayCostumers = FXMLLoader.load(getClass().getResource("/costumers/displayCostumers.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(displayCostumers, 650, 590));
            stage.setMaximized(true);
            stage.setTitle("Display Costumers");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createReservations(){
        try{
            Pane createReservation = FXMLLoader.load(getClass().getResource("/reservations/createReservation.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(createReservation, 560, 560));
            stage.setMaxWidth(560);
            stage.setMaxHeight(560);
            stage.setTitle("Add Reservation");
            stage.initOwner(homePageMenu.getScene().getWindow());
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayReservations(){
        try{
            Pane displayReservations = FXMLLoader.load(getClass().getResource("/reservations/displayReservations.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(displayReservations, 650, 590));
            stage.setMaximized(true);
            stage.setTitle("Display Reservations");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createOrder(){
        try{
            Pane displayReservations = FXMLLoader.load(getClass().getResource("/orders/addTable.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(displayReservations, 451, 160));
            stage.setMaxWidth(451);
            stage.setMaxHeight(160);
            stage.setTitle("Add Table No");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getTableOrder(){
        try{
            Pane getTable = FXMLLoader.load(getClass().getResource("/orders/getTable.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(getTable, 600, 160));
            stage.setMaxWidth(600);
            stage.setMaxHeight(160);
            stage.setTitle("Search specific table");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void exit(){
        Platform.exit();
    }

}
