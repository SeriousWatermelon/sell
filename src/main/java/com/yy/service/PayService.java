package com.yy.service;

import com.lly835.bestpay.model.PayResponse;
import com.yy.dto.OrderDTO;

/**
 * Created by 稻草人 on 2018/9/10.
 */
public interface PayService {

    /**
     *  创建订单发起支付
     * @param orderDTO
     */
    PayResponse create(OrderDTO orderDTO);

    /**
     * 支付结果异步通知
     * @param notifyData 异步通知支付结果
     */
    PayResponse notify(String notifyData);


}
