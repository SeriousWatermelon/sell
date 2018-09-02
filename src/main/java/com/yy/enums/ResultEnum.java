package com.yy.enums;

import lombok.Getter;

/**
 * Created by 稻草人 on 2018/9/2.
 * 异常信息
 * 枚举类
 */
@Getter
public enum ResultEnum {

    PRODUCT_NOT_EXIST(99,"商品不存在"),
    PRODUCT_STOCK_ERROR(98,"库存不足"),
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code=code;
        this.message=message;
    }
}
