package com.yy.repository;

import com.yy.dataobject.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 卖家信息dao
 * Created by 稻草人 on 2018/10/21.
 */
public interface SellerInfoRepository extends JpaRepository<SellerInfo,String> {

    //根据openid查询卖家信息
    SellerInfo findByOpenid(String openid);






}
