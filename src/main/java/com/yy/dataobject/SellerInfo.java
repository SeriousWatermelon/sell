package com.yy.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 卖家个人信息实体类
 * Created by 稻草人 on 2018/10/21.
 */
@Data
@Entity
@DynamicUpdate
@Table(name = "seller_info")
public class SellerInfo {

    @Id
    private String sellerId;

    private String username;

    private String password;

    private String openid;
/*
    private Date createTime;

    private Date updateTime;*/

}
