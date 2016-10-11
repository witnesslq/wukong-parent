package com.easemob.wukong.utils.cookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by dongwentao on 16/9/26.
 */
public class CookieUtils {

    public static final String defaultPath = "/";
    public static final int defaultMaxAge = -1;

    public static String getCookie(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0)
            return null;
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals(key))
                return cookies[i].getValue();
        }
        return null;
    }

    public static void saveCookie(HttpServletRequest request, HttpServletResponse response, String key, String value) {
        saveCookie(request, response, key, value, defaultMaxAge);
    }

    public static void saveCookie(HttpServletRequest request, HttpServletResponse response, String key, String value, int maxAge) {
        saveCookie(request,response,key,value,maxAge,defaultPath);
    }

    public static void saveCookie(HttpServletRequest request,HttpServletResponse response, String key, String value, int maxAge, String path){

        String domain = request.getServerName();

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(maxAge);
        cookie.setPath(path);
        cookie.setDomain(domain);
        response.addCookie(cookie);
    }

    public static void clearCookie(HttpServletResponse response, String key) {
        clearCookie(response,key,defaultPath);
    }

    public static void clearCookie(HttpServletResponse response, String key, String path) {
        Cookie cookie = new Cookie(key, null);
        cookie.setPath(path);
        cookie.setMaxAge(defaultMaxAge);
        response.addCookie(cookie);
    }
}
