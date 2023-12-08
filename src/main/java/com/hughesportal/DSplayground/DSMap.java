package com.hughesportal.DSplayground;

import java.util.ArrayList;
import java.util.Map;

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
    private final ArrayList<Map<String,String>> inputs;
    private final String resources;

    DSMap(ArrayList<Map<String,String>> inputs, String res){
        this.inputs = inputs;
        this.resources = res;
    }

    public ArrayList<Map<String,String>> getInputs() {

        return inputs;
    }

    public String getResources() {
        return resources;
    }
}
