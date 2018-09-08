package com.yy.service;

import com.yy.dto.OrderDTO;

/**
 * Created by 稻草人 on 2018/9/8.
 * 买家service
 * 查询订单和取消订单的逻辑
 * 使结构更加清晰；
 * 对查询的用户的openid进行判断
 */
public interface BuyerService {

    //查询一个订单 详情
    OrderDTO findOrderOne(String openid,String orderId);

    //取消订单
    OrderDTO cancelOrder(String openid,String orderId);

}
