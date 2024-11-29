package team08.oop.lms.model;

import java.util.*;

public class FinalResult {
    
    private List<Result> list_result;
    
    public FinalResult(List<Result> list_result){
        this.list_result = list_result;
    }

    public List<Result> getList_result(){
        return list_result;
    }

    public void setList_result(List<Result> list_result){
        this.list_result = list_result;
    }
}
