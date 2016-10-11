package com.easemob.wukong.model.data;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Created by dongwentao on 16/9/27.
 */
public class CommonResponse extends ResponseAdapter {

    public CommonResponse(){}
    public CommonResponse(int code, String message, JsonNode data){
        this.code=code;
        this.message=message;
        this.data=data;
    }
}
