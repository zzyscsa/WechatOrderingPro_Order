package com.scsa.order.message;

import com.scsa.order.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * @Author: SCSA
 * @Date: 2020/8/23 22:27
 */
@Component
@Slf4j
@EnableBinding(StreamClient.class)
public class StreamReceiver {

//    @StreamListener(StreamClient.INPUT)
//    public void receiveMessage(Object message) {
//        log.info("StreamReceiver: {}", message);
//    }

    /** 接收对象 */
    @StreamListener(StreamClient.INPUT)
    @SendTo(StreamClient.INPUT2) //处理完后发送消息
    public String receiveMessage(OrderDTO message) {
        log.info("StreamReceiver: {}", message);
        //处理后发送mq消息回应
        return "receive.OK!";
    }

    /** 接收回应的消息 */
    @StreamListener(StreamClient.INPUT2)
    public void receiveMessage2(String message) {
        log.info("StreamReceiver2: {}", message);
    }
}
