package com.scsa.order.VO;

import lombok.Data;

/**
 * @Author: SCSA
 * @Date: 2020/8/20 16:01
 */
@Data
public class ResultVO<T> {

    private Integer code;

    private String msg;

    private T data;
}
