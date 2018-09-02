package com.yy.utils;

import java.util.Random;

/**
 * Created by 稻草人 on 2018/9/2.
 *
 */
public class KeyUtil {

    /**
     * 订单详情和订单主表
     * 生成唯一主键
     * synchronized关键字是为了在多线程中，保证生成的key唯一
     * @return
     */
    public static synchronized String getUniqueKey(){
        Random random=new Random();

        //生成六位随机数
        Integer number = random.nextInt(900000)+100000;

        return System.currentTimeMillis()+String.valueOf(number);
    }

}
