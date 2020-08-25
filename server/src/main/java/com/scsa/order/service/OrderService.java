package com.scsa.order.service;

import com.scsa.order.dto.OrderDTO;

/**
 * @Author: SCSA
 * @Date: 2020/8/20 15:46
 */
public interface OrderService {

    OrderDTO create(OrderDTO orderDTO);

    /**
     * 完结订单（只能卖家操作）
     * @param orderId
     * @return
     */
    OrderDTO finish(String orderId);
}
