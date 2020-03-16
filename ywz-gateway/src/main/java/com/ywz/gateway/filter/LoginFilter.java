package com.ywz.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.ywz.common.utils.CookieUtils;
import com.ywz.common.utils.JwtUtils;
import com.ywz.gateway.config.FilterProperties;
import com.ywz.gateway.config.JwtProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
@EnableConfigurationProperties({JwtProperties.class, FilterProperties.class})
public class LoginFilter extends ZuulFilter {
    @Autowired
    private JwtProperties properties;
    @Autowired
    private FilterProperties filterProperties;

    @Override
    public String filterType() {
        //进入路由前拦截
        return "pre";
    }

    @Override
    public int filterOrder() {
        //拦截顺序 为了扩展性 所以是10
        return 10;
    }

    @Override
    public boolean shouldFilter() {
        //是否拦截
        //查看是否在白名单中
        List<String> allowPaths = this.filterProperties.getAllowPaths();
        //初始化zuul网关运行上下文
        RequestContext context = RequestContext.getCurrentContext();
        //获取request对象
        HttpServletRequest request = context.getRequest();
        //获取请求的路径
        String url = request.getRequestURL().toString();

        for (String allowPath : allowPaths) {
            System.err.println("当前请求路径为：" + url + ",对比路口为：" + allowPath);
            if (StringUtils.contains(url, allowPath)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //初始化zuul网关运行上下文
        RequestContext context = RequestContext.getCurrentContext();
        //获取request对象
        HttpServletRequest request = context.getRequest();
        String token = CookieUtils.getCookieValue(request, this.properties.getCookieName());
        //
        if(StringUtils.isBlank(token)){
            //不转发请求
            context.setSendZuulResponse(false);
            //返回错误代码
            context.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        }

        try {
            JwtUtils.getInfoFromToken(token,this.properties.getPublicKey());
        } catch (Exception e) {
            e.printStackTrace();
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        }



        return null;
    }
}
