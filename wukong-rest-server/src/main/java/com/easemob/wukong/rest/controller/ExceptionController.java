package com.easemob.wukong.rest.controller;

import com.easemob.wukong.model.data.IResponse;
import com.easemob.wukong.utils.response.ResponseUtils0;
import com.easemob.wukong.utils.wukong.ResponseUtils;
import com.wukong.support.exception.StatusException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by dongwentao on 16/10/7.
 */
@ControllerAdvice
@ResponseBody
public class ExceptionController {

    @ExceptionHandler(value=Exception.class)
    public ResponseEntity<IResponse> handler(HttpServletRequest request, HttpServletResponse response,Exception e){
        return ResponseUtils0.buildResponse(ResponseUtils.buildFailMessage(e.getMessage()));
    }

    @ExceptionHandler(value=StatusException.class)
    public ResponseEntity<IResponse> handler(HttpServletRequest request, HttpServletResponse response,StatusException e){
        return ResponseUtils0.buildResponse(ResponseUtils.buildFailMessage(e.getMessage()));
    }

}
