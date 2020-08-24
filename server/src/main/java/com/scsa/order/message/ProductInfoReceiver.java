package com.scsa.order.message;

import com.fasterxml.jackson.core.type.TypeReference;
import com.scsa.order.util.JsonUtil;
import com.scsa.product.common.ProductInfoOutPut;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 接收商品服务发送的mq消息
 * @Author: SCSA
 * @Date: 2020/8/24 17:51
 */
@Component
@Slf4j
public class ProductInfoReceiver {

    private static final String PRODUCT_SOTCK_TEMPLATE = "product_stock_%s";

    @Autowired
    private RedisTemplate redisTemplate;

    @RabbitListener(queuesToDeclare = @Queue("productInfo"))
    public void process(String message) { //入参是json字符串
        //message 转成 ProductInfoOutPut
        List<ProductInfoOutPut> productInfoOutPutList = (List<ProductInfoOutPut>) JsonUtil.fromJson(message,
                                        new TypeReference<List<ProductInfoOutPut>>() {});
        log.info("从队列{}接收到消息：{}", "productInfo", productInfoOutPutList);

        //接收到消息存到Redis中
        for (ProductInfoOutPut productInfoOutPut : productInfoOutPutList) {
            redisTemplate.opsForValue().set(String.format(PRODUCT_SOTCK_TEMPLATE, productInfoOutPut.getProductId()),
                    String.valueOf(productInfoOutPut.getProductStock()));
        }
    }

}
