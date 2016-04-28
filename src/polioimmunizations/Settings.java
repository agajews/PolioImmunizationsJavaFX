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
            try{
                if(settingsFileExists()) {
                    System.out.println("Settings file exists");
                    instance = load();
                }
                else {
                    instance = new Settings();
                }
            }
            catch(IOException e) {
                System.out.println("IOException");
            }
        }
        return instance;
    }
    
    public static boolean settingsFileExists() {
        return new File(filename).exists();
    }
    
    public static void save() throws IOException {
        FileOutputStream fileOut = new FileOutputStream(filename);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(getInstance());
        out.close();
        fileOut.close();
        System.out.println("Saved");
    }
    
    public static Settings load() throws IOException {
        FileInputStream fileIn = new FileInputStream(filename);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        Settings obj = null;
        try{
            obj = (Settings) in.readObject();
        }
        catch(ClassNotFoundException e){
            System.out.println("ClassNotFoundException");
        }
        in.close();
        fileIn.close();
        return obj;
    }
    
    /*
        Instance
    */
    private double minVal = 0;
    private double maxVal = 100;
    
    private Settings(){}
    
    public void setMin(double minVal){
        minVal = minVal;
    }
    
    public double getMin(){
        return minVal;
    }
    
    public void setMax(double maxVal){
        maxVal = maxVal;
    }
    
    public double getMax(){
        return maxVal;
    }
}
