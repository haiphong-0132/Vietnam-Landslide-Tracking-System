package team08.oop.lms.model;

import java.util.*;

public class Result {
    
    private String city, type, type_struct;
    private List<MiniFeature> list_minifeature;

    public Result() {

    }

    public String getCity(){
        return city;
    }

    public String getType(){
        return type;
    }

    public String getType_struct(){
        return type_struct;
    }

    public void setCity_name(String city){
        this.city = city;
    }

    public void setList_minifeature(List<MiniFeature> list_minifeature){
        this.list_minifeature = list_minifeature;
    }

    public List<MiniFeature> getList_minifeature(){
        return list_minifeature;
    }

    public void setType(String type){
        this.type = type;
    }

    public Result(String city, String type, String type_struct, List<MiniFeature> list_minifeature){
        this.city= city;
        this.type = type;
        this.type_struct = type_struct;
        this.list_minifeature = list_minifeature;
    }
}
