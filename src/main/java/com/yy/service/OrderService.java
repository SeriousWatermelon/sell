package com.yy.service;

import com.yy.dataobject.OrderMaster;
import com.yy.dto.OrderDTO;
import org.hibernate.criterion.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by 稻草人 on 2018/9/1.
 * 买家订单业务逻辑层
 */
public interface OrderService {

    /**
     * 创建订单
     * @param orderDTO  订单详情和订单列表
     * @return
     */
    OrderDTO create(OrderDTO orderDTO);


    /**
     * 查询单个订单
     * @param orderId   订单id
     * @return
     */
    OrderDTO findOne(String orderId);


    /**
     * 查询订单列表
     * @param buyerOpenId   买家openid
     * @param pageable      分页对象
     * @return
     */
    Page<OrderDTO> findList(String buyerOpenId, Pageable pageable);


    /**
     * 取消订单-修改订单状态
     */
    OrderDTO cancel(OrderDTO orderDTO);


    /**
     * 完结订单-修改订单状态
     */
    OrderDTO finish(OrderDTO orderDTO);


    /**
     * 支付订单-修改订单状态
     */
    OrderDTO paid(OrderDTO orderDTO);

    /**
     * 卖家端——查询订单列表
     * @param pageable
     * @return
     */
    Page<OrderDTO> findList(Pageable pageable);

}
