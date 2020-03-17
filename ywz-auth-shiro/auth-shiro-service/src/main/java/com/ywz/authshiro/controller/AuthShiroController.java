package com.ywz.authshiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthShiroController {

    @RequestMapping("/index")
    public String toIndex(Model model){
        model.addAttribute("msg","hello");
        return "index";
    }

    @RequestMapping("/user/add")
    public String toAdd(Model model){
        model.addAttribute("msg","add");
        return "add";
    }

    @RequestMapping("/user/update")
    public String toUpdate(Model model){
        model.addAttribute("msg","update");
        return "update";
    }

    @RequestMapping("/tologin")
    public String toLogin(Model model){
        return "login";
    }

    @RequestMapping
    public String login(String username,String password,Model model){
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        //封装用户的登入数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);//执行登录方法，如果没有异常说明ok
            return "index";
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            model.addAttribute("msg","用户名不存在");
            return "login";
        }catch (IncorrectCredentialsException e){
            model.addAttribute("msg","密码错误");
            return "login";
        }
    }
}
