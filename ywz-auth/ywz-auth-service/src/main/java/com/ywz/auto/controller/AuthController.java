package com.ywz.auto.controller;

import com.ywz.auto.config.JwtProperties;
import com.ywz.auto.service.AuthService;
import com.ywz.common.pojo.UserInfo;
import com.ywz.common.utils.CookieUtils;
import com.ywz.common.utils.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@EnableConfigurationProperties(JwtProperties.class)
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private JwtProperties properties;

    @PostMapping("accredit")
    public ResponseEntity<Void> accredit(
            @RequestParam("username")String username,
            @RequestParam("password")String password,
            HttpServletResponse response,
            HttpServletRequest request
    ){
        //创建token
        String token = this.authService.accredit(username,password);
        if (StringUtils.isBlank(token)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        //把token放入cookie中
        CookieUtils.setCookie(request,response,properties.getCookieName(),token,this.properties.getExpire()*60);
        return ResponseEntity.ok().build();
    }

    /**
     * 接受token信息返回页面用户名
     * CookieValue -> 接收cookie的值参数为cookie名称
     * @param token
     * @return
     */
    @GetMapping("verify")
    public ResponseEntity<UserInfo> verify(
            @CookieValue("YWZ_TOKEN")String token,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        UserInfo user = null;
        try {
            //使用公钥解析jwt
            user = JwtUtils.getInfoFromToken(token, this.properties.getPublicKey());
            if(user == null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            //刷新jwt中的有效时间
            token = JwtUtils.generateToken(user, this.properties.getPrivateKey(), this.properties.getExpire());
            //刷新cookie中的有效时间
            CookieUtils.setCookie(request,response,this.properties.getCookieName(),token,this.properties.getExpire()*60);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
