package com.yy.dto;

import com.yy.dataobject.OrderDetail;
import com.yy.enums.OrderStatusEnum;
import com.yy.enums.PayStatusEnum;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by 稻草人 on 2018/9/1.
 * 订单DTO。在订单类的基础上加上了订单详情的list集合
 * DTO：传输对象，用于在各个层直接作为传输类
 */
@Data
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
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 订单详情集合
     */
    List<OrderDetail> orderDetailList;
}
