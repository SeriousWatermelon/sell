package com.yy.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yy.dataobject.OrderDetail;
import com.yy.utils.serializer.Date2LongSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by 稻草人 on 2018/9/1.
 * 订单DTO。在订单类的基础上加上了订单详情的list集合
 * DTO：传输对象，用于在各个层直接作为传输类
 *
 * 在获取订单列表时，若订单详情列表为null，则不输出到json串中，
 * 在此使用JsonInclude注解进行实现。
 * 但是当实体类很多时，一个类一个类的加太过麻烦，可以配置到全局application.yml中。
 */
@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {

    /**
     * 订单编号id
     */
    private String orderId;

    /**
     * 买家姓名
     */
    private String buyerName;

    /**
     * 买家电话
     */
    private String buyerPhone;

    /**
     * 买家地址
     */
    private String buyerAddress;

    /**
     * 买家open_id
     */
    private String buyerOpenid;

    /**
     * 订单总金额——在service层计算
     */
    private BigDecimal orderAmount;

    /**
     * 订单状态，默认新下单
     */
    private Integer orderStatus;

    /**
     * 支付状态，默认为0,等待支付
     */
    private Integer payStatus;

    /**
     * 创建时间
     * @JsonSerialize 注解，在进行json序列化时，
     * 将使用using指定的类中的方法进行类型转换
     */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    /**
     * 订单详情集合
     */
    List<OrderDetail> orderDetailList;
}
