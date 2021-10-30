package sample.Controller.UserPanel;

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

import java.net.URL;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class ViewCartController extends SceneSwitcher implements Initializable {
    @FXML
    TableView<Product> inv;
    @FXML
    TextField textfield;
    @FXML
    Spinner<Integer> amount;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initSpinner();
        //https://stackoverflow.com/questions/32398007/javafx-scene-control-tablecolumn-cannot-be-cast-to-javafx-scene-control-tablecol
        TableColumn id = new TableColumn("ID");
        TableColumn name = new TableColumn("NAME");
        TableColumn brand = new TableColumn("BRAND");
        TableColumn price = new TableColumn("PRICE");
        TableColumn quantity = new TableColumn("QUANTITY");
        TableColumn cat = new TableColumn("CATEGORY");



        inv.getColumns().addAll(cat, id, name, price, brand, quantity);
        final ObservableList<Product> data = Main.getStore().getCart();



        cat.setCellValueFactory(new PropertyValueFactory<Product, String>("category"));
        id.setCellValueFactory(new PropertyValueFactory<Product, String>("id"));
        name.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        price.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));
        quantity.setCellValueFactory(new PropertyValueFactory<Product, String>("quantity"));


        inv.setItems(data);

        //********************* Updating Product Count With Spinner *********************
        inv.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                int maxVal = Main.getStore().getQuantity(inv.getSelectionModel().getSelectedItem().getId());
                amount.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, maxVal, inv.getSelectionModel().getSelectedItem().getQuantity()));
            }
        });

        amount.valueProperty().addListener((obs, oldValue, newValue) ->{
            Main.getStore().updateProductInCart(inv.getSelectionModel().getSelectedItem().getId(), amount.getValue());
            inv.refresh();
        });


        //********************************************************************************

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
    private void initSpinner() {
        amount.setPromptText("SLCT ITM");
    }




    public void removeProduct(){
        try {
            Product selectedItem = inv.getSelectionModel().getSelectedItem();
            inv.getItems().remove(selectedItem);
            Main.getStore().removeProductFromCart(selectedItem.getId());
        }
        catch (Exception e){
            invalidOperationError();
        }
    }

    public void confirmRemove(ActionEvent event){
        Alert dialogBox = new Alert(Alert.AlertType.CONFIRMATION);
        getStage(event).setAlwaysOnTop(true);
        dialogBox.initOwner(getStage(event));
        dialogBox.setTitle("Confirm Deletion".toUpperCase(Locale.ROOT));
        dialogBox.setHeaderText("The Item Will Be Removed From Cart".toUpperCase(Locale.ROOT));
        dialogBox.setContentText("Confirm?".toUpperCase(Locale.ROOT));

        Optional<ButtonType> result = dialogBox.showAndWait();
        if(result.get() == ButtonType.OK){
            removeProduct();
        }
    }

}
