package com.scsa.order.exception;

import com.scsa.order.enums.ResultEnum;

/**
 * @Author: SCSA
 * @Date: 2020/8/20 16:01
 */
public class OrderException extends RuntimeException {

    private Integer code;

    public OrderException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public OrderException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
}
