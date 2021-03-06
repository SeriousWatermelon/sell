package com.yy.service;

import com.yy.dataobject.ProductInfo;
import com.yy.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 商品信息service
 * Created by 稻草人 on 2018/7/29.
 */
public interface ProductService {

    /**
     * 根据商品id查询商品信息
     * @param productId
     * @return
     */
    ProductInfo findOne(String productId);

    /**
     * 查询在架的商品列表
     * @return
     */
    List<ProductInfo> findUpAll();

    /**
     * 分页查询所有的商品列表
     * @return page对象，而不是List对象
     */
    Page<ProductInfo> findAll(Pageable pageable);

    /**
     * 保存商品信息
     * @param productInfo
     * @return
     */
    ProductInfo save(ProductInfo productInfo);

    //增库存
    void increaseStock(List<CartDTO> cartDTOList);

    //减库存
    void decreaseStock(List<CartDTO> cartDTOList);

    //上架
    ProductInfo onSale(String productId);

    //下架
    ProductInfo offSale(String productId);


}







