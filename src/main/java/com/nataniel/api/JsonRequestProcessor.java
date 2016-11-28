package com.nataniel.api;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by chen on 18/03/2016.
 */
@Component
public class JsonRequestProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        String jsonString = (String) exchange.getIn().getHeader("jsonContent");
        if(jsonString == null || jsonString.trim().equals("")){
            return;
        }
        if(jsonString.startsWith("[")){
            JSONArray array = new JSONArray(jsonString);
            exchange.getIn().setHeader("jsonRequest", array);
        }else{
            JSONObject object = new JSONObject(jsonString);
            exchange.getIn().setHeader("jsonRequest", object);
        }
    }
}