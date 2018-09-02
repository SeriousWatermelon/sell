package com.yy.service;

import com.yy.dataobject.ProductCategory;

import java.util.List;

/**
 * Created by 稻草人 on 2018/7/29.
 */
public interface CategoryService {

    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);

}
