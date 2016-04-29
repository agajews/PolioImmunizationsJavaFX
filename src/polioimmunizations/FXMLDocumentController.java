/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polioimmunizations;

import com.google.gson.*;
import java.net.*;
import java.util.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.chart.*;
import javafx.scene.control.Alert.AlertType;
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
        max.setMin(0);
        max.setMax(100);
        min.setMin(0);
        min.setMax(100);
        if(max.getValue() > 5){
            min.setMax(max.getValue()-5);
        }else{
            max.setMin(min.getValue()+5);
        }
        graphData(min.getValue(), max.getValue());
        Settings.getInstance().setMin(min.getValue());
        Settings.getInstance().setMax(max.getValue());
        updateSliderLabels();
    }
    
    @FXML
    private void handleClose(Event event) {
        System.out.println("Clicked close");
        Settings.save();
        System.exit(0);
    }
    
    @FXML
    private void handleHelp(Event event) {
        System.out.println("Clicked help");
        Alert alert = new Alert(AlertType.INFORMATION, "Hi, I'm the developer.", ButtonType.OK);
        alert.showAndWait();
    }
    
    private void updateSliderLabels(){
        minLabel.setText("Minimum " + Math.round(min.getValue()) + "%");
        maxLabel.setText("Maximum " + Math.round(max.getValue()) + "%");
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
        
        //System.out.println("Polio Immunizations");
        Location[] locations = data.getLocations();
        for(Location location : locations){
            String country = location.getDim().getCountry();
            int value = location.getValue();
            if(country != null && !countries.contains(country)){
                if (value >= minVal && value <= maxVal){
                    //System.out.println(country + ": " + value + "%");
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
        
        Settings settings = Settings.getInstance();
        double minVal = settings.getMin();
        double maxVal = settings.getMax();
        System.out.println(minVal);
        System.out.println(maxVal);
        
        graphData(minVal, maxVal);
        min.setValue(minVal);
        max.setValue(maxVal);
        updateSliderLabels();
        
    }    
    
}
