package com.yy.VO;

import lombok.Data;

/**
 * 视图对象泛型
 * Http返回的最外层对象
 * Created by 稻草人 on 2018/7/29.
 */
@Data
public class ResultVO<T> {

    /**
     * 错误码
     * 0成功
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String msg;


    /**
     * 返回对象
     */
    private T data;




}
