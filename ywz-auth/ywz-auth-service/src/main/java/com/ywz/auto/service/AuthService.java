package com.ywz.auto.service;

import com.ywz.auto.client.UserClient;
import com.ywz.auto.config.JwtProperties;
import com.ywz.common.pojo.UserInfo;
import com.ywz.common.utils.JwtUtils;
import com.ywz.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserClient userClient;
    @Autowired
    private JwtProperties properties;

    public String accredit(String username, String password) {
        //根据用户名和密码查询
        User user = this.userClient.queryUser(username, password);
        //判断USER
        if (user == null){
            return null;
        }
        try {
            //jwtUtil生成jwt类型的tocken
            UserInfo userInfo = new UserInfo();
            userInfo.setId(user.getId());
            userInfo.setUsername(user.getUsername());
            return JwtUtils.generateToken(userInfo,this.properties.getPrivateKey(),this.properties.getExpire());
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
