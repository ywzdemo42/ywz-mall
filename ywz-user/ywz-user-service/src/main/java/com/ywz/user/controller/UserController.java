package com.ywz.user.controller;

import com.ywz.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/check/{data}/{type}")
    private ResponseEntity<Boolean> checkUser(@PathVariable("data")String data,@PathVariable("type")Integer type){

        Boolean bool = this.userService.checkUser(data,type);
        if(bool == null){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(bool);
    }
}
