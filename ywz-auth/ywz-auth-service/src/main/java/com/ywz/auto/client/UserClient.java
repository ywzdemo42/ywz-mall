package com.ywz.auto.client;

import com.ywz.user.api.UserApi;
import com.ywz.user.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;

@FeignClient("user-service")
public interface UserClient extends UserApi {

}
