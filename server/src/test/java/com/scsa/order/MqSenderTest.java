package com.scsa.order;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * 发送mq消息
 * @Author: SCSA
 * @Date: 2020/8/23 21:02
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MqSenderTest {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void send() {
        amqpTemplate.convertAndSend("myQueue", "now"+new Date()); //队列名字，发送内容
    }

    @Test
    public void sendOrder() {
        amqpTemplate.convertAndSend("myOrder","computer", "now"+new Date());
    }
}
