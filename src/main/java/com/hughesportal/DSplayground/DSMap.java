package com.hughesportal.DSplayground;

import java.util.ArrayList;

public class DSMap {

    /* example:
{
"inputs": [
{
  "name": "payload",
  "content": "ewogICJodG1sIjogewogICAgImhlYWQiOiB7CiAgICAgICJ0aXRsZSI6IHsKICAgICAgICAiJCI6ICIgUGFnZSB0aXRsZSAiLAogICAgICAgICJ+IjogMQogICAgICB9LAogICAgICAifiI6IDEKICAgIH0sCiAgICAiYm9keSI6IHsKICAgICAgImhsIjogewogICAgICAgICIkIjogIkZpcnN0IEhlYWRpbmciLAogICAgICAgICJ+IjogMQogICAgICB9LAogICAgICAicCI6IHsKICAgICAgICAiJCI6ICJGaXJzdCBwYXJhZ3JhcGguIiwKICAgICAgICAifiI6IDIKICAgICAgfSwKICAgICAgIn4iOiAyCiAgICB9LAogICAgIn4iOiAxCiAgfQp9",
  "contentType": "application/json"
}
],
"resources": "/** DataSonnet\nversion=2.0\noutput application/xml\ninput payload application/json\n\*\/payload"
}
 */
    private ArrayList<Inputs> inputs = new ArrayList<>();
    private Object resources;

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
        StringBuilder content = new StringBuilder("{\n");
        content.append("\t\"inputs\":");
        if(inputs.size()==0){
            content.append("[],\n");
        }
        else{
            content.append("[\n");
            for(Inputs in : inputs){
                if(inputs.indexOf(in) == inputs.size()) {
                    content.append(in.toString()).append("\n");
                }else{
                    content.append(in.toString()).append(",\n");
                }
            }
            content.append("],\n");
        }
        content.append("\"resources\": ").append(resources.toString()).append("\n");
        content.append("}");

        return content.toString();
    }
}
