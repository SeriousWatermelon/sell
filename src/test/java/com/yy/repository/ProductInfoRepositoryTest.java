package com.yy.repository;

import com.yy.dataobject.ProductInfo;
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
 * Created by 稻草人 on 2018/7/29.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository repository;

    /**
     * 新增
     * @throws Exception
     */
    @Test
    public void saveProductInfoTest(){
        ProductInfo productInfo=new ProductInfo();
        productInfo.setProductId("123456");
        productInfo.setProductName("皮蛋粥");
        productInfo.setProductPrice(new BigDecimal(3.2)); //单价
        productInfo.setProductStock(100);//库存量
        productInfo.setProductDescription("很好喝");
        productInfo.setProductIcon("htp://***.png");
        productInfo.setProductStatus(0); //0上架；1下架
        productInfo.setCategoryType(2);

        ProductInfo result = repository.save(productInfo);
        Assert.assertNotNull(result);
    }


    @Test
    public void findByProductStatus() throws Exception {
        List<ProductInfo> productInfoList=repository.findByProductStatus(0);
        Assert.assertNotEquals(0,productInfoList.size());
    }

}