package com.yy.service;

import com.yy.dto.OrderDTO;

/**
 * Created by 稻草人 on 2018/10/28.
 * 微信公众平台——微信消息推送
 */
public interface PushMessageService {

    /**
     * (订单完结之后进行推送——即用户付款成功后)
     * OrderServiceImpl中finish方法中调用
     * 订单状态变更消息
     * @param orderDTO
     */
    void orderStatus(OrderDTO orderDTO);

}
