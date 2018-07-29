package com.yy.repository;

import com.yy.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 商品信息数据逻辑层
 * Created by 稻草人 on 2018/7/29.
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo,String> {

    /**
     * 根据商品上下架的状态查询
     * @param productStatus
     * @return
     */
    List<ProductInfo> findByProductStatus(Integer productStatus);

}



















