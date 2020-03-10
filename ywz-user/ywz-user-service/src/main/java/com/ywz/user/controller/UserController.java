package com.ywz.user.controller;

import com.ywz.user.pojo.User;
import com.ywz.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 检查用户是否可以注册
     * @param data
     * @param type
     * @return
     */
    @GetMapping("/check/{data}/{type}")
    public ResponseEntity<Boolean> checkUser(@PathVariable("data")String data,@PathVariable("type")Integer type){

        Boolean bool = this.userService.checkUser(data,type);
        if(bool == null){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(bool);
    }

    /**
     * 短信发送验证码模块
     * @param phone 电话号码
     * @return
     */
    @PostMapping("code")
    public ResponseEntity<Void> sendVerifyCode(@RequestParam("phone")String phone){
        this.userService.sendVerifyCode(phone);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 用户注册
     * @param user 用户类
     * @param code 验证码
     * @return
     */
    @PostMapping("register")
    public ResponseEntity<Void> userRegister(@Valid User user, @RequestParam("code") String code){
        Boolean isRegister = this.userService.userRegister(user,code);
        if (isRegister == null || !isRegister){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("query")
    public ResponseEntity<User> queryUser(
            @RequestParam("username")String username,
            @RequestParam("password")String password
    ){
        User user = this.userService.queryUser(username,password);
        if(user == null){
            return null;
        }

        return ResponseEntity.ok(user);
    }
}
