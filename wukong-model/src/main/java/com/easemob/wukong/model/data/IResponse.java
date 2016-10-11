package com.easemob.wukong.model.data;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Created by dongwentao on 16/9/27.
 */
public interface IResponse {
    int getCode();
    String getMessage();
    JsonNode getData();
}
