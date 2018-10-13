package com.yy.service.impl;

import com.yy.dto.OrderDTO;
import com.yy.service.OrderService;
import com.yy.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by 稻草人 on 2018/9/10.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class PayServiceImplTest {

    @Autowired
    private PayService payService;

    @Autowired
    private OrderService orderService;

    @Test
    public void create() throws Exception {
        OrderDTO orderDTO = orderService.findOne("1536382658991849148");
        payService.create(orderDTO);
    }

    @Test
    public void refund() throws Exception {
        OrderDTO orderDTO=orderService.findOne("1536382658991849148");
        payService.refund(orderDTO);

    }


}