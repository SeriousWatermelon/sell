package com.yy.CategoryService.impl;

import com.yy.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * Created by 稻草人 on 2018/7/29.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Test
    public void findOne() throws Exception {
        ProductCategory productCategory=categoryService.findOne(2);
        Assert.assertEquals(new Integer(2),productCategory.getCategoryId());

    }

    @Test
    public void findAll() throws Exception {
        List<ProductCategory> productCategoryList=categoryService.findAll();
        Assert.assertNotEquals(0,productCategoryList.size());
    }

    @Test
    public void findByCategoryTypeIn() throws Exception {
        List<Integer> list= Arrays.asList(1,2,3,4);
        List<ProductCategory> productCategoryList=categoryService.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0,productCategoryList.size());
    }

    @Test
    public void save() throws Exception {
        ProductCategory productCategory = new ProductCategory("男生专享", 4);
        ProductCategory result=categoryService.save(productCategory);
        Assert.assertNotNull(result);
    }

}