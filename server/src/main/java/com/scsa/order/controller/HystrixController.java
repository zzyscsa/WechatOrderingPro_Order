package com.scsa.order.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * @Author: SCSA
 * @Date: 2020/8/26 15:01
 */
@RestController
@DefaultProperties(defaultFallback = "defaultFallBack")
public class HystrixController {

//    服务降级
//    @HystrixCommand(fallbackMethod = "fallback")
//    @HystrixCommand //使用默认fallback
//  //设置超时时间，超过2s触发降级，默认1s
//    @HystrixCommand(commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
//    })

    //服务熔断
    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"), //设置熔断
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"), //设置在时间窗口中，断路器最小请求数，至少请求10次才触发熔断
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"), //时间窗口，断路器打开对主逻辑启动熔断，Hystrix会启动一个休眠时间窗，这个时间窗内降级逻辑临时成为主逻辑，当休眠时间窗到期（10s）,断路器进入半熔断状态，如果失败，重新计时
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"),//断路器打开的错误百分比条件
    })
    @GetMapping("/getProductInfoList")
    public String getProductInfoList() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject("http://127.0.0.1:8082/product/listForOrder",
                Arrays.asList("123460"),
                String.class);

//        // 抛出了异常就会触发降级
//        throw new RuntimeException("发生异常");
    }

    private String fallback() {
        return "网络被挤爆了，请稍后再试";
    }

    private String defaultFallBack() {
        return "默认提示，服务拥挤";
    }

}
