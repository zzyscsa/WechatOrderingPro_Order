package com.scsa.order.enums;

import lombok.Getter;

/**
 * @Author: SCSA
 * @Date: 2020/8/20 15:43
 */
@Getter
public enum PayStatusEnum {
    WAIT(0, "等待支付"),
    SUCCESS(1, "支付成功"),
    ;
    private Integer code;

    private String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
