package com.scsa.order.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 接收RabbitMQ消息
 * @Author: SCSA
 * @Date: 2020/8/23 21:00
 */
@Slf4j
@Component
public class MqReceiver {

    //1. @RabbitListener(queues = "myQueue")
    //2. 自动创建队列 @RabbitListener(queuesToDeclare = @Queue("myQueue"))
    //3. 自动创建，Exchange和Queue绑定
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("myQueue"),
            exchange = @Exchange("myExchange")
    ))
    public void receiveMessage(String message) {
        log.info("MqReceiver: {}", message);
    }

    /**
     * 电子产品供应商服务接收消息
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange("myOrder"),
            key = "computer",
            value = @Queue("computerOrder")
    ))
    public void processComputer(String message) {
        log.info("computer MqReceiver: {}", message);
    }

    /**
     * 水果供应商服务接收消息
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange("myOrder"),
            key = "fruit",
            value = @Queue("fruitOrder")
    ))
    public void processfruit(String message) {
        log.info("fruit MqReceiver: {}", message);
    }

}
