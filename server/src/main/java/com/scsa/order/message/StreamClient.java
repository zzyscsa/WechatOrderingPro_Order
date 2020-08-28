package com.scsa.order.message;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @Author: SCSA
 * @Date: 2020/8/23 22:26
 */
public interface StreamClient {

    String INPUT = "myMessage";

    String INPUT2 = "myMessage2";

    @Input(StreamClient.INPUT)
    SubscribableChannel input();

    @Output(StreamClient.INPUT2)
    MessageChannel output();

//    @Input(StreamClient.INPUT2)
//    SubscribableChannel input2();
//
//    @Output(StreamClient.INPUT2)
//    MessageChannel output2();
}
