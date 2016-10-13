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
    public static ObjectNode objectNode(String key, String value){
        return objectMapper.createObjectNode().put(key,value);
    }
    public static ObjectNode objectNode(String key0, String value0,String key1, String value1){
        return objectMapper.createObjectNode().put(key0,value0).put(key1,value1);
    }
    public static ObjectNode objectNode(String key0, String value0,String key1, String value1,String key2, String value2){
        return objectMapper.createObjectNode().put(key0,value0).put(key1,value1).put(key2,value2);
    }
    public static ObjectNode objectNode(String key, int value){
        return objectMapper.createObjectNode().putPOJO(key,value);
    }
    public static ObjectNode objectNode(String key0, int value0,String key1, int value1){
        return objectMapper.createObjectNode().put(key0,value0).put(key1,value1);
    }
    public static ObjectNode objectNode(String key0, int value0,String key1, int value1,String key2, int value2){
        return objectMapper.createObjectNode().put(key0,value0).put(key1,value1).put(key2,value2);
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
