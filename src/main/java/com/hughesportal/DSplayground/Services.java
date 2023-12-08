package com.hughesportal.DSplayground;

import com.datasonnet.Mapper;
import com.datasonnet.MapperBuilder;
import com.datasonnet.document.DefaultDocument;
import com.datasonnet.document.Document;
import com.datasonnet.document.MediaType;
import com.datasonnet.document.MediaTypes;
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
        String script = input_data.getResources().toString();

        Map<String, Document<?>> variables = new HashMap<>();
        for(Inputs var : input_data.getInputs()){
            if(var.getName().equals("payload")){
                payload=var.getContent();
            }else {
                variables.put(var.getName(), new DefaultDocument<>(var.getContent(), MediaType.parseMediaType(var.getContentType())));
            }
        }

        String inputType = "application/json";
        Matcher headerMatcher = Pattern.compile("\\/\\*\\* DataSonnet.*\\*\\/")
                .matcher(script.replaceAll("\n", " "));
        if(headerMatcher.find()){
            String header = headerMatcher.group(0);
            Matcher subMatcher = Pattern.compile("input \\s*payload \\s*(\\S*[^*]\\/[^ *]*)").matcher(header);
            if(subMatcher.find()){
                inputType = subMatcher.group(1);
            }
        }


        Response resp = null;
        try {
            Document<String> doc = new MapperBuilder(script).build()
                    .transform(new DefaultDocument<>(payload, MediaTypes.UNKNOWN),Map.of(), MediaTypes.ANY,String.class);
            resp = new Response(doc.getContent().toString(), doc.getMediaType().toString(),inputType);
        }
        catch (Exception e){
            resp = new Response(Response.errorLoc(0,0,0), Response.errorLoc(0,0,0),
                    Objects.requireNonNullElse(e.getMessage(), "Unknown Error"), inputType);
        }
        System.out.println("Returning: " + resp.getMasterResponse());
        return  ResponseEntity.ok(resp.getMasterResponse());
    }


    public ResponseEntity<?> getScriptVariables(String script){
        ArrayList<Keyword> keywords = new ArrayList<>();
        Matcher scriptMatcher = Pattern.compile("local\\s+(\\S+|\\S+(\\(.*?\\))?)\\s?=(.*);")
                .matcher(script.replaceAll("(\n|\\s+)", " ").replaceAll(";", ";\n"));
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

    private final Mapper payloadVariableMapper = new MapperBuilder(
            "/** DataSonnet\n" +
                    "version=2.0\n" +
                    "output application/json\n" +
                    "input payload application/json\n" +
                    "*/\n" +
                    "local getKeys(key, item, update=\"\")=\n" +
                    "    if(ds.typeOf(item) == \"object\") then\n" +
                    "        ds.mapEntries(\n" +
                    "            item, \n" +
                    "            function(value,mapKey,index)\n" +
                    "                getKeys(key+\".\"+mapKey, value)\n" +
                    "        ) + [key]\n" +
                    "    else if(ds.typeOf(item) == \"array\") then\n" +
                    "        ds.map(\n" +
                    "            item, \n" +
                    "            function(item,index)\n" +
                    "                getKeys(key+\"[]\", item)\n" +
                    "        ) + [key]\n" +
                    "    else\n" +
                    "        [key+update];\n" +
                    "\n" +
                    "std.uniq(\n" +
                    "    ds.arrays.deepFlatten(\n" +
                    "        getKeys(\"payload\", payload) + [\"payload\"]\n" +
                    "    )\n" +
                    ")\n").build();


    public ResponseEntity<?> getPayloadVariables(String payload){
        try {
            Document<String> doc = payloadVariableMapper
                    .transform(new DefaultDocument<>(payload, MediaTypes.UNKNOWN));
            String content = doc.getContent();
            List<Keyword> values = Arrays.stream(content
                    .substring(1, content.length()-1)
                    .replaceAll("\"", "")
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
        responseHeaders.set("Content-Type", "application/json");

        File nav = new File("./docs/nav.md");
        String navStr = new String(Files.readAllBytes(nav.toPath()));

        File docs = new File("./docs/documentation.md");
        String docsStr = new String(Files.readAllBytes(docs.toPath()));

        return ResponseEntity.ok().headers(responseHeaders).body(new Documentation(navStr,docsStr));
    }
}
