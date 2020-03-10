package com.ywz.user.service;

import com.ywz.common.utils.NumberUtils;
import com.ywz.user.mapper.UserMapper;
import com.ywz.user.pojo.User;
import com.ywz.user.utils.CodecUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
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
        //this.amqpTemplate.convertAndSend("YWZ.SMS.EXCHANGE","verifycide.sms",msg);
        //把验证码保存到redis中
        this.redisTemplate.opsForValue().set(KEY_PREFIX + phone,code,5, TimeUnit.MINUTES);
        //this.redisTemplate.opsForValue().set("key2", "value2",5, TimeUnit.HOURS);
    }

    /**
     * 用户注册
     * @param user 用户信息
     * @param code 验证码
     * @return
     */
    public Boolean userRegister(User user, String code) {
        //查询redis中的验证码
        String redisCode = this.redisTemplate.opsForValue().get(KEY_PREFIX + user.getPhone());
        // 1校验验证码
        if(!StringUtils.equals(code,redisCode)){
            return false;
        }
        //2生成盐
        String salt = CodecUtils.generateSalt();
        user.setSalt(salt);
        //3加盐加密
        user.setPassword(CodecUtils.md5Hex(user.getPassword(),salt));
        //4新增用户
        user.setId(null);
        user.setCreated(new Date());
        Boolean isRegister = this.userMapper.insertSelective(user) == 1;
        //删除验证码
        if (isRegister){
            this.redisTemplate.delete(KEY_PREFIX + user.getPhone());
        }
        return isRegister;
    }

    /**
     * 查找用户
     * @param username
     * @param password
     * @return
     */
    public User queryUser(String username, String password) {
        User record = new User();
        record.setUsername(username);
        User user = this.userMapper.selectOne(record);
        //判断User是否为空
        if(user == null){
            return null;
        }
        //获取密码加盐加密
        password = CodecUtils.md5Hex(password, user.getSalt());
        //比较数据库密码
        if(StringUtils.equals(password,user.getPassword())){
            return user;
        }

        return null;

    }
}
