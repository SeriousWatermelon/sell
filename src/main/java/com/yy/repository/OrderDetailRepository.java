package com.yy.repository;

import com.yy.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 订单详情dao层
 * Created by 稻草人 on 2018/8/19.
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail,Integer> {

    List<OrderDetail> findByOrderId(String orderId);

}
