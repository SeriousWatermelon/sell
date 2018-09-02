package com.yy.repository;

import com.yy.dataobject.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by 稻草人 on 2018/8/19.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;

    @Test
    public void saveOrderDetail(){
        OrderDetail orderDetail=new OrderDetail();
        orderDetail.setDetailId("123456780");
        orderDetail.setOrderId("123456");
        orderDetail.setProductIcon("http://xxx.jpg");
        orderDetail.setProductId("123458");
        orderDetail.setProductName("小米粥");
        orderDetail.setProductPrice(new BigDecimal(2.3));
        orderDetail.setProductQuantity(3);

        OrderDetail result=repository.save(orderDetail);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByOrderId() throws Exception {
        List<OrderDetail> orderDetailList=repository.findByOrderId("123456");
        for(OrderDetail od : orderDetailList){
            System.out.println(od.getProductName());
        }
        Assert.assertNotNull(orderDetailList);

    }

}