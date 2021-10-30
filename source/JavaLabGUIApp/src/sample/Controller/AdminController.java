package sample.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.DataHandler;
import sample.Main;
import sample.uapbazar.Cart;
import sample.uapbazar.Store;


import javax.swing.*;
import java.io.IOException;

public class AdminController extends SceneSwitcher{

        public void goToAddProduct(ActionEvent event) throws IOException {
            Stage actionStage = getStage(event);
            actionStage.setScene(openScene("UI/Admin/AddProduct.fxml"));
        }

        public void goToViewProd(ActionEvent event) throws IOException {
            Stage actionStage = getStage(event);
            actionStage.setScene(openScene("UI/Admin/AdminViewProduct.fxml"));
        }



        public void goToPutOnSale(ActionEvent event) throws IOException {
            Stage actionStage = getStage(event);
            Stage secondaryStage = new Stage();
            secondaryStage.initOwner(actionStage); // <-
            secondaryStage.setX(actionStage.getX() + actionStage.getWidth());
            secondaryStage.setY(actionStage.getY());

            secondaryStage.setScene(openScene("UI/Admin/AdminViewProduct.fxml"));
            secondaryStage.show();

            secondaryStage.setAlwaysOnTop(false); //setAlwaysOnTop attribute must be set after stage shown.
            actionStage.setAlwaysOnTop(true);

            actionStage.setScene(openScene("UI/Admin/PutOnSale.fxml"));

        }



}
