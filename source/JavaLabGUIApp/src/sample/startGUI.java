package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;


public class startGUI extends Application {
    public static void main(String[] args) {

        /*

         */

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("UI/Home.fxml"));
        GridPane homeLayout = loader.load();

        Scene homeScene = new Scene(homeLayout);
        primaryStage.setTitle(Main.getStore().name);
        primaryStage.setScene(homeScene);
        primaryStage.setX(50);
        primaryStage.setY(0);
        primaryStage.show();



    }
}
