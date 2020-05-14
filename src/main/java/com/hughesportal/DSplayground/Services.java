package com.hughesportal.DSplayground;

import com.datasonnet.Document;
import com.datasonnet.Mapper;
import com.datasonnet.StringDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class Services {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public ResponseEntity<?> transformLogic(DSMap input_data){

        logger.info(input_data.toString());
        String payload = "";
        String payloadType="";
        String resource= input_data.getResources().toString();
        String script = resource.substring(resource.indexOf('=')+1, resource.lastIndexOf('}'));
        logger.info("Script: " + script);

        Map<String, Document> variables = new HashMap<>();
        for(Inputs var : input_data.getInputs()){
            if(var.getName().equals("payload")){
                payload=var.getContent();
                payloadType=var.getContentType();
            }else {
                variables.put(var.getName(), new StringDocument(var.getContent(), var.getContentType()));
            }
        }
        Response resp = null;
        try {
            Mapper mapper = new Mapper(script, variables.keySet(), true);
            Document transformedResult = mapper.transform(new StringDocument(payload, payloadType), variables, "application/json");
            String jsonResult = transformedResult.contents();
            logger.info("RESULT: " + jsonResult);

            resp = new Response(jsonResult, "application/json");
        }
        catch (Exception e){
            resp = new Response(Response.errorLoc(0,0,0), Response.errorLoc(0,0,0), e.getMessage());
        }
        return  ResponseEntity.ok(resp.getMasterResponse());
    }
}
