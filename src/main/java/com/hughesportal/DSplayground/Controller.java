package com.hughesportal.DSplayground;

import com.datasonnet.Document;
import com.datasonnet.Mapper;
import com.datasonnet.StringDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.LogManager;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    Services service;

    @GetMapping("/")
    public String index(){
        return "index2";
    }


    @PostMapping("/transform")
    public ResponseEntity<?> transform(@RequestBody DSMap input_data){
        return service.transformLogic(input_data);
    }
}
