package com.scsa.order.util;

import com.scsa.order.VO.ResultVO;

/**
 * @Author: SCSA
 * @Date: 2020/8/20 16:04
 */
public class ResultVOUtil {

    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(object);
        return resultVO;
    }
}
