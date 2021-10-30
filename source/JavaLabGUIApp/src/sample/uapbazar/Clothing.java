package sample.uapbazar;

import sample.uapbazar.enums.Category;
import sample.uapbazar.enums.Size;
import sample.uapbazar.enums.SubCategory;

public class Clothing extends Product{// make this a subclass of Product
    private String brand;
    private SubCategory subCategory; // Can use String as well
    private Size size; // Can avaoid enum if you want

    // constructor
    public Clothing(String name, String id, int quantity, String brand, SubCategory subCategory, double price, Size size) {
        super(name, id, Category.CLOTH, quantity, price);
        this.subCategory = subCategory;
        this.size = size;
        this.brand = brand;
    }

    // getter/setter
    public String getBrand(){
        return this.brand;
    }
    public String getSize(){
        return this.size.name();
    }
    public String getSubCategory(){
        return this.subCategory.name();
    }


    // Override the putOnSale method
    // This method will put an item on sale and also set the sale percentage
    // The item id ans sale percentage will be decided by the admin.
    @Override
    public void putOnSale(Object id, int percentage) {
        if(this.getId().equals(id)){ ///
        this.setSalePercent(percentage);
        this.setOnSale(true);
        }
    }

    // This details method is for admin user. Admin should be able to see everything.
    // Call details method of parent class and add other info specific to this class
    public String details() {
        return "-> Name:  " + getName() + " | ID: " + getId() + " | Price "+ totalPrice(this.getQuantity()) + " | Category: "+getCategory() +" | Quantitiy: "+ getQuantity() +" | Sale Perct: " + getSalePercent()+ " | Brand: " + getBrand() + " | Sub-Category: " + getSubCategory() + " | Size: " + getSize();
        
    }

}

