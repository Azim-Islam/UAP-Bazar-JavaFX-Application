package sample.uapbazar;

import sample.uapbazar.enums.Category;
import sample.uapbazar.enums.ElectCategory;

public class Electronics extends Product{ // make this a subclass of Product
    private String manufacturer;
    private ElectCategory subCategory; // Can be String or other data type as well
    
    // Constructor
    public Electronics(String name, String id, int quantity, String manufacturer, ElectCategory subCategory, double price) {
        super(name, id, Category.ELECTRONICS, quantity, price);
        this.manufacturer = manufacturer;
        this.subCategory = subCategory;
    }
    
    // getter/setter
    public String getManufacturer(){
        return this.manufacturer;
    }

    public String getBrand(){ return  this.manufacturer;}

    public String getSubCategory(){
        return this.subCategory.name();
    }
    
    // Override the putOnSale method
    // This method will put an item on sale and also set the sale percentage
    // The item id and sale percentage will be decided by the admin.
    @Override
    public void putOnSale(Object id, int percentage) {
        this.setSalePercent(percentage);
        this.setOnSale(true);
    }
    
    // This details method is for admin user. Admin should be able to see everything.
    public String details() {
        return "-> Name:  " + getName() + " | ID: " + getId() + " | Price "+ totalPrice(this.getQuantity()) + " | Category: "+getCategory() +" | Quantitiy: "+ getQuantity() +" | Sale Perct: " + getSalePercent()+ " | Manufacturer:" + getManufacturer() + " | Sub-Category" + getSubCategory();
}}

