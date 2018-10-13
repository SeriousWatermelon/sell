package com.yy.dataobject;

import com.yy.enums.OrderStatusEnum;
import com.yy.enums.PayStatusEnum;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单主表
 * Created by 稻草人 on 2018/8/19.
 */
@Data
@Entity
@Table(name = "order_master")
@DynamicUpdate
public class OrderMaster {

    /**
     * 订单编号id
     */
    @Id
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
     * 订单总金额
     */
    private BigDecimal orderAmount;

    /**
     * 订单状态，默认新下单
     */
    private Integer orderStatus= OrderStatusEnum.NEW.getCode();

    /**
     * 支付状态，默认为0,等待支付
     */
    private Integer payStatus= PayStatusEnum.WAIT.getCode();

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

}
