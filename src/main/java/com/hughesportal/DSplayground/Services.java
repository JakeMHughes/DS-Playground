package com.hughesportal.DSplayground;

import com.datasonnet.Document;
import com.datasonnet.Mapper;
import com.datasonnet.StringDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@org.springframework.stereotype.Service
public class Services {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public ResponseEntity<?> transformLogic(DSMap input_data){

        String payload = "";
        String payloadType="";
        String script = input_data.getResources().toString();
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

    public ResponseEntity<?> getKeywords() throws IOException {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type",
                "application/json");

        File resource = new ClassPathResource("keywords.json").getFile();
        String keywords = new String(Files.readAllBytes(resource.toPath()));

        return ResponseEntity.ok().headers(responseHeaders).body(keywords);
    }

    public ResponseEntity<?> getDocs() throws IOException {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type",
                "application/json");

        File nav = new File("./docs/nav.md");
        String navStr = new String(Files.readAllBytes(nav.toPath()));

        File docs = new File("./docs/documentation.md");
        String docsStr = new String(Files.readAllBytes(docs.toPath()));

        return ResponseEntity.ok().headers(responseHeaders).body(new Documentation(navStr,docsStr));
    }
}
