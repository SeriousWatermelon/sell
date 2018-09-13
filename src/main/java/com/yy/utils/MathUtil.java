package com.yy.utils;

/**
 * Created by 稻草人 on 2018/9/13.
 * 用户支付的金额和数据库中的金额比较
 */
public class MathUtil {
    private static final Double MONEY_RANGE=0.01;

    public static Boolean equals(Double d1,Double d2){
        //abs() 方法可返回数的绝对值
        Double result = Math.abs(d1-d2);
        if(result<MONEY_RANGE){ //若差值的绝对值小于0.01，则认为相等。
            return true;
        }else{
            return false;
        }

    }

}
