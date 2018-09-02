package com.yy.repository;

import com.yy.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 订单主表dao层
 * Created by 稻草人 on 2018/8/19.
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster,String>{

    /**
     * 根据用户openid查询订单
     * @param buyerOpenid openid
     * @param pageable 分页查询
     * @return
     */
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);

}
