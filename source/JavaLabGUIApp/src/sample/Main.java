package sample;


import sample.uapbazar.Store;

public class Main{

    public static Store uapbazar = new Store("UAP BAZAR Inc.");


    public static void setStore(Store tmp) {Main.uapbazar = tmp;}

    public static Store getStore(){
        return uapbazar;
    }
    public static void main(String[] args) {

        sample.Controller.SceneSwitcher.loadStore();
        startGUI.main(args);

    }

}
