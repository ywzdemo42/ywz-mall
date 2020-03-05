package com.ywz.search.listener;

import com.ywz.search.service.SearchService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GoodsListener {
    @Autowired
    private SearchService searchService;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "YWZ.SEARCH.SAVE.QUEUE", durable = "true"),
            exchange = @Exchange(value = "YWZ.ITEM.EXCHANGE", type = ExchangeTypes.TOPIC ,ignoreDeclarationExceptions = "true"),
            key = {"item.insert","item.update"}
    ))
    public void  save(Long id) throws IOException {
        if (id == null){
            return;
        }
        //spring会根据你是否抛出异常来判断是否ack
        this.searchService.save(id);
    }
}
