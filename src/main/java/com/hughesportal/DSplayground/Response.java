package com.hughesportal.DSplayground;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Response {

    private Map<String, Object> masterResponse = new HashMap<>();
    private Map<String, Object> result;
    private Map<String, Object> error;


    Response(){}

    Response(String jsonResult, String contentType){
        masterResponse.put("success", true);
        result = new HashMap<>();
        result.put("content", jsonResult);
        result.put("contentType", contentType);
        result.put("encoding", "UTF-8");
        result.put("logs", new ArrayList<>());
        masterResponse.put("result", result);
    }

    Response(Map<String, Object> start, Map<String, Object> end, String message){
        masterResponse.put("success", false);
        error = new HashMap<>();
        Map<String, Object> location = new HashMap<>();
        location.put("start", start);
        location.put("end", end);
        error.put("location", location);
        error.put("logs", new ArrayList<>());
        error.put("message", message);
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

    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }

    public Map<String, Object> getError() {
        return error;
    }

    public void setError(Map<String, Object> error) {
        this.error = error;
    }
}
