package com.yy.service.impl;

import com.yy.dataobject.OrderDetail;
import com.yy.dto.OrderDTO;
import com.yy.enums.OrderStatusEnum;
import com.yy.enums.PayStatusEnum;
import com.yy.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by 稻草人 on 2018/9/2.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    private final String BUYER_OPENID="open_id_15574223387";

    private final String ORDER_ID="1539501540159208473";

    @Test
    public void create() throws Exception {
        OrderDTO orderDTO=new OrderDTO();
        //买家信息
        orderDTO.setBuyerName("莹");
        orderDTO.setBuyerAddress("常德");
        orderDTO.setBuyerPhone("26685334498");
        orderDTO.setBuyerOpenid(BUYER_OPENID);

        //买家订单详情信息
        List<OrderDetail> orderDetailList=new ArrayList<>();
        OrderDetail orderDetail1=new OrderDetail();
        orderDetail1.setProductId("123456");
        orderDetail1.setProductQuantity(2);
        orderDetailList.add(orderDetail1);
        OrderDetail orderDetail2=new OrderDetail();
        orderDetail2.setProductId("123457");
        orderDetail2.setProductQuantity(3);
        orderDetailList.add(orderDetail2);
        OrderDetail orderDetail3=new OrderDetail();
        orderDetail3.setProductId("123458");
        orderDetail3.setProductQuantity(1);
        orderDetailList.add(orderDetail3);

        orderDTO.setOrderDetailList(orderDetailList);
        OrderDTO result=orderService.create(orderDTO);

        log.info("【创建订单】result={}",result);
        Assert.assertNotNull(result);
    }

    @Test
    public void fondOne() throws Exception {
        OrderDTO orderDTO=orderService.findOne(ORDER_ID);
        log.info("【查询订单】result={}",orderDTO);
        Assert.assertNotNull(orderDTO);
    }

    @Test
    public void findList() throws Exception {
        PageRequest request = new PageRequest(0,5);
        Page<OrderDTO> orderDTOPage=orderService.findList(BUYER_OPENID,request);
        System.out.println(orderDTOPage.getContent());
        System.out.println(orderDTOPage.getTotalElements());
        Assert.assertNotEquals(0,orderDTOPage.getTotalElements());
    }

    @Test
    public void cancle() throws Exception {
        OrderDTO orderDTO=orderService.findOne(ORDER_ID);
        OrderDTO result=orderService.cancel(orderDTO);
        log.info("【取消订单】result={}",result);
        Assert.assertEquals(OrderStatusEnum.CANCLE.getCode(),result.getOrderStatus());
    }

    @Test
    public void finish() throws Exception {
        OrderDTO orderDTO=orderService.findOne(ORDER_ID);
        OrderDTO result=orderService.finish(orderDTO);
        log.info("【订单完结】result={}",result);
        Assert.assertEquals(OrderStatusEnum.FINISH.getCode(),result.getOrderStatus());
    }

    @Test
    public void paid() throws Exception {
        OrderDTO orderDTO=orderService.findOne(ORDER_ID);
        OrderDTO result=orderService.paid(orderDTO);
        log.info("【订单支付完成】result={}",result);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(),orderDTO.getPayStatus());
    }

    @Test
    public void list() throws Exception{
        PageRequest request=new PageRequest(0,2);
        Page<OrderDTO> orderDTOPage = orderService.findList(request);
        //Assert.assertNotEquals(0,orderDTOPage.getTotalElements());
        //出错时，会打印出message
        Assert.assertTrue("查询所有订单列表",orderDTOPage.getTotalElements()>0);
    }


}