package com.easemob.wukong.rest.interceptor;

import com.easemob.wukong.model.annotation.IgnoreLogin;
import com.easemob.wukong.model.annotation.Logined;
import com.easemob.wukong.model.annotation.NotLogin;
import com.easemob.wukong.services.user.AuthenticationService;
import com.wukong.support.exception.StatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

/**
 * Created by dongwentao on 16/10/6.
 */
@Service
public class LoginInterceptor extends AbstractInterceptor {

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    protected PriorityType getPriorityType() {
        return PriorityType.login;
    }

    @Override
    protected boolean isForAction(Class<?> clazz, Method method) {
        if (containsAnnotations(method.getAnnotations(), NotLogin.class) || containsAnnotations(method.getAnnotations(), IgnoreLogin.class)) {
            return false;
        }
        if (!containsAnnotations(method.getAnnotations(), Logined.class) && (containsAnnotations(clazz.getAnnotations(), NotLogin.class) || containsAnnotations(clazz.getAnnotations(), IgnoreLogin.class))) {
            return false;
        }
        return true;
    }

    // TODO   验证登陆
    @Override
    protected boolean before(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean islogin = authenticationService.islogin(request);
        if (!islogin) {
            throw new StatusException("Oops, Login required!");
        }
        return true;
    }

    @Override
    protected void after(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("login after access");
    }
}
