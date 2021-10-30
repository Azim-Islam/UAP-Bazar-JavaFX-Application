package sample;

import java.io.*;

public class DataHandler {
    String path = "";

    public static void writeObject(Object obj){
        try{
            FileOutputStream fileOut = new FileOutputStream(new File("store.txt"));
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(obj);
            objectOut.close();
            fileOut.close();
            System.out.println("Saved!");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Object readObject(){
        try{
            FileInputStream fileIn = new FileInputStream(new File("store.txt"));
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            System.out.println("Loaded!");
            return objectIn.readObject();

        }
        catch (Exception e){
            //e.printStackTrace();
            return null;
        }
    }
}