package com.scsa.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 购物车
 * @Author: SCSA
 * @Date: 2020/8/21 23:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {

    private String productId;

    private Integer productQuantity;
}
