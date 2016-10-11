package com.easemob.wukong.services;

import com.easemob.wukong.utils.json.JSONUtils;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Base64;

/**
 * Created by dongwentao on 16/10/8.
 */
public class GeneralTest {
    public static void main(String [] args){
        ObjectNode jsonNodes = JSONUtils.objectNode();
        jsonNodes.put("sentence1","you are so beautiful!");
        jsonNodes.put("sentence2","you are a beautiful girl!");
        jsonNodes.put("type","1");
        String s = Base64.getEncoder().encodeToString(jsonNodes.toString().getBytes());
        System.out.println(s.length());

    }
}
