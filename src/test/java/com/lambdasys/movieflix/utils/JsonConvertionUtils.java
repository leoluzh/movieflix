package com.lambdasys.movieflix.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonConvertionUtils {

    public static String asJsonString( Object object ){
        try{
            ObjectMapper om = new ObjectMapper();
            om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
            om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
            om.registerModule(new JavaTimeModule());
            return om.writeValueAsString( object );
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }

    }

}
