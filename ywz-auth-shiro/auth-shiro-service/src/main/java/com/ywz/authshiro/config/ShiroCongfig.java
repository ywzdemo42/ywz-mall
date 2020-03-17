package com.ywz.authshiro.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroCongfig {

    //realm 自定义
    @Autowired
    private UserRealm userRealm;


    //shiroFilterFactoruBean subject
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("getDefaultWebSecurityManager")DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);
        //添加shiro的内置过滤器
        /**
         * anno 无需认证就可以访问
         * authc 必须认证了才能访问
         * user 必须拥有 记住我功能才能访问
         * perms 拥有对某个资源的权限才能访问
         */
        //登入拦截
        Map<String, String> filterMap = new LinkedHashMap<>();

        //授权
        filterMap.put("/user/add","perms[user:add]");
        filterMap.put("/user/*","authc");
        bean.setFilterChainDefinitionMap(filterMap);
        //设置登入的请求
        bean.setLoginUrl("/tologin");
        return bean;
    }

    //securityManager
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //关联reaml
        defaultWebSecurityManager.setRealm(userRealm);
        return defaultWebSecurityManager;
    }


}
