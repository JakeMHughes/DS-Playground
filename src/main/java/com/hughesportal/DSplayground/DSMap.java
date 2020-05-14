package com.hughesportal.DSplayground;

import java.util.ArrayList;

public class DSMap {

    private String main="";
    private ArrayList<Inputs> inputs = new ArrayList<>();
    private Object resources;



    DSMap(){}
    DSMap(String main, ArrayList<Inputs> input, String res){
        this.main = main;
        this.inputs = input;
        this.resources = res;
    }


    public String getMain() {
        return main;
    }

    public ArrayList<Inputs> getInputs() {
        return inputs;
    }

    public Object getResources() {
        return resources;
    }

    @Override
    public String toString() {
        String content = "{\n";
        content += "\t\"main\": \"" + main +"\",\n";
        content += "\t\"inputs\":";
        if(inputs.size()==0){
            content +="[],\n";
        }
        else{
            content +="[\n";
            for(Inputs in : inputs){
                if(inputs.indexOf(in) == inputs.size()) {
                    content += in.toString() +"\n";
                }else{
                    content += in.toString() +",\n";
                }
            }
            content+="],\n";
        }
        content+="\"resources\": "+ resources.toString() + "\n";
        content+="}";

        return content;
    }
}
