package com.ywz.cart.interceptor;

import com.ywz.cart.config.JwtProperties;
import com.ywz.common.pojo.UserInfo;
import com.ywz.common.utils.CookieUtils;
import com.ywz.common.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Action;

//拦截器
@Component
@EnableConfigurationProperties(JwtProperties.class)
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private JwtProperties properties;

    private static final ThreadLocal<UserInfo> THREAD_LOCAL = new ThreadLocal<>();
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取cookie中的token
        String token = CookieUtils.getCookieValue(request, properties.getCookieName());
        //解析token获取用户信息
        UserInfo user = JwtUtils.getInfoFromToken(token, this.properties.getPublicKey());
        if(user == null){
            return false;
        }
        THREAD_LOCAL.set(user);
        return true;
    }

    public static UserInfo getUserInfo(){
        return THREAD_LOCAL.get();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //清空线程的局部变量
        THREAD_LOCAL.remove();
    }
}
