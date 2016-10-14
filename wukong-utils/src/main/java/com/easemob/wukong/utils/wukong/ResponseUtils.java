package com.easemob.wukong.utils.wukong;

import com.easemob.wukong.model.data.CommonResponse;
import com.easemob.wukong.utils.json.JSONUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NullNode;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by dongwentao on 16/9/30.
 */
@Slf4j
public class ResponseUtils {

    private static final int successCode = 0;
    private static final String successMessage = "SUCCESS";
    private static final int failCode = 1;
    private static final String failMessage = "FAIL";
    private static final JsonNode defaultData = NullNode.instance;

    public static CommonResponse build(int code, String message, String data){
        return build(code,message, JSONUtils.fromObject(data));
    }

    public static  CommonResponse build(int code, String message, Object data){
        return new CommonResponse(code,message,JSONUtils.fromObject(data));
    }

    public static  CommonResponse build(int code, String message, JsonNode data){
        return new CommonResponse(code,message,data);
    }

    // success
    public static  CommonResponse buildSuccess(){
        return buildSuccess(defaultData);
    }
    public static  CommonResponse buildSuccess(String data){
        return build(successCode,successMessage,data);
    }
    public static  CommonResponse buildSuccess(JsonNode data){
        return build(successCode,successMessage,data);
    }
    public static  CommonResponse buildSuccess(Object data){
        return build(successCode,successMessage,data);
    }

    public static  CommonResponse buildSuccessMessage(String message){
        return buildSuccessMessage(message,defaultData);
    }

    public static  CommonResponse buildSuccessMessage(String message,String data){
        return build(successCode,message,data);
    }
    public static  CommonResponse buildSuccessMessage(String message,JsonNode data){
        return build(successCode,message,data);
    }
    public static  CommonResponse buildSuccessMessage(String message,Object data){
        return build(successCode,message,data);
    }

    // fail
    public static  CommonResponse buildFail(){
        return buildSuccess(defaultData);
    }
    public static  CommonResponse buildFail(String data){
        return build(failCode,failMessage,data);
    }
    public static  CommonResponse buildFail(JsonNode data){
        return build(failCode,failMessage,data);
    }
    public static  CommonResponse buildFail(Object data){
        return build(failCode,failMessage,data);
    }

    public static  CommonResponse buildFailMessage(String message){
        return buildFailMessage(message,defaultData);
    }

    public static  CommonResponse buildFailMessage(String message,String data){
        return build(failCode,message,data);
    }
    public static  CommonResponse buildFailMessage(String message,JsonNode data){
        return build(failCode,message,data);
    }
    public static  CommonResponse buildFailMessage(String message,Object data){
        return build(failCode,message,data);
    }
}
