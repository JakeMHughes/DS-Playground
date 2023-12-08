package com.hughesportal.DSplayground;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Response {

    private Map<String, Object> masterResponse = new HashMap<>();


    Response(){}

    Response(String jsonResult, String contentType, String inputType){
        masterResponse.put("success", true);
        Map<String, Object> result = new HashMap<>();
        result.put("content", jsonResult);
        result.put("contentType", contentType);
        result.put("inputType", Objects.requireNonNullElse(inputType, "application/json"));
        result.put("encoding", "UTF-8");
        result.put("logs", new ArrayList<>());
        masterResponse.put("result", result);
    }

    Response(Map<String, Object> start, Map<String, Object> end, String message,String inputType){
        masterResponse.put("success", false);
        Map<String, Object> error = new HashMap<>();
        Map<String, Object> location = new HashMap<>();
        location.put("start", start);
        location.put("end", end);
        error.put("location", location);
        error.put("logs", new ArrayList<>());
        error.put("message", message);
        error.put("inputType", Objects.requireNonNullElse(inputType, "application/json"));
        masterResponse.put("error", error);
    }

    static public Map<String,Object> errorLoc(int column, int index, int line){
        Map<String, Object> loc = new HashMap<>();
        loc.put("column", column);
        loc.put("index", index);
        loc.put("line", line);
        return  loc;
    }


    public Map<String, Object> getMasterResponse() {
        return masterResponse;
    }

    public void setMasterResponse(Map<String, Object> masterResponse) {
        this.masterResponse = masterResponse;
    }
}
