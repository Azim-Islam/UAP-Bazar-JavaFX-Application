package sample.Controller.AdminPanel;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import sample.Controller.SceneSwitcher;
import sample.Main;
import sample.uapbazar.Product;

;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class ViewProdController extends SceneSwitcher implements Initializable {
    @FXML
    TableView<Product> inv;
    @FXML
    TextField textfield;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //https://stackoverflow.com/questions/32398007/javafx-scene-control-tablecolumn-cannot-be-cast-to-javafx-scene-control-tablecol
        TableColumn id = new TableColumn("ID");
        TableColumn name = new TableColumn("NAME");
        TableColumn brand = new TableColumn("BRAND");
        TableColumn price = new TableColumn("PRICE");
        TableColumn quantity = new TableColumn("QUANTITY");
        TableColumn mfgdate = new TableColumn("MFG DATE");
        TableColumn expDate = new TableColumn("EXP DATE");
        TableColumn size = new TableColumn("SIZE");
        TableColumn scat = new TableColumn("SUB CATEGORY");
        TableColumn cat = new TableColumn("CATEGORY");
        TableColumn sale = new TableColumn("SALE PERCENTAGE");



        inv.getColumns().addAll(cat, id, name, price, brand, quantity, scat, size, mfgdate, expDate, sale);
        final ObservableList<Product> data = Main.getStore().getInventory();



        cat.setCellValueFactory(new PropertyValueFactory<Product, String>("category"));
        id.setCellValueFactory(new PropertyValueFactory<Product, String>("id"));
        name.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        price.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));
        quantity.setCellValueFactory(new PropertyValueFactory<Product, String>("quantity"));
        sale.setCellValueFactory(new PropertyValueFactory<Product, String>("salePercent"));


        scat.setCellValueFactory(new PropertyValueFactory<Product, String>("subCategory"));
        size.setCellValueFactory(new PropertyValueFactory<Product, String>("size"));
        brand.setCellValueFactory(new PropertyValueFactory<Product, String>("brand"));
        mfgdate.setCellValueFactory(new PropertyValueFactory<Product, LocalDate>("mfgDate"));
        expDate.setCellValueFactory(new PropertyValueFactory<Product, LocalDate>("expDate"));

        inv.setItems(data);

        FilteredList<Product> filteredData = new FilteredList<>(data, p -> true);

        //https://stackoverflow.com/questions/17017364/fast-filtering-in-javafx-tableview
        textfield.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (oldValue != null && (newValue.length() < oldValue.length())) {
                inv.setItems(data);
            }
            String value = newValue.toLowerCase();
            ObservableList<Product> subentries = FXCollections.observableArrayList();

            long count = inv.getColumns().stream().count();
            for (int i = 0; i < inv.getItems().size(); i++) {
                for (int j = 0; j < count; j++) {
                    String entry = "" + inv.getColumns().get(j).getCellData(i);
                    if (entry.toLowerCase().contains(value)) {
                        subentries.add(inv.getItems().get(i));
                        break;
                    }
                }
            }
            inv.setItems(subentries);
        });


    }

    public void removeProduct(){
        try {
            Product selectedItem = inv.getSelectionModel().getSelectedItem();
            inv.getItems().remove(selectedItem);
            Main.getStore().removeProductFromStore(selectedItem.getId());
            saveStore();
        }
        catch (Exception e){
            invalidOperationError();
        }
    }

    public void confirmRemove(ActionEvent event){
        Alert dialogBox = new Alert(Alert.AlertType.CONFIRMATION);
        getStage(event).setAlwaysOnTop(true);
        dialogBox.initOwner(getStage(event));
        dialogBox.setTitle("Confirm Deletion");
        dialogBox.setHeaderText("The Item Will Be Removed From Inventory");
        dialogBox.setContentText("Confirm?");
        dialogBox.initModality(Modality.WINDOW_MODAL);

        Optional<ButtonType> result = dialogBox.showAndWait();
        if(result.get() == ButtonType.OK){
            removeProduct();
        }
    }

}
