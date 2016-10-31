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

    public static final int defaultMaxAge = -1;

    /**
     * 设置一个session属性
     * @param key
     * @param value
     */
    public static void setAttribute(String key,Object value){
        HttpSession session = getSession(false);
        if(null!=session) {
            session.setAttribute(key, value);
        }
    }

    /**
     * 设置存活时间
     * @param maxAge
     */
    public static void setAttribute(int maxAge){
        HttpSession session = getSession(false);
        if(null!=session) {
            session.setMaxInactiveInterval(maxAge);
        }
    }

    /**
     * 设置一个属性,同时设置存活时间
     * @param key
     * @param value
     * @param maxAge
     */
    public static void setAttribute(String key,Object value,int maxAge){
        HttpSession session = getSession(false);
        if(null!=session) {
            session.setAttribute(key, value);
            session.setMaxInactiveInterval(maxAge);
        }
    }

    /**
     * 设置属性
     * @param httpSession
     * @param key
     * @param value
     */
    public static void setAttribute(HttpSession httpSession,String key,Object value){
        if(null!=httpSession) {
            httpSession.setAttribute(key, value);
        }
    }

    /**
     * 设置属性
     * @param key0
     * @param value0
     * @param key1
     * @param value1
     */
    public static void setAttribute(String key0,Object value0,String key1,Object value1){
        HttpSession session = getSession(false);
        if(null!=session) {
            session.setAttribute(key0, value0);
            session.setAttribute(key1, value1);
        }
    }

    /**
     * 设置属性
     * @param httpSession
     * @param key0
     * @param value0
     * @param key1
     * @param value1
     */
    public static void setAttribute(HttpSession httpSession,String key0,Object value0,String key1,Object value1){
        if(null!=httpSession) {
            httpSession.setAttribute(key0, value0);
            httpSession.setAttribute(key1, value1);
        }
    }

    /**
     * 设置属性
     * @param key0
     * @param value0
     * @param key1
     * @param value1
     * @param key2
     * @param value2
     */
    public static void setAttribute(String key0,Object value0,String key1,Object value1,String key2,Object value2){
        HttpSession session = getSession(false);
        if(null!=session) {
            session.setAttribute(key0, value0);
            session.setAttribute(key1, value1);
            session.setAttribute(key2, value2);
        }
    }

    /**
     * 设置属性
     * @param httpSession
     * @param key0
     * @param value0
     * @param key1
     * @param value1
     * @param key2
     * @param value2
     */
    public static void setAttribute(HttpSession httpSession,String key0,Object value0,String key1,Object value1,String key2,Object value2){
        if(null!=httpSession) {
            httpSession.setAttribute(key0, value0);
            httpSession.setAttribute(key1, value1);
            httpSession.setAttribute(key2, value2);
        }
    }

    /**
     * 获取属性
     * @param key
     * @return Object
     */
    public static Object getAttribute(String key) {
        return getRequest().getAttribute(key);
    }

    /**
     * 获取属性
     * @param key
     * @return JsonNode
     */
    public static JsonNode getJsonAttribute(String key) {
        return JSONUtils.fromObject(getRequest().getAttribute(key));
    }

    /**
     * 获取session对象
     * @param create
     * @return HttpSession
     */
    public static HttpSession getSession(boolean create){
        return getRequest().getSession(create);
    }

    /**
     * 登出,销毁session
     */
    public static void logout(){
        if(isLogin()){
            getSession(false).invalidate();
        }
    }

    /**
     * 验证是否登陆
     * @return
     */
    public static boolean isLogin(){
        return null!=getSession(false);
    }

    /**
     * 获取request对象
     * @return
     */
    private static HttpServletRequest getRequest(){
        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    }
}
