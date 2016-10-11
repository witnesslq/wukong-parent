package com.easemob.wukong.rest.interceptor;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by dongwentao on 16/9/26.
 */
public abstract class AbstractInterceptor extends HandlerInterceptorAdapter implements Comparable<AbstractInterceptor>{

    protected static enum PriorityType{

        access, // AccessInterceptor 记录访问
        login, // UserInterceptor 保证用户登陆了

        ;
    }

    private final int[] prioprities = new int[PriorityType.values().length];

    @PostConstruct
    void initPriorities() {

        PriorityType[] priorityTypes = PriorityType.values();
        int priorityBeforeLogin = 100000000;// Login之前的拦截器的优先级
        int priorityAfterLogin = -100000000;// Login之后的拦截器的优先级

        int loginPos = PriorityType.login.ordinal();
        int pos = 0;
        int length = priorityTypes.length;
        for (; pos < loginPos; ++pos) {
            prioprities[pos] = priorityBeforeLogin + loginPos - 1 - pos;
        }
        for (; pos < length; ++pos) {
            prioprities[pos] = priorityAfterLogin + loginPos + 1 - pos;
        }

    }

    public final int getPriority() {
        return prioprities[getPriorityType().ordinal()];
    }

    protected abstract PriorityType getPriorityType();

    protected boolean isForAction(Class<?> clazz, Method method){
        return true;
    }

    protected boolean before(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }
    protected void after(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
            return;
    }
    protected void finish(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        return;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!isForAction(((HandlerMethod)handler).getBeanType(),((HandlerMethod)handler).getMethod())){
            return true;
        }
        return before(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if(!isForAction(((HandlerMethod)handler).getBeanType(),((HandlerMethod)handler).getMethod())){
            return;
        }
        after(request, response, handler,modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if(!isForAction(((HandlerMethod)handler).getBeanType(),((HandlerMethod)handler).getMethod())){
            return;
        }
        finish(request, response, handler,ex);
    }

    @Override
    public int hashCode() {
        return getPriority();
    }

    @Override
    public boolean equals(Object obj) {
        if(!AbstractInterceptor.class.isInstance(obj)){
            return false;
        }
        if(this.getPriority() == ((AbstractInterceptor)obj).getPriority()){
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(AbstractInterceptor abstractInterceptor) {
        if(this.getPriority()>abstractInterceptor.getPriority()){
            return -1;
        }
        return this.getPriority()<abstractInterceptor.getPriority()?1:0;
    }

    protected boolean containsAnnotations(Annotation[] annotations,Class<?> ...clazzs){
        for (Annotation annotation:annotations){
            if (ArrayUtils.contains(clazzs,annotation.annotationType())){
                return true;
            }
        }
        return false;
    }
}
