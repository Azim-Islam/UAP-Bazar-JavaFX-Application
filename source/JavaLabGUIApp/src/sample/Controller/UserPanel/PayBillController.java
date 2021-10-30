package sample.Controller.UserPanel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.Controller.SceneSwitcher;
import sample.Main;

import java.io.IOException;


public class PayBillController extends SceneSwitcher{
    @FXML
    Text pay;

    @FXML
    public void initialize(){
        pay.setText(Main.getStore().getBill()+" BDT");
        //pay.setText("HIE");
    }
    public void confirm(ActionEvent event) throws IOException {
        Main.getStore().payBill();
        Main.getStore().clearCart();
        saveStore();
        goToUserButton(event);
    }


}
