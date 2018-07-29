package com.yy.enums;

import lombok.Getter;

/**
 * Created by 稻草人 on 2018/7/29.
 */
@Getter
public enum ProductStatusEnum implements CodeEnum {

    UP(0,"在架"),
    DOWN(1,"下架")
        ;

    private Integer code;

    private String message;

    ProductStatusEnum(Integer code,String message){
        this.code=code;
        this.message=message;
    }


}
