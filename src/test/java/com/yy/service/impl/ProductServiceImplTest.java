package com.yy.service.impl;

import com.yy.dataobject.ProductInfo;
import com.yy.enums.ProductStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by 稻草人 on 2018/7/29.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    @Test
    public void findOne() throws Exception {
        ProductInfo productInfo=productService.findOne("123456");
        Assert.assertEquals("123456",productInfo.getProductId());
    }

    @Test
    public void findUpAll() throws Exception {
        List<ProductInfo> productInfoList=productService.findUpAll();
        Assert.assertNotEquals(0,productInfoList.size());
    }

    @Test
    public void findAll() throws Exception {
        PageRequest request = new PageRequest(0,2); //0页2条记录
        Page<ProductInfo> productInfoPage = productService.findAll(request);
        System.out.println(productInfoPage.getTotalElements());
    }

    @Test
    public void save() throws Exception {
        ProductInfo productInfo=new ProductInfo();
        productInfo.setProductId("123457");
        productInfo.setProductName("小米粥");
        productInfo.setProductPrice(new BigDecimal(2.5)); //单价
        productInfo.setProductStock(100);//库存量
        productInfo.setProductDescription("很好喝de小米粥");
        productInfo.setProductIcon("htp://***.png");
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productInfo.setCategoryType(3);

        ProductInfo result=productService.save(productInfo);
        Assert.assertNotNull(result);
    }

}