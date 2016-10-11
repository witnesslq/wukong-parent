package com.easemob.wukong.model.data;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

/**
 * Created by dongwentao on 16/9/27.
 */
@Data
public class ResponseAdapter implements IResponse {
    protected int code;
    protected String message;
    protected JsonNode data;
}
