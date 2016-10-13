package com.easemob.wukong.rest.controller.user;

import com.easemob.wukong.model.annotation.NotLogin;
import com.easemob.wukong.model.data.CommonResponse;
import com.easemob.wukong.model.data.IResponse;
import com.easemob.wukong.model.data.user.LoginRegRequest;
import com.easemob.wukong.model.entity.user.User;
import com.easemob.wukong.rest.controller.WuKongController;
import com.easemob.wukong.services.user.AuthenticationService;
import com.easemob.wukong.utils.cookie.CookieUtils;
import com.easemob.wukong.utils.json.JSONUtils;
import com.easemob.wukong.utils.response.ResponseUtils0;
import com.easemob.wukong.utils.wukong.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by dongwentao on 16/9/27.
 */
@RestController
@RequestMapping("user")
@NotLogin
public class UserController extends WuKongController {

    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(value = "login",method = {RequestMethod.POST,RequestMethod.GET})
    public ResponseEntity<IResponse> login(HttpServletRequest request, HttpServletResponse response, LoginRegRequest loginRegRequest){
        User user  = authenticationService.login(loginRegRequest);
        CommonResponse commonResponse = null;
        if(null!=user) {
            HttpSession session = request.getSession(true);
            session.setAttribute("userId",user.getId());
            session.setAttribute("userName",user.getName());
            session.setAttribute("userRole",user.getRole());
            JSONUtils

            CookieUtils.saveCookie(request,response, COOKIE_USER,+"", COOKIE_MAX_TIME);
            CookieUtils.saveCookie(request,response, COOKIE_USER,user.getId()+"", COOKIE_MAX_TIME);
            CookieUtils.saveCookie(request,response, COOKIE_USER,user.getId()+"", COOKIE_MAX_TIME);
            commonResponse = ResponseUtils.buildSuccessMessage("Login success.");
        }else {
            commonResponse = ResponseUtils.buildFailMessage("Login failed, please retry later.");
        }
        return ResponseUtils0.buildResponse(commonResponse);
    }

    @RequestMapping(value = "regist",method = {RequestMethod.POST,RequestMethod.GET})
    public ResponseEntity<IResponse>  regist(HttpServletRequest request,HttpServletResponse response, LoginRegRequest loginRegRequest){
        User user = authenticationService.regist(loginRegRequest);
        CommonResponse commonResponse = null;
        if(null!=user) {
            HttpSession session = request.getSession(true);
            CookieUtils.saveCookie(request,response, COOKIE_USER,user.getId()+"", COOKIE_MAX_TIME);
            commonResponse = ResponseUtils.buildSuccessMessage("Regist success.");
        }else {
            commonResponse = ResponseUtils.buildFailMessage("Regist failed, please retry later.");
        }
        return ResponseUtils0.buildResponse(commonResponse);
    }
}
