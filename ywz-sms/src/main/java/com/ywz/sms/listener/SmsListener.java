package com.ywz.sms.listener;

import com.ywz.sms.config.SmsProperties;
import com.ywz.sms.utils.SmsUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;

@Component
public class SmsListener {
    @Autowired
    private SmsUtils smsUtils;
    @Autowired
    private SmsProperties smsProperties;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "ywz.sms.queue", durable = "true"),
            exchange = @Exchange(value = "YWZ.SMS.EXCHANGE",ignoreDeclarationExceptions = "true",type = ExchangeTypes.TOPIC),
            key = {"verifycide.sms"}
    ))
    public void  sendSms(Map<String,String> msg){
        if(CollectionUtils.isEmpty(msg)){
            return;
        }

        String phone = msg.get("PHONE");
        String code = msg.get("CODE");
        if(StringUtils.isNoneBlank(phone) || StringUtils.isNoneBlank(code)){
            this.smsUtils.SendSms(phone,code,smsProperties.getSignName(),smsProperties.getVerifyCodeTemplate());
        }
    }
}
