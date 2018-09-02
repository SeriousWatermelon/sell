package com.yy.repository;

import com.yy.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;


/**
 * Created by 稻草人 on 2018/8/19.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository repository;

    private final String OPENID="110";

    @Test
    public void saveOrder(){
        OrderMaster orderMaster=new OrderMaster();
        orderMaster.setOrderId("123457");
        orderMaster.setBuyerName("大师兄");
        orderMaster.setBuyerPhone("13755556666");
        orderMaster.setBuyerAddress("大场镇图书馆");
        orderMaster.setBuyerOpenid(OPENID);
        orderMaster.setOrderAmount(new BigDecimal(2.5));

        OrderMaster result=repository.save(orderMaster);
        Assert.assertNotNull(orderMaster);
    }

    @Test
    public void findByBuyerOpenid() throws Exception {
        PageRequest request=new PageRequest(1,3);
        Page<OrderMaster> result = repository.findByBuyerOpenid(OPENID,request);
        System.out.println(result.getTotalElements());
        System.out.println(result.getContent().size());
        //Assert.assertNotEquals(0,result.getTotalElements());
    }

}