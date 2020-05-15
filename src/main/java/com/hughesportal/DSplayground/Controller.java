package com.hughesportal.DSplayground;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@org.springframework.stereotype.Controller
public class Controller {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    Services service;

    @GetMapping("/")
    public String index(){
        return "index";
    }


    @PostMapping("/transform")
    public ResponseEntity<?> transform(@RequestBody DSMap obj){
        logger.info(obj.toString());
        return service.transformLogic((DSMap) obj);
    }
}