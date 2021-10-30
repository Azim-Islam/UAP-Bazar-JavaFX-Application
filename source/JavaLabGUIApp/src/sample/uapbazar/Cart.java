package sample.uapbazar;

import java.util.ArrayList;

public class Cart{
    ArrayList<Product> items= new ArrayList<>();
    
    public void addProduct(Product p) {
        // Add p to products;
        items.add(p);
    }
    
        
    // Search for the item in the items list and remove the item;
    public void removeProduct(String id) {    
        Product tmp = findProduct(id);
        if(tmp != null){
            items.remove((tmp));
        }
        else{
            System.out.println("-> Product not found!");
        }
    }
    
    // Search for the item in the items list and update the quantity of the item;
    public void updateProduct(String id, int count) {
        Product tmp = findProduct(id);
        if(tmp != null){
            tmp.setQuantity(count);
        }
        else{
            System.out.println("-> Product not found!");
        }
    }
    
    // View the items in the cart and their details which include the name, quantity and price
    // If any item is on sale, the discounted price will be shown
    public void viewCart() {
        for(Product product: items){
            System.out.println(product.details());
            if(product.getOnSale()){
                System.out.println("-> Sale Price: "+ product.salePrice(product.getQuantity())); ////<-
            }
        }
    }
    
    // clear the items in the cart
    public void clearCart() {        
        items.clear();
    }
    
    // Search for a specific product and return the index in the items arrayList.
    // If the product is not available return -1
    private Product findProduct(String id) {          
        for(Product product: items){
            if(product.getId().equals(id)){
                return product;
            }
        }
        return null;
    }

}
