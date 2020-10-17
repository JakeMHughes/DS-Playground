package com.hughesportal.DSplayground;

import com.datasonnet.Mapper;
import com.datasonnet.document.Document;
import com.datasonnet.document.StringDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class Services {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public ResponseEntity<?> transformLogic(DSMap input_data){

        String payload = "";
        String payloadType="";
        String script = input_data.getResources().toString();
        //logger.info("Script: " + script);



        Map<String, Document> variables = new HashMap<>();
        for(Inputs var : input_data.getInputs()){
            if(var.getName().equals("payload")){
                payload=var.getContent();
                payloadType=var.getContentType();
            }else {
                variables.put(var.getName(), new StringDocument(var.getContent(), var.getContentType()));
            }
        }
        Response resp;
        try {
            Mapper mapper = new Mapper(script, variables.keySet(), true);
            Document transformedResult = mapper.transform(new StringDocument(payload, payloadType), variables, "application/json");
            String jsonResult = transformedResult.getContentsAsString();

            resp = new Response(jsonResult, "application/json");
        }
        catch (Exception e){
            resp = new Response(Response.errorLoc(0,0,0), Response.errorLoc(0,0,0), e.getMessage());
        }
        return  ResponseEntity.ok(resp.getMasterResponse());
    }

    public ResponseEntity<?> getScriptVariables(String script){
        ArrayList<Keyword> keywords = new ArrayList<>();
        Matcher scriptMatcher = Pattern.compile("local\\s+(\\S+|\\S+(\\(.*?\\))?)=(.*);")
                .matcher(script.replaceAll("(\n|\\s+)", " "));
        while(scriptMatcher.find()){
            String name = scriptMatcher.group(1);
            if(name.contains("(")){
                keywords.add(new Keyword(name.substring(0, name.indexOf("(")), name, ""));
                //TODO
            }
            else{
                keywords.add(new Keyword(name, name, ""));
            }
        }
        HttpHeaders responseHeaders = new HttpHeaders();
        return ResponseEntity.ok().headers(responseHeaders).body(keywords);
    }

    private final Mapper payloadVariableMapper = new Mapper(
                    "local getKeys(key, item)=\n" +
                            "    if(std.type(item) == \"object\") then\n" +
                            "        [   getKeys(key+\".\"+objKey, item[objKey])\n" +
                            "            for objKey in std.objectFields(item)] + [key]\n" +
                            "    else if(std.type(item) == \"array\") then\n" +
                            "        std.map(function(x) getKeys(key, x), item)\n" +
                            "    else \n" +
                            "        [key];\n" +
                            "\n" +
                            "std.uniq(\n" +
                            "    DS.Util.deepFlattenArrays(\n" +
                            "        getKeys(\"payload\", payload) + [\"payload\"]\n" +
                            "    )\n" +
                            ")",Set.of(),true);


    public ResponseEntity<?> getPayloadVariables(String payload){
        logger.info("HERE");
        try {
            Document doc = payloadVariableMapper
                    .transform(new StringDocument(payload, "application/json"), Map.of(), "application/json");

            logger.info(doc.getContentsAsString());

            List<Keyword> values = Arrays.stream(doc.getContentsAsString()
                    .replaceAll("\"", "")
                    .replace("[", "")
                    .replace("]", "")
                    .split(",").clone())
                    .map(item -> new Keyword(item, item, ""))
                    .collect(Collectors.toList());
            return  ResponseEntity.ok(values);
        } catch (Exception e){
            logger.info("Invalid json");
            return ResponseEntity.status(400).body(null);
        }
    }

    public ResponseEntity<?> getKeywords() throws IOException {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type",
                "application/json");

        File wordsFile = new File("./docs/keywords.json");
        String wordsStr = new String(Files.readAllBytes(wordsFile.toPath()));

        return ResponseEntity.ok().headers(responseHeaders).body(wordsStr);
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
