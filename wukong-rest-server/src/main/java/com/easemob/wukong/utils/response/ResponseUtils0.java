package com.easemob.wukong.utils.response;

import com.easemob.wukong.model.data.CommonResponse;
import com.easemob.wukong.model.data.IResponse;
import com.easemob.wukong.utils.json.JSONUtils;
import com.easemob.wukong.utils.wukong.ResponseUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by dongwentao on 16/10/7.
 */
public class ResponseUtils0 {

    private static final HttpStatus successHttpStatus = HttpStatus.OK;

    public static <T extends IResponse> ResponseEntity<T> buildResponse(T t){
        return new ResponseEntity<T>(t,successHttpStatus);
    }

    public static <T extends IResponse> ResponseEntity<T> buildResponse(T t,HttpStatus httpstatus){
        return new ResponseEntity<T>(t,httpstatus);
    }

    public static <T extends IResponse> ResponseEntity<T> buildResponse(T t, MultiValueMap<String, String> headers, HttpStatus httpstatus){
        return new ResponseEntity<T>(t,headers,httpstatus);
    }

    public static <T extends IResponse> ResponseEntity<T> buildResponse(T t, HttpStatus httpstatus,String key,String value){
        HttpHeaders headers = new HttpHeaders();
        headers.add(key,value);
        return new ResponseEntity<T>(t,headers,httpstatus);
    }

    public static <T extends IResponse> ResponseEntity<T> buildResponse(T t, HttpStatus httpstatus,String key0,String value0,String key1,String value1){
        HttpHeaders headers = new HttpHeaders();
        headers.add(key0,value0);
        headers.add(key1,value1);
        return new ResponseEntity<T>(t,headers,httpstatus);
    }

    public static void write(int code,String message,String data) throws IOException {
        CommonResponse responseMessage = ResponseUtils.build(code, message, data);
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        PrintWriter writer = response.getWriter();
        writer.write(JSONUtils.fromObject(responseMessage).toString());
    }
}
