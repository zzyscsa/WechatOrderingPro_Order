package com.scsa.order.util;

import java.util.Random;

/**
 * @Author: SCSA
 * @Date: 2020/8/20 15:56
 */
public class KeyUtil {
    public static synchronized String genUniqueKey() {
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;

        return System.currentTimeMillis() + String.valueOf(number);
    }
}
