package com.yy.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单详情
 * Created by 稻草人 on 2018/8/19.
 */
@Entity
@Data
@DynamicUpdate
@Table(name = "order_detail")
public class OrderDetail {

    /**
     * 订单详情id
     */
    @Id
    private String detailId;

    /**
     * 订单主表id
     */
    private String orderId;

    /**
     * 商品id
     */
    private String productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品单价——必须从数据库查询，不能从前台获取
     * 即前台不能任意修改单价值
     */
    private BigDecimal productPrice;

    /**
     * 商品数量
     */
    private Integer productQuantity;

    /**
     * 商品图片
     */
    private String productIcon;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
