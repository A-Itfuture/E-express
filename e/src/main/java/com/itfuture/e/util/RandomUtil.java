package com.itfuture.e.util;

import java.util.Random;

/**
 * @Description:
 * @Author 王小虎
 * @Version 1.0
 * @Date Created in 2021-12-31 18:20
 */
public class RandomUtil {
    private  static Random random = new Random();
    public static int getCode() {
        return random.nextInt(900000) + 100000;
    }
}
