package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import sample.DataHandler;
import sample.Main;
import sample.uapbazar.Cart;
import sample.uapbazar.Store;

import  java.util.HashMap;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

/*
    1. Cache all the Scenes in a HashMap (Dictionary).
    2. When a button is pressed return the Scene from the HashMap.

 */

public class SceneSwitcher{


        public HashMap<String, Scene> allScenes = new HashMap<>();

        public Scene openScene(String sceneDir) throws IOException {
            Scene tmp = allScenes.get(sceneDir);
            FXMLLoader loader = new FXMLLoader();

            if(tmp != null){
                return tmp;
            }
            else{
                loader.setLocation(Main.class.getResource(sceneDir));
                Pane tmpLayout = loader.load();
                Scene tmpScene = new Scene(tmpLayout);
                allScenes.put(sceneDir, tmpScene);
                return tmpScene;
            }
        }

        public void closeExtraStage(Stage actionStage){
            List<Window> windows = Window.getWindows();
            for (Window window : windows) {
                if (window.getScene() != actionStage.getScene()) {
                    ((Stage) window).close();
                }
            }
            actionStage.setX(50);
        }
        public void closeWindow(ActionEvent event) throws IOException {
            ((Stage)((Node) event.getSource()).getScene().getWindow()).close();
        }



        public Stage getStage(ActionEvent event){
            return (Stage) ((Node)event.getSource()).getScene().getWindow();
        }

        public void goToHomeButton(ActionEvent event) throws IOException{
            Stage actionStage = getStage(event);
            actionStage.setScene(openScene("UI/Home.fxml"));
            closeExtraStage(actionStage);
        }

        public void goToAdminButton(ActionEvent event) throws IOException {
            Stage actionStage = getStage(event);
            actionStage.setScene(openScene("UI/AdminPanel.fxml"));
            closeExtraStage(actionStage);
        }

        public void goToUserButton(ActionEvent event) throws IOException {
            Stage actionStage = getStage(event);
            actionStage.setScene(openScene("UI/UserPanel.fxml"));
            closeExtraStage(actionStage);
        }

        public void invalidOperationError(){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Operation Could Not Be Executed Successfully".toUpperCase(Locale.ROOT));
            alert.setContentText("Re Check Inputs And Try Again".toUpperCase(Locale.ROOT));
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.setAlwaysOnTop(true);
            alert.showAndWait();
        }
        public void invalidOperationError(String msg){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Operation Could Not Be Executed Successfully".toUpperCase(Locale.ROOT));
            alert.setContentText(msg.toUpperCase(Locale.ROOT));
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.setAlwaysOnTop(true);
            alert.showAndWait();
        }

        public void successBox(){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("SUCCESS!");
            alert.setHeaderText("Operation Executed Successfully".toUpperCase(Locale.ROOT));
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.setAlwaysOnTop(true);
            alert.showAndWait();
    }

    public void saveStore(){
        DataHandler.writeObject(Main.getStore());
    }

    public static void loadStore(){
        Store tmp = (Store) DataHandler.readObject();
        if(tmp == null){
            tmp = new Store("UAP BAZAR Inc.");
        }
        Main.setStore(tmp);
        if(Main.getStore().cart == null){
            Main.getStore().cart = new Cart();
        }
    }


}
