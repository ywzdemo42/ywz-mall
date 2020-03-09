package com.ywz.user.service;

import com.ywz.common.utils.NumberUtils;
import com.ywz.user.mapper.UserMapper;
import com.ywz.user.pojo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private StringRedisTemplate redisTemplate;
    private static final String KEY_PREFIX = "user:verify:";

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

    //发送验证码
    public void sendVerifyCode(String phone) {
        if(StringUtils.isBlank(phone)){
            return;
        }
        //生成验证码
        String code = NumberUtils.generateCode(6);
        System.err.println(code);
        //发送信息到MQ
        HashMap<String, String> msg = new HashMap<>();
        msg.put("phone", phone);
        msg.put("code", code);
        this.amqpTemplate.convertAndSend("YWZ.SMS.EXCHANGE","verifycide.sms",msg);
        //把验证码保存到redis中
        this.redisTemplate.opsForValue().set(KEY_PREFIX + phone,code,5, TimeUnit.MILLISECONDS);
    }
}
