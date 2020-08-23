package com.scsa.order.controller;

import com.scsa.order.dto.OrderDTO;
import com.scsa.order.message.StreamClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Author: SCSA
 * @Date: 2020/8/23 22:29
 */
@RestController
public class SendMessageController {

    @Autowired
    private StreamClient streamClient;

//    @GetMapping("/sendMessage")
//    public void process() {
//        String message = "now"+new Date();
//        streamClient.output().send(MessageBuilder.withPayload(message).build());
//    }

    /** 发送对象 */
    @GetMapping("/sendMessage")
    public void process() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId("555555555555");
        streamClient.output().send(MessageBuilder.withPayload(orderDTO).build());
    }
}
