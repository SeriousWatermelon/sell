package com.yy.enums;

import lombok.Getter;

/**
 * Created by 稻草人 on 2018/8/19.
 */
@Getter
public enum PayStatusEnum implements CodeEnum {

    WAIT(0,"等待支付"),
    SUCCESS(1,"支付成功")
        ;

    private Integer code;

    private String message;

    PayStatusEnum(Integer code,String message){
        this.code=code;
        this.message=message;
    }

}
