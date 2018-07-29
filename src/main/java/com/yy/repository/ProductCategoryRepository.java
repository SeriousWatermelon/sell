package com.yy.repository;

import com.yy.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 数据持久层
 * Created by 稻草人 on 2018/7/29.
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer > {

    /**
     * 根据category_type的数组查询所有的类目
     */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);


}
