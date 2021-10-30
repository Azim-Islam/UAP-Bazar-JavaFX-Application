package sample.uapbazar;

import sample.uapbazar.enums.Category;

import java.time.LocalDate;


public class FoodItem extends Product{     // make this a subclass of Product
    private LocalDate mfgDate, expDate;
    
    // constructor
    public FoodItem(String name, String id, int quantity, LocalDate mfg, LocalDate exp,double price) {
        super(name, id, Category.FOOD, quantity, price);
        this.mfgDate = mfg;
        this.expDate = exp;
    }

    // This method will put an item on sale if the item expires 
    // within next few days which will be decided by the admin.
    @Override
    public void putOnSale(Object expireWithin, int p) {
        LocalDate today = LocalDate.now();
        if(this.expDate.compareTo(today) <= ((int)expireWithin)){
             this.setOnSale(true);
             this.setSalePercent(p);
        }
    }

    public String getBrand(){ return "";}

    public LocalDate getMfgDate(){
        return this.mfgDate;
    }

    public LocalDate getExpDate(){
        return this.expDate;
    }


    
    // This details method is for admin user. Admin should be able to see everything.
    public String details() {
        return "-> Name:  " + getName() + " | ID: " + getId() +  " | Price "+ totalPrice(this.getQuantity()) + " | Category: "+ getCategory() +" | Quantitiy: "+ getQuantity() +" | Sale Perct: " + getSalePercent() +" | ExpDate: "+ getExpDate() + " | MFG Date: "+ getMfgDate();
    }    
}