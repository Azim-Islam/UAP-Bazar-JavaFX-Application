package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.Main;

import java.io.IOException;

public class ErrorController extends SceneSwitcher{
    @FXML
    Label message;

    public void Error() throws IOException {
        message.setText("PLEASE ENTER CORRECT INPUTS");

    }
    @FXML
    public void initialize() throws IOException {
        Error();
    }
    public void closeWindow(ActionEvent event){
        getStage(event).close();
    }
}
