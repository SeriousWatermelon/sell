package com.yy.service.impl;

import com.yy.dataobject.OrderDetail;
import com.yy.dataobject.OrderMaster;
import com.yy.dataobject.ProductInfo;
import com.yy.dto.CartDTO;
import com.yy.dto.OrderDTO;
import com.yy.enums.ResultEnum;
import com.yy.exception.SellException;
import com.yy.repository.OrderDetailRepository;
import com.yy.repository.OrderMasterRepository;
import com.yy.service.OrderService;
import com.yy.service.ProductService;
import com.yy.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.beans.Transient;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 稻草人 on 2018/9/1.
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        //定义总价变量为0,遍历订单详情时计算
        BigDecimal orderAmount=new BigDecimal((BigInteger.ZERO));

        //生成订单id;所有detial的id在同一个主订单下
        String orderId= KeyUtil.getUniqueKey();

        //1.查询商品库存、单价
        for(OrderDetail orderDetail : orderDTO.getOrderDetailList()){
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if(productInfo==null){//若订单信息不存在，则一定有问题。进行异常处理
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //2.计算订单的总价=单价.multiply(数量) + 原总价
            orderAmount=orderDetail.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);
            //订单详情入库(OrderDetail);
            orderDetail.setDetailId(KeyUtil.getUniqueKey());//订单详情id
            orderDetail.setOrderId(orderId);//主订单id

            //将productInfo的名称、图片等信息放入OrderDetail中。
            //传统方法：orderDetail.setProductName(productInfo.getProductName()); ......
            BeanUtils.copyProperties(productInfo,orderDetail);//将productInfo的属性信息拷贝到orderDetail中
            orderDetailRepository.save(orderDetail);

            //传统方法扣库存
            /*CartDTO cartDTO=new CartDTO(orderDetail.getProductId(),orderDetail.getProductQuantity());
            cartDTOList.add(cartDTO);*/
        }
        //3.写入订单数据库（OrderMaster）
        OrderMaster orderMaster=new OrderMaster();
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);

        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMasterRepository.save(orderMaster);

        //4.下单成功后扣减库存数量,lambda表达式
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e->
            new CartDTO(e.getProductId(),e.getProductQuantity())
        ).collect(Collectors.toList());
        productService.decreaseStock(cartDTOList);

        return orderDTO;
    }

    @Override
    public OrderDTO fondOne(String orderId) {
        return null;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenId, Pageable pageable) {
        return null;
    }

    @Override
    public OrderDTO cancle(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        return null;
    }
}
