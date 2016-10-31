package com.easemob.wukong.rest.controller.user;

import com.easemob.wukong.model.annotation.Logined;
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
import com.easemob.wukong.utils.session.SessionUtils;
import com.easemob.wukong.utils.wukong.ResponseUtils;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/user")
@NotLogin
@Slf4j
public class UserController extends WuKongController {

    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(value = "login", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<IResponse> login(HttpServletRequest request, HttpServletResponse response, LoginRegRequest loginRegRequest) {
        log.info("UserController|login|LoginRegRequest:{}",loginRegRequest);
        CommonResponse commonResponse = null;
        if (doLogin(request,response,loginRegRequest)) {
            commonResponse = ResponseUtils.buildSuccessMessage("Login success.");
        } else {
            commonResponse = ResponseUtils.buildFailMessage("Login failed, please retry later.");
        }
        log.info("UserController|login|CommonResponse:{}",commonResponse);
        return ResponseUtils0.buildResponse(commonResponse);
    }

    @Logined
    @RequestMapping(value = "logout", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<IResponse> logout(HttpServletRequest request, HttpServletResponse response) {
        log.info("UserController|logout|User:{}",SessionUtils.getJsonAttribute(SESSION_USER));
        CommonResponse commonResponse = null;
        if (SessionUtils.isLogin()) {
            SessionUtils.logout();
            commonResponse = ResponseUtils.buildSuccessMessage("Logout success.");
        } else {
            commonResponse = ResponseUtils.buildFailMessage("Logout failed, not login.");
        }
        log.info("UserController|login|CommonResponse:{}",commonResponse);
        return ResponseUtils0.buildResponse(commonResponse);
    }

    @RequestMapping(value = "regist", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<IResponse> regist(HttpServletRequest request, HttpServletResponse response, LoginRegRequest loginRegRequest) {
        log.info("UserController|regist|LoginRegRequest:{}",loginRegRequest);
        User user = authenticationService.regist(loginRegRequest);
        CommonResponse commonResponse = null;
        if (null!=user&&doLogin(request,response,loginRegRequest)) {
            commonResponse = ResponseUtils.buildSuccessMessage("Regist success.");
        } else {
            commonResponse = ResponseUtils.buildFailMessage("Regist failed, please retry later.");
        }
        log.info("UserController|regist|CommonResponse:{}",commonResponse);
        return ResponseUtils0.buildResponse(commonResponse);
    }

    private boolean doLogin(HttpServletRequest request,HttpServletResponse response,LoginRegRequest loginRegRequest){
        User user = authenticationService.login(loginRegRequest);
        if(null!=user) {
            HttpSession session = request.getSession(true);
            ObjectNode _user = JSONUtils.objectNode("id", user.getId() + "", "name", user.getName(), "role", user.getRole() + "");
            SessionUtils.setAttribute(SESSION_USER, _user,SESSION_MAX_TIME);
            CookieUtils.saveCookie(request, response, COOKIE_USER, _user, COOKIE_MAX_TIME);
            return true;
        }
        return false;
    }
}
