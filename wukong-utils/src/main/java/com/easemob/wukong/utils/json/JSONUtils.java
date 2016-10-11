package com.easemob.wukong.utils.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;

/**
 * Created by dongwentao on 16/9/27.
 */
public class JSONUtils {

    private static ObjectMapper objectMapper= new ObjectMapper();

    static {
        objectMapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING,true);
    }

    public static ObjectNode objectNode(){
        return objectMapper.createObjectNode();
    }
    public static ArrayNode arrayNode(){
        return objectMapper.createArrayNode();
    }

    public static JsonNode fromObject(Object object){
        try {
            return fromObject(objectMapper.writeValueAsString(object));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return NullNode.instance;
    }

    public static JsonNode fromObject(String elements){
        try {
            return objectMapper.readTree(elements);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return NullNode.instance;
    }
}
