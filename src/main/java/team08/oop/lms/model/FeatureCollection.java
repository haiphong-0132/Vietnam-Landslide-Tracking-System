package team08.oop.lms.model;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class FeatureCollection {
    private String type;
    private List<Feature> features;


    public FeatureCollection(){

    }

    public FeatureCollection(String type, List<Feature> features){
        this.type = type;
        this.features = features;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public List<Feature> getFeatures(){
        return features;
    }

    public void setFeatures(List<Feature> features){
        this.features = features;
    }
}
