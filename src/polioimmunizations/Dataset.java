/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polioimmunizations;

/**
 *
 * @author csstudent
 */
public class Dataset {
    private Legend[] dimension;
    private Location[] fact;
    
    public Legend[] getDimensions(){
        return dimension;
    }
    public Location[] getLocations(){
        return fact;
    }
}
