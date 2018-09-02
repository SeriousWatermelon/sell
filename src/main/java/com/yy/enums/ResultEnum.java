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
    ORDER_NOT_EXIST(97,"订单不存在"),
    ORDERDETAIL_NOT_EXIST(96,"订单详情不存在"),
    ORDER_STATUS_ERROR(95,"订单状态不正确"),
    ORDER_UPDATE_ERROR(94,"订单更新失败"),
    ORDER_DETAIL_EMPTY(93,"订单详情为空"),
    ORDER_PAY_ERROR(92,"订单支付状态错误");
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code=code;
        this.message=message;
    }
}
