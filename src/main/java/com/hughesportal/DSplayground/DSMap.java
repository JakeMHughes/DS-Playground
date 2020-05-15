package com.hughesportal.DSplayground;

import java.util.ArrayList;

public class DSMap {

    private ArrayList<Inputs> inputs = new ArrayList<>();
    private Object resources;



    DSMap(){}
    DSMap(ArrayList<Inputs> inputs, String res){
        this.inputs = inputs;
        this.resources = res;
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
