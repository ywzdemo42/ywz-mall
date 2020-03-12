package com.ywz.user.api;

import com.ywz.user.pojo.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserApi {

    @GetMapping("query")
    public User queryUser(
            @RequestParam("username")String username,
            @RequestParam("password")String password
    );
}
