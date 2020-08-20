package com.scsa.order.service;

import com.scsa.order.dto.OrderDTO;

/**
 * @Author: SCSA
 * @Date: 2020/8/20 15:46
 */
public interface OrderService {

    OrderDTO create(OrderDTO orderDTO);
}
