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
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.chart.*;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author csstudent
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private BarChart<String, Number> chart;
    
    @FXML
    private Slider min;
    
    @FXML
    private Slider max;
    
    @FXML
    private Label minLabel;
    
    @FXML
    private Label maxLabel;
    
    private Dataset data;
    
    @FXML
    private void handleChangeFilter(MouseEvent event) {
        System.out.println("Clicked slider");
        graphData(min.getValue(), max.getValue());
        minLabel.setText("Minimum " + Math.round(min.getValue()) + "%");
        maxLabel.setText("Maximum " + Math.round(max.getValue()) + "%");
    }
    
    @FXML
    private void handleClose(Event event) {
        System.out.println("Clicked close");
        System.exit(0);
    }
    
    @FXML
    private void handleHelp(Event event) {
        System.out.println("Clicked help");
    }
    
    private Dataset getData(){
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
        return dataset;
    }
    
    private void graphData(double minVal, double maxVal){
        XYChart.Series<String, Number> immunizations = new XYChart.Series();
        immunizations.setName("immunizations");
        chart.setTitle("Polio Immunizations");
        chart.getXAxis().setLabel("Country");
        chart.getYAxis().setLabel("% Polio Immunizations");
        
        TreeSet<String> countries = new TreeSet();
        
        System.out.println("Polio Immunizations");
        Location[] locations = data.getLocations();
        for(Location location : locations){
            String country = location.getDim().getCountry();
            int value = location.getValue();
            if(country != null && !countries.contains(country)){
                if (value >= minVal && value <= maxVal){
                    System.out.println(country + ": " + value + "%");
                    immunizations.getData().add(new XYChart.Data(country, value));
                    countries.add(country);
                }
            }
        }
        
        chart.getData().setAll(immunizations);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        data = getData();
        
        graphData(0, 100);
        
        System.out.println(min.getValue());
        
    }    
    
}
