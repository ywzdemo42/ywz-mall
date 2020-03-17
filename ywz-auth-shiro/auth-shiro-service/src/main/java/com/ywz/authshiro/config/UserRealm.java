package com.ywz.authshiro.config;

import com.ywz.authshiro.client.UserClient;
import com.ywz.user.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserClient userClient;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.err.println("执行了：{}" + "授权");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //info.addStringPermission("user:add");
        //拿去当前用户
        Subject subject = SecurityUtils.getSubject();
        //此时可以取出subject里的user
        User currentUser = (User)subject.getPrincipal();
        //我觉得是一个map的集合  遍历附加权限
        info.addStringPermission(currentUser.getPerms());
        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.err.println("执行了：{}" + "认证");
        String name = "root";
        String password = "root";

        //用户名 密码
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        User user = new User();
        user.setUsername(userToken.getUsername());
        user.setPassword(userToken.getPassword().toString());
        //User user = this.userClient.queryUser(userToken.getUsername(), userToken.getPassword().toString());
        if(!user.getUsername().equals(name)){
            return null;
        }

        //密码认证，shiro做md5加盐加密
        //第一个参数就是把user和subject绑定
        return new SimpleAuthenticationInfo(user,password,"");
    }

}
