package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import sample.Main;


import java.io.IOException;
import java.util.List;

public class UserController extends SceneSwitcher {

    public void goToBrowseProd(ActionEvent event) throws IOException {
        Stage actionStage = getStage(event);
        actionStage.setScene(openScene("UI/User/UserBrowseProduct.fxml"));
    }
    public void goToAddToCart(ActionEvent event) throws IOException {
        Stage actionStage = getStage(event);
        Stage secondaryStage = new Stage();
        secondaryStage.initOwner(actionStage); // <-
        secondaryStage.setX(actionStage.getX() + actionStage.getWidth());
        secondaryStage.setY(actionStage.getY());


        secondaryStage.setScene(openScene("UI/User/UserBrowseProduct.fxml"));
        secondaryStage.show();
        actionStage.setScene(openScene("UI/User/AddToCart.fxml"));

        secondaryStage.setAlwaysOnTop(false); //setAlwaysOnTop attribute must be set after stage shown.
        actionStage.setAlwaysOnTop(true);
    }

    public void goToViewCart(ActionEvent event) throws IOException {
        Stage actionStage = getStage(event);
        actionStage.setScene(openScene("UI/User/ViewCart.fxml"));
    }
    public void goToPayBill(ActionEvent event) throws IOException {
        Stage actionStage = getStage(event);
        actionStage.setScene(openScene("UI/User/PayBill.fxml"));
    }

    public void clearCart(ActionEvent event){
        Main.getStore().clearCart();
    }



}
