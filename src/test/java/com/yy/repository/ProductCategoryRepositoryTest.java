package com.yy.repository;

import com.yy.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 稻草人 on 2018/7/29.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void findOneTest(){
        ProductCategory productCategory=repository.findOne(1);
        System.out.println(productCategory.toString());
    }


    //@Transactional事务：测试数据完全回滚，不保留在数据库中。
    @Transactional
    @Test
    public void saveTest(){
        ProductCategory productCategory=new ProductCategory("女生最爱",3);
        ProductCategory result=repository.save(productCategory);//存储或更新后返回原对象
        //Assert.assertNotEquals(null,result); //断言result不为null。
        Assert.assertNotNull(result);
    }

    @Transactional
    @Test
    public void updateTest(){
        ProductCategory productCategory=repository.findOne(2);
        productCategory.setCategoryName("男生最爱");
        productCategory.setCategoryType(2);
        repository.save(productCategory);
    }

    @Test
    public void deleteTest(){
        repository.delete(3);
    }

    @Test
    public void findByCategoryTypeInTest(){
        List<Integer> list= Arrays.asList(2,3,4);

        List<ProductCategory> categories=repository.findByCategoryTypeIn(list);

        Assert.assertNotEquals(0,categories.size());

    }


}












