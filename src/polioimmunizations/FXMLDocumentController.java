/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polioimmunizations;

import com.google.gson.*;
import java.net.*;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.chart.*;

/**
 *
 * @author csstudent
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private BarChart<String, Number> chart;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String s = "http://apps.who.int/gho/athena/data/GHO/WHS4_544.json?profile=simple&filter=YEAR:1980";
        URL address = null;
        try {
            address = new URL(s);
        } catch (Exception e) {
            System.out.println("Improper URL " + s);
            System.exit(-1);
        }
     
        // read from the URL
        Scanner scan = null;
        try {
            scan = new Scanner(address.openStream());
        } catch (Exception e) {
            System.out.println("Could not connect to " + s);
            System.exit(-1);
        }
        
        String str = new String();
        while (scan.hasNext()) {
            str += scan.nextLine() + "\n";
        }
        scan.close();

        Gson gson = new Gson();
        Dataset dataset = gson.fromJson(str, Dataset.class);
        
        XYChart.Series<String, Number> immunizations = new XYChart.Series();
        immunizations.setName("immunizations");
        chart.setTitle("Polio Immunizations");
        chart.getXAxis().setLabel("Country");
        chart.getYAxis().setLabel("% Polio Immunizations");
        
        TreeSet<String> countries = new TreeSet();
        
        System.out.println("Polio Immunizations");
        Location[] locations = dataset.getLocations();
        for(Location location : locations){
            String country = location.getDim().getCountry();
            int value = location.getValue();
            System.out.println(country + ": " + value + "%");
            if(country != null && !countries.contains(country)){
                System.out.println(value);
                immunizations.getData().add(new XYChart.Data(country, value));
                countries.add(country);
            }
        }
        
        chart.getData().add(immunizations);
        
        
    }    
    
}
