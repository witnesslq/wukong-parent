package com.easemob.wukong.utils.session;

import com.easemob.wukong.utils.json.JSONUtils;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by dongwentao on 16/9/26.
 */
public class SessionUtils {


    public static void setAttribute(String key,Object value){
        HttpSession session = getSession(false);
        if(null!=session) {
            System.out.println("SessionUtils|setAttribute:"+value.toString());
            session.setAttribute(key, value);
        }
    }

    public static void setAttribute(HttpSession httpSession,String key,Object value){
        if(null!=httpSession) {
            httpSession.setAttribute(key, value);
        }
    }

    public static void setAttribute(String key0,Object value0,String key1,Object value1){
        HttpSession session = getSession(false);
        if(null!=session) {
            session.setAttribute(key0, value0);
            session.setAttribute(key1, value1);
        }
    }

    public static void setAttribute(HttpSession httpSession,String key0,Object value0,String key1,Object value1){
        if(null!=httpSession) {
            httpSession.setAttribute(key0, value0);
            httpSession.setAttribute(key1, value1);
        }
    }

    public static void setAttribute(String key0,Object value0,String key1,Object value1,String key2,Object value2){
        HttpSession session = getSession(false);
        if(null!=session) {
            session.setAttribute(key0, value0);
            session.setAttribute(key1, value1);
            session.setAttribute(key2, value2);
        }
    }

    public static void setAttribute(HttpSession httpSession,String key0,Object value0,String key1,Object value1,String key2,Object value2){
        if(null!=httpSession) {
            httpSession.setAttribute(key0, value0);
            httpSession.setAttribute(key1, value1);
            httpSession.setAttribute(key2, value2);
        }
    }

    public static Object getAttribute(String key) {
        return getRequest().getAttribute(key);
    }

    public static JsonNode getJsonAttribute(String key) {
        return JSONUtils.fromObject(getRequest().getAttribute(key));
    }

    public static HttpSession getSession(boolean create){
        return getRequest().getSession(create);
    }

    public static void logout(){
        if(isLogin()){
            getSession(false).invalidate();
        }
    }

    public static boolean isLogin(){
        return null!=getSession(false);
    }

    private static HttpServletRequest getRequest(){
        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    }
}
