package com.yy.utils;

import com.yy.enums.CodeEnum;

/**
 * Created by 稻草人 on 2018/10/13.
 * 获取枚举状态信息工具类
 * 传入状态吗，返回状态信息，以便在前台展示状态信息（而不是状态吗）
 */
public class EnumUtil {

    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass){
        for(T each:enumClass.getEnumConstants()){
            if(code.equals(each.getCode())){
                return each;
            }
        }
        return null;
    }
}
