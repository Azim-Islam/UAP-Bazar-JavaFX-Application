package sample.Controller.AdminPanel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Controller.SceneSwitcher;
import sample.Main;


import java.io.IOException;

public class PutOnSaleController extends SceneSwitcher {
    @FXML
    TextField id;
    @FXML
    TextField prct;
    @FXML
    TextField expdate;
    String idL;
    double prctL;
    int expL;

    public void confirm(ActionEvent event) throws IOException {
        try {
            idL = id.getText();
            prctL = Double.parseDouble(prct.getText());
            try{
                expL = Integer.parseInt(expdate.getText());
            }
            catch (Exception e){
                expL = -1;
            }
        }
        catch (Exception e){
            Stage tmp = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("UI/Error.fxml"));
            Pane tmpLayout = loader.load();
            Scene tmpPanel = new Scene(tmpLayout);
            tmp.setScene(tmpPanel);
            tmp.initOwner(((Node)event.getSource()).getScene().getWindow());
            tmp.initModality(Modality.WINDOW_MODAL);
            tmp.show();
        }

        if(expL == -1){
            if(prctL > -1 || prctL <= 100) {
                if(idL.isEmpty()|| idL.isBlank()){
                    invalidOperationError("Id can not be blank");
                }
                else {
                    Main.getStore().giveSale((int) prctL, idL);
                }
            }
            else{
                invalidOperationError("Invalid sale percentage!");
            }
        }
        else{
            if(expL>0) {
                Main.getStore().giveSale(expL, (int) prctL);
                successBox();
            }
            else{
                invalidOperationError("Invalid expiry date.");
            }
        }
        saveStore();
    }

}
