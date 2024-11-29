package team08.oop.lms.model;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Geometry {
    
    private List<List<Double>> coordinates;

    public Geometry(){

    }

    public Geometry(List<List<Double>> coordinates){
        this.coordinates = coordinates;
    }

    public List<List<Double>> getCoordinates(){
        return coordinates;
    }

    public void setCoordinates(List<List<Double>> coordinates){
        this.coordinates = coordinates;
    }
}
