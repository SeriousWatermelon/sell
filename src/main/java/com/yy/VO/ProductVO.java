package com.yy.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 商品(包含类目)
 * Created by 稻草人 on 2018/7/29.
 */
@Data
public class ProductVO {

    /**
     * 类目名称
     * JsonProperty在将对象序列化时，将categoryName变量转换为name
     */
    @JsonProperty("name")
    private String categoryName;

    /**
     * 类目编号
     */
    @JsonProperty("type")
    private Integer categoryType;


    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;



}










