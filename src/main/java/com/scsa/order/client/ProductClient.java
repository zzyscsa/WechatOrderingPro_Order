package com.scsa.order.client;

import com.scsa.order.dataobject.ProductInfo;
import com.scsa.order.dto.CartDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Author: SCSA
 * @Date: 2020/8/21 22:07
 */
@FeignClient(name = "product") //表示调用的是哪个应用
public interface ProductClient {

    /** 声明调用应用的哪些方法 */
    @GetMapping("/msg")
    String productMsg();

    @PostMapping("/product/listForOrder") //路径填写完整
    List<ProductInfo> listForOrder(List<String> productIdList);

    @PostMapping("/product/decreaseStock")
    void decreaseStock(@RequestBody List<CartDTO> cartDTOList);
}
