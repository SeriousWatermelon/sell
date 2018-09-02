package com.yy.dto;

import lombok.Data;

/**
 * Created by 稻草人 on 2018/9/2.
 * 购物车传输对象
 */
@Data
public class CartDTO {

    /**
     * 商品id
     */
    private String productId;

    /**
     * 商品数量
     */
    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}


