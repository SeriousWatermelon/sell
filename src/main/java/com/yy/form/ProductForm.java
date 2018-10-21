package com.yy.form;

import com.yy.enums.ProductStatusEnum;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用于新增或修改商品表单的实体类
 * Created by 稻草人 on 2018/10/20.
 */
@Data
public class ProductForm {

    private String productId;

    /** 名字. */
    @NotEmpty(message = "商品名称必填")
    private String productName;

    /** 单价. */
    @NotEmpty(message = "商品单价必填")
    private BigDecimal productPrice;

    /** 库存. */
    @NotEmpty(message = "商品库存必填")
    private Integer productStock;

    /** 描述. */
    private String productDescription;

    /** 小图. */
    private String productIcon;

    /** 状态, 0正常1下架.默认是在架的 */
    private Integer productStatus = ProductStatusEnum.UP.getCode();

    /** 类目编号. */
    @NotEmpty(message = "商品类目必填")
    private Integer categoryType;

}
