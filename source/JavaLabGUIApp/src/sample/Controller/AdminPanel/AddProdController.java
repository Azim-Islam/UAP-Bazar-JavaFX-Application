package sample.Controller.AdminPanel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Controller.ErrorController;
import sample.Controller.SceneSwitcher;
import sample.Main;
import sample.uapbazar.enums.Category;
import sample.uapbazar.enums.ElectCategory;
import sample.uapbazar.enums.Size;
import sample.uapbazar.enums.SubCategory;

import java.io.IOException;
import java.time.LocalDate;

public class AddProdController extends SceneSwitcher {

    @FXML
    TextField name, id, brand, quantity, price, manufacturer;
    @FXML
    MenuButton categoryMenu;
    @FXML
    Button MEN, WOMEN, KIDS, ELECTRONIC, FOOD, CLOTH;
    @FXML
    DatePicker mfg, exp;
    @FXML
    Label message;

    static SubCategory subCat;
    static ElectCategory eSub;
    static Category cat;
    static Size size;
    static String manufacturerL;
    static LocalDate mfgL, expL;

    public void addProduct(ActionEvent event) throws IOException {
        try {
            String n = name.getText();
            String i = id.getText();
            String b = brand.getText();
            int q = Integer.parseInt(quantity.getText());
            double p = Double.parseDouble(price.getText());

            if(cat == null){
                invalidOperationError("Category was not selected, please select the category.");
            }

            if(n.isEmpty() || i.isBlank() || b.isBlank() || b.isEmpty()){
                invalidOperationError();
            }

            else if (cat == Category.CLOTH) {
                if(subCat == null || size == null){
                    invalidOperationError("Size or sub category was not selected properly.");
                }
                else {
                    Main.getStore().addProduct(n, i, q, b, subCat, size, p);
                    successBox();
                }
            }

            else if(cat == Category.FOOD) {
                if(mfgL.isAfter(expL) || mfgL == null || expL == null) {
                    invalidOperationError("error in manufacturing date or expiry date input");
                }
                else {
                    Main.getStore().addProduct(n, i, q, mfgL, expL, p);
                    successBox();
                }
            }

            else if(cat == Category.ELECTRONICS){
                if(manufacturerL.isBlank() ||manufacturerL.isEmpty() || eSub == null){
                    invalidOperationError("electronic subcategory or manufacturer information was not entered properly");
                }
                else {
                    Main.getStore().addProduct(n, i, q, manufacturerL, eSub, p);
                    successBox();
                }
            }
            saveStore();
        }
        catch (Exception e){
            invalidOperationError("One or more input is invalid, please recheck all the inputs!");
        }
        resetValues();
        //Main.getStore().viewProducts(true);
    }

    //Add product
    public void selectCat(ActionEvent event){
        cat = Category.valueOf(((Node)event.getSource()).getId()); // <- Category.FOOD/Category.ELECTRONIC..etc
    }

    public void selectSub(ActionEvent event){
        subCat = SubCategory.valueOf(((Node)event.getSource()).getId());
    }
    public void selectElectSub(ActionEvent event){
        eSub = ElectCategory.valueOf(((Node)event.getSource()).getId());
    }
    public void selectSize(ActionEvent event){
        size = Size.valueOf(((Node)event.getSource()).getId());
    }

    public void closeWindow(ActionEvent event){
        try {
            if (((Node) event.getSource()).getId().equals("ELECTRONIC")) {
                manufacturerL = manufacturer.getText();
            }
            else if (((Node) event.getSource()).getId().equals("FOOD")) {
                mfgL = mfg.getValue();
                expL = exp.getValue();
            }
            else if( ((Node) event.getSource()).getId().equals("CLOTH")) {
            }
            else{
                System.out.println("ERROR");
            }
            //((Stage)((Node) event.getSource()).getScene().getWindow()).close();
            getStage(event).close();
        }
        catch (Exception e){
            invalidOperationError();
        }
    }


    public void resetValues(){
        mfgL = null;
        expL = null;
        size = null;
        manufacturerL = null;
        eSub = null;
        subCat = null;
        cat = null;
    }



    //Navigation
    public void createSecondStage(ActionEvent event, Stage secondaryStage){
        Stage actionStage = (Stage) ((MenuButton )categoryMenu).getScene().getWindow();
        secondaryStage.initOwner(actionStage); // <-
        secondaryStage.setX(actionStage.getX() + actionStage.getWidth());
        secondaryStage.setY(actionStage.getY());
        secondaryStage.initModality(Modality.WINDOW_MODAL);
    }

    public void openFoodMenu(ActionEvent event) throws IOException {

        Stage secondaryStage = new Stage();
        secondaryStage.setScene(openScene("UI/Admin/FoodMfgExp.fxml"));
        createSecondStage(event, secondaryStage);
        secondaryStage.show();
        selectCat(event);

    }

    public void openElectronicMenu(ActionEvent event) throws IOException {

        Stage secondaryStage = new Stage();
        secondaryStage.setScene(openScene("UI/Admin/ElectronicCategory.fxml"));
        createSecondStage(event, secondaryStage);
        secondaryStage.show();
        selectCat(event);


    }

    public void openClothingSubCategory(ActionEvent event) throws IOException {

        Stage secondaryStage = new Stage();
        secondaryStage.setScene(openScene("UI/Admin/ClothingSubCategory.fxml"));
        createSecondStage(event, secondaryStage);
        secondaryStage.show();
        selectCat(event);

    }

}
