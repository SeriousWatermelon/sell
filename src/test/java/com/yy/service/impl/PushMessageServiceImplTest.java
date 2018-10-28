package com.yy.service.impl;

import com.yy.dto.OrderDTO;
import com.yy.service.OrderService;
import com.yy.service.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * Created by 稻草人 on 2018/10/28.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class PushMessageServiceImplTest {

    @Autowired
    private PushMessageService pushMessageService;

    @Autowired
    private OrderService orderService;

    @Test
    public void orderStatus() throws Exception {
        OrderDTO orderDTO = orderService.findOne("1539503914903380708");
        pushMessageService.orderStatus(orderDTO);
    }

}