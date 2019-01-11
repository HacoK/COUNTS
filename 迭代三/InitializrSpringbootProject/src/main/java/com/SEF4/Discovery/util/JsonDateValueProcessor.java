/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SEF4.Discovery.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.JSONUtils;

/**
 *
 * @author Kevin
 */
public class JsonDateValueProcessor implements JsonValueProcessor {  
    private String format ="yyyy-MM-dd";  
    private static JsonConfig jsonConfig=null;
      
    public JsonDateValueProcessor() {  
        super();  
    }  
      
    public JsonDateValueProcessor(String format) {  
        super();  
        this.format = format;  
    }  
  
    @Override  
    public Object processArrayValue(Object paramObject,  
            JsonConfig paramJsonConfig) {  
        return process(paramObject);  
    }  
  
    @Override  
    public Object processObjectValue(String paramString, Object paramObject,  
            JsonConfig paramJsonConfig) {  
        return process(paramObject);  
    }  
      
      
    private Object process(Object value){  
        if(value instanceof Date){    
            SimpleDateFormat sdf = new SimpleDateFormat(format,Locale.CHINA);    
            return sdf.format(value);  
        }    
        return value == null ? "" : value.toString();    
    }  
    
    public static JsonConfig getJsonConfig(){
        if(jsonConfig!=null)
            return jsonConfig;
        else{
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
            JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"}));
            return jsonConfig;
        }
    }

    /*
    JSONObject json = new JSONObject();
//Map转JSON  

    json.putAll (Map, jsonConfig);

    JSONObject json = new JSONObject();
//JavaBean转JSON  

    json.fromObject (object, jsonConfig)  */
     
}  
