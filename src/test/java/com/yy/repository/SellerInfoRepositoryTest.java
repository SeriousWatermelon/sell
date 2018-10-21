package com.yy.repository;

import com.yy.dataobject.SellerInfo;
import com.yy.utils.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;

import static org.junit.Assert.*;

/**
 * Created by 稻草人 on 2018/10/21.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SellerInfoRepositoryTest {

    @Autowired
    private SellerInfoRepository repository;

    @Test
    public void save(){
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setSellerId(KeyUtil.getUniqueKey());
        sellerInfo.setUsername("syy");
        sellerInfo.setPassword("syy");
        sellerInfo.setOpenid("abc");

        SellerInfo result = repository.save(sellerInfo);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByOpenid(){
        String openid="abc";
        SellerInfo sellerInfo = repository.findByOpenid(openid);
        Assert.assertEquals(openid,sellerInfo.getOpenid());
    }

}