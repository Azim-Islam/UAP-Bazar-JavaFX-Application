package sample.uapbazar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.uapbazar.enums.Category;
import sample.uapbazar.enums.ElectCategory;
import sample.uapbazar.enums.Size;
import sample.uapbazar.enums.SubCategory;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;


public class Store implements Serializable {
    public String name;
    ArrayList<Product> products = new ArrayList<>();
    transient public Cart cart = new Cart();



    public ObservableList<Product> getInventory(){
        return FXCollections.observableArrayList(products);
    }

    public ObservableList<Product> getCart(){
        return FXCollections.observableArrayList(cart.items);
    }
    public Store(String name){
        this.name = name;
    }

    // Constructor
    
    // ************** Cart related methods *******************

    public String addProductToCart(String id, int amt) throws CloneNotSupportedException{
        // Call findProduct. If the product exist and the store has enough quantity,
        // Call the addProduct method of Cart class. Otherwise show "Out of Stock" message
        // 
        Product tmp = findProduct(id);
        
        if(tmp != null) {
                Product p = (Product) tmp.clone(); 
                if (p.getQuantity()>= amt) {
                p.setQuantity(amt);
                cart.addProduct(p);
                return "String";
            }
            else {
                return "Out Of Stock or Not Enough Product.";
            }
        }
        else {
             return "Product does not exist";
            }
        
    }


    public void removeProductFromCart(String id) {
        // call removeProduct of Cart class
        cart.removeProduct(id);
    }

    public void updateProductInCart(String id, int count) {
        // Call findProduct (String, ArrayList<>) and Call the updateProduct method of Cart class
        Product tmp = findProduct(id);
        if(tmp != null && tmp.getQuantity() >= count){
            cart.updateProduct(id, count);
        }
        else{
            System.out.println("-> Not enough in stock!");
        }
          

    }

    public void clearCart() {
        // Call the clearCart method of Cart class
        cart.clearCart();
    }

    // Pay Bill
    public void payBill() {
        // Iterate through each of the products in the cart and do the following
        // a) reduce those products quantity, 
        // b) If the item is on sale add the saleprice to the bill.
        // c) if not onSale add the totalPrice to bill. 
        // d) Call the clearCart method
        double bill = 0;
        Product tmp;
        for(Product product: cart.items){
            if(product.getOnSale()){
                bill += product.salePrice(product.getQuantity());
            }
            else{
                bill += product.totalPrice(product.getQuantity());
            }
            tmp = findProduct(product.getId());
            tmp.setQuantity(tmp.getQuantity()-product.getQuantity());
            //Remove product quantity from store.
        }

    }
    public String getBill(){
        double bill = 0;
        for(Product product: cart.items){
            if(product.getOnSale()){
                bill += product.salePrice(product.getQuantity());
            }
            else{
                bill += product.totalPrice(product.getQuantity());
            }
        }
        return ""+bill;

    }

    // ****************Store view related methods ******************
    
    // Admin will see all the information about each product. 
    // Non-admin will be able to view just the name, id and price of the product
    // Display the items in groups (Food, Electronics, Clothing)

    public int getQuantity(String id){
        return findProduct(id).getQuantity();
    }

    public void viewProducts(boolean isAdmin) {
        // if isAdmin is true call viewProductsAsAdmin for different categories
        // else call viewProducts for different categories
        if(isAdmin){
            for(Category product: Category.values()){
                viewProductCatAsAdmin(product);
            }
        }
        else{
            for(Category product: Category.values()){
                viewProductCat(product);
            }
        }
    }

    // View the list of items in a specific category. Show only name, id and price
    public void viewProductCat(Category category) {
        for(Product product: products){
            if(product.getCategory().equals(category)){
                System.out.println(product.toString());
            }
        }
    }

    // View the list of items in a specific category. Show all info about each product.
    // This functionality is for admin
    public void viewProductCatAsAdmin(Category category) {
        for(Product product: products){
            if(product.getCategory().equals(category)){
                System.out.println(product.details());
            }
        }
    }
    
    // *************** Admin methods to put a item(s) on sale*************
    public void giveSale(int percentage, String id){
        putOnSaleElectronic(id, percentage);
        putOnSaleCloth(id, percentage);
    }
    public void giveSale(int exp, int percentage){
        putOnSaleFood(exp, percentage);
    }
    // Put all food items on sale that will expire within next expireWithin days
    public void putOnSaleFood(Integer expireWithin, int percentage) {
        // Search for the items that will expire within next expireWithin days and call putOnSale for that item
        for(Product product: products){
            if(product.getCategory().equals(Category.FOOD)){
                ((FoodItem)product).putOnSale(expireWithin, percentage);
            }
        }
    }
    
    // Put a specific cloth item on sale
    public void putOnSaleCloth(String  id, int percentage) {
        // Search for the items with the specific id and call putOnSale for that item
        for(Product product: products){
            if(product.getCategory().equals(Category.CLOTH)){
                ((Clothing)product).putOnSale(id, percentage);
            }
        }
    }
    
    // Put a specific cloth item on sale
    public void putOnSaleElectronic(String  id, int percentage) {
        // Search for the items with the specific id and call putOnSale for that item
        for(Product product: products){
            if(product.getCategory().equals(Category.ELECTRONICS)){
                ((Electronics)product).putOnSale(id, percentage);
            }
        }
    }
    
    

    //****************************Admin methods to add and remove items in the store*********

    // This method is for adding clothing item to the store
    public void addProduct(String name, String id, int quantity, String b, SubCategory sub, Size size, double price) {
        // Call the addProduct(Product p) method with Clothing object as parameter
        Product tmp = new Clothing(name, id, quantity, b, sub, price, size);
        this.addProduct(tmp);
    }

    // This method is for adding Electronics item to the store
    public void addProduct(String name, String id, int quantity, String manufacturer, ElectCategory subCategory, double price) {
        // Call the addProduct(Product p) method with Electronics object as parameter
        Product tmp = new Electronics(name, id, quantity, manufacturer, subCategory, price);
        this.addProduct(tmp);
    }

    // This method is for adding Food item to the store
    public void addProduct(String name, String id, int quantity, LocalDate mfg, LocalDate exp,double price) {
        Product item = findProduct(id);
        if(item != null) {
            if (((FoodItem)item).getMfgDate().equals(mfg) && ((FoodItem)item).getExpDate().equals(exp)) {
                item.updateQuantity(quantity);
                return;                    
            }
        }
        products.add(new FoodItem(name, id, quantity, mfg, exp, price));
    }

    //Method to remove a product from the store
    public void removeProductFromStore(String id){
        Product tmp = findProduct(id);
        products.remove(tmp);
    }
    // ******************** private methods**************
    
    private Product findProduct(String id) {      
        // search the product in the products list using id. 
        // If the product exists return the product, otherwise return -1
        for(Product product: products){
            if(product.getId().equals(id)){
                return product;
            }
        }
        return null;
        
    }

    // the following method is a private method for only Clothing and Electronics/
    private void addProduct(Product p) {
        // Check if the item is available in the store.
        // If available increment the quantity. Else add the product in the list
        Product tmp = findProduct(p.getId());
        if(tmp != null){
            tmp.setQuantity(tmp.getQuantity() + p.getQuantity());
        }
        else{
            products.add(p);
        }
    }

}
