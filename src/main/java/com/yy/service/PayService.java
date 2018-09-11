package com.yy.service;

import com.lly835.bestpay.model.PayResponse;
import com.yy.dto.OrderDTO;

/**
 * Created by 稻草人 on 2018/9/10.
 */
public interface PayService {

    /**
     *
     * @param orderDTO
     */
    PayResponse create(OrderDTO orderDTO);


}
