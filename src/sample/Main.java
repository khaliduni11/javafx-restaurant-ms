package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import menu.DisplayMenu;

import java.util.Formatter;

public class Main extends Application {

    DisplayMenu menu = new DisplayMenu();
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Login Form");
        primaryStage.setScene(new Scene(root, 750, 340));
//        primaryStage.setMaxHeight(340);
//        primaryStage.setMaxWidth(750);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
