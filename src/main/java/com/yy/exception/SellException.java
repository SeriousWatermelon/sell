package com.yy.exception;

import com.yy.enums.ResultEnum;

/**
 * Created by 稻草人 on 2018/9/2.
 * 异常类
 */
public class SellException extends RuntimeException {

    private Integer code;

    /**
     * @param resultEnum    异常信息枚举类
     */
    public SellException(ResultEnum resultEnum){
        super(resultEnum.getMessage());//异常信息提示交给父类
        this.code=resultEnum.getCode();
    }

}

