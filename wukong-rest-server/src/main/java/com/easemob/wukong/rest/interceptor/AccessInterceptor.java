package com.easemob.wukong.rest.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by dongwentao on 16/10/6.
 */
@Service
@Slf4j
public class AccessInterceptor extends AbstractInterceptor {

    @Override
    protected PriorityType getPriorityType() {
        return PriorityType.access;
    }

    @Override
    protected boolean before(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("request:{},{},{},{},{}",request.getMethod(),request.getRequestURL(),request.getQueryString(),request.getRemoteAddr(),request.getRemotePort());
        return true;
    }

    @Override
    protected void after(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("responseStatus:{}|modelAndView:{}",response.getStatus(),modelAndView);
    }

    @Override
    protected void finish(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("responseStatus:{}|ex:{}",response.getStatus(),ex);
    }

}
