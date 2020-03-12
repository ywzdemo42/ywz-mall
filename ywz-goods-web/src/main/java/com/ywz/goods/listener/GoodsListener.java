package com.ywz.goods.listener;

import com.ywz.goods.service.GoodsHtmlService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GoodsListener {
    @Autowired
    private GoodsHtmlService goodsHtmlService;

    //方法名随便写
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "YWZ.ITEM.SAVE.QUEUE",durable = "true"),
            exchange = @Exchange(value = "YWZ.ITEM.EXCHANGE", ignoreDeclarationExceptions = "true", type = ExchangeTypes.TOPIC),
            key = {"item.insert", "item.update"}
    ))
    public void save(Long id){
        if (id == null){
            return;
        }

        this.goodsHtmlService.createHtml(id);
    }
}

//删除还没有完成