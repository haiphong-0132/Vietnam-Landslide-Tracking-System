package team08.oop.lms.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Feature {
    
    private Geometry geometry;
    private Properties properties;

    public Feature(){

    }

    public Feature(Geometry geometry, Properties properties){
        this.geometry = geometry;
        this.properties = properties;
    }


    public Geometry getGeometry(){
        return geometry;
    }

    public void setGeometry(Geometry geometry){
        this.geometry = geometry;
    }

    public Properties getProperties(){
        return properties;
    }

    public void setProperties(Properties properties){
        this.properties = properties;
    }
    
}
