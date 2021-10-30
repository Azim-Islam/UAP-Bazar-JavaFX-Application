package sample.Controller.UserPanel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.Controller.SceneSwitcher;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.security.spec.ECField;
import java.util.Locale;
import java.util.ResourceBundle;


public class AddToCartController extends SceneSwitcher implements Initializable {
    @FXML
    TextField id;
    @FXML
    Spinner<Integer> amount;

    String idL;
    int amtL = 1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id.textProperty().addListener((obs, oldValue, newValue) -> {
            initSpinner();
        });
    }



    private void initSpinner() {
        try {
            amount.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Main.getStore().getQuantity(id.getText())));
        }
        catch (Exception ignored){
        }
    }

    public void confirm(ActionEvent event) throws IOException, CloneNotSupportedException {
        try {
            idL = id.getText();
            amtL = amount.getValue();
            if(idL.isEmpty() || idL.isBlank()){
                invalidOperationError("ID can not be empty");
            }
            else {
                Main.getStore().addProductToCart(idL, amtL);
                successBox();
            }
        }
        catch (Exception e){
           invalidOperationError("Inputs were invalid, please try again");
        }
    }
}
