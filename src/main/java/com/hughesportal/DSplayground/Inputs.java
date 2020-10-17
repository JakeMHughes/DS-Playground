package com.hughesportal.DSplayground;

import java.util.Base64;

public class Inputs {

    private final String name;
    private final String content;
    private String contentType="application/json";

    Inputs(String name, String content, String contentType){
        this.name = name;
        this.content = content;
        this.contentType = contentType;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return new String(Base64.getDecoder().decode(content));
    }

    public String getContentType() {
        return contentType;
    }

    @Override
    public String toString() {
        String content = "\t{\n";

        content+="\t\t\"name\": \"" + getName() +"\",\n";
        content+="\t\t\"content\": \"" + getContent().replace("\n","") +"\",\n";
        content+="\t\t\"contentType\": \"" + getContentType() +"\",\n";

        content +="\t}";
        return content;
    }
}
