package com.ywz.user.service;

import com.ywz.user.mapper.UserMapper;
import com.ywz.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 校验数据是否可用
     * @param data
     * @param type 姓名/电话
     * @return
     */
    public Boolean checkUser(String data, Integer type) {
        User t = new User();
        if (type == 1) {
            t.setUsername(data);
        } else if(type == 2){
            t.setPhone(data);
        }else {
            return null;
        }

        return this.userMapper.selectCount(t) == 0;
    }
}
