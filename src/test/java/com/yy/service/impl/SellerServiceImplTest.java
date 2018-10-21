package com.yy.service.impl;

import com.yy.dataobject.SellerInfo;
import com.yy.service.SellerService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by 稻草人 on 2018/10/21.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SellerServiceImplTest {

    @Autowired
    private SellerService sellerService;

    @Test
    public void findSellerInfoByOpenid() throws Exception {
        String openid="abc";
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(openid);
        Assert.assertEquals(openid,sellerInfo.getOpenid());

    }

}