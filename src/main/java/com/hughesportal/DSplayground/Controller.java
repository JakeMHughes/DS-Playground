package com.hughesportal.DSplayground;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    Services service;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/keywords")
    public ResponseEntity<?> getKeywords() throws IOException {
        return service.getKeywords();
    }

    @PostMapping(value = "/keywords/script", produces = "application/json")
    public ResponseEntity<?> getScriptVariables(@RequestBody String value){
        return service.getScriptVariables(value);
    }

    @PostMapping(value = "/keywords/payload", produces = "application/json")
    public ResponseEntity<?> getPayloadVariables(@RequestBody String value){
        return service.getPayloadVariables(value);
    }

    @GetMapping("/docs")
    public ResponseEntity<?> getDocs() throws IOException {
        return  service.getDocs();
    }

    @PostMapping("/transform")
    public ResponseEntity<?> transform(@RequestBody DSMap obj){
        return service.transformLogic(obj);
    }
}