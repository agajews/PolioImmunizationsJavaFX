/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polioimmunizations;

import java.io.*;

/**
 *
 * @author csstudent
 */
public class Settings implements Serializable{
    /*
        Static
    */
    private static Settings instance = null;
    private static String filename = "/tmp/polioimmunizations.ser";
    
    public static Settings getInstance() {
        if(instance == null){
            if(settingsFileExists()) {
                System.out.println("Settings file exists");
                instance = load();
                System.out.println("Saved vals: min=" + instance.getMin() + ", max=" + instance.getMax());
            }
            else {
                instance = new Settings();
            }
        }
        return instance;
    }
    
    public static boolean settingsFileExists() {
        return new File(filename).exists();
    }
    
    public static void save() {
        try{
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(getInstance());
            out.close();
            fileOut.close();
        }
        catch(IOException e){
            System.out.println("IOException");
        }
        System.out.println("Saved");
    }
    
    public static Settings load() {
        Settings obj = null;
        try{
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            obj = (Settings) in.readObject();
            in.close();
            fileIn.close();
        }
        catch(ClassNotFoundException e){
            System.out.println("ClassNotFoundException");
        }
        catch(IOException e){
            System.out.println("IOException");
        }
        return obj;
    }
    
    /*
        Instance
    */
    private double minVal = 0;
    private double maxVal = 100;
    
    private Settings(){}
    
    public void setMin(double val){
        minVal = val;
        System.out.println("New min: " + minVal);
    }
    
    public double getMin(){
        return minVal;
    }
    
    public void setMax(double val){
        maxVal = val;
        System.out.println("New max: " + maxVal);
    }
    
    public double getMax(){
        return maxVal;
    }
}
