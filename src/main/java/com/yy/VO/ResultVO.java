package com.yy.VO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 视图对象泛型
 * Http返回的最外层对象
 * Created by 稻草人 on 2018/7/29.
 * 查询结果有可能为null，因此添加JsonInclude注解，实现当数据为空时，返回的json字符串不显示该字段。
 * 但是当实体类很多时，一个类一个类的加太过麻烦，可以配置到全局application.yml中。
 */
@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
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
