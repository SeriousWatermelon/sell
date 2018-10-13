package com.yy.service.impl;

import com.yy.converter.OrderMaster2OrderDTO;
import com.yy.dataobject.OrderDetail;
import com.yy.dataobject.OrderMaster;
import com.yy.dataobject.ProductInfo;
import com.yy.dto.CartDTO;
import com.yy.dto.OrderDTO;
import com.yy.enums.OrderStatusEnum;
import com.yy.enums.PayStatusEnum;
import com.yy.enums.ResultEnum;
import com.yy.exception.SellException;
import com.yy.repository.OrderDetailRepository;
import com.yy.repository.OrderMasterRepository;
import com.yy.service.OrderService;
import com.yy.service.PayService;
import com.yy.service.ProductService;
import com.yy.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 稻草人 on 2018/9/1.
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private PayService payService;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        //定义总价变量为0,遍历订单详情时计算
        BigDecimal orderAmount=new BigDecimal(BigInteger.ZERO);

        //生成订单id;所有detial的id在同一个主订单下
        String orderId= KeyUtil.getUniqueKey();

        //1.查询商品库存、单价-从数据库商品信息表中获取
        for(OrderDetail orderDetail : orderDTO.getOrderDetailList()){
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if(productInfo==null){//若订单信息不存在，则一定有问题。进行异常处理
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //2.计算订单的总价=单价.multiply(数量) + 原总价
            orderAmount=productInfo.getProductPrice()
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
        orderDTO.setOrderId(orderId);
        OrderMaster orderMaster=new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        //先拷贝属性，在设置值，否则会被orderDTO中的null值替换掉；另外，拷贝时会把orderMaster的状态置空。
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());

        orderMasterRepository.save(orderMaster);

        //4.下单成功后扣减库存数量,lambda表达式
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e->
            new CartDTO(e.getProductId(),e.getProductQuantity())
        ).collect(Collectors.toList());
        productService.decreaseStock(cartDTOList);

        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        //查询订单主表
        OrderMaster orderMaster=orderMasterRepository.findOne(orderId);
        if(orderMaster==null){//判空
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        //查询订单详情表
        List<OrderDetail> orderDetailList=orderDetailRepository.findByOrderId(orderId);
        if(CollectionUtils.isEmpty(orderDetailList)){//判空
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }

        OrderDTO orderDTO=new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    /**
     * 不需要再查询订单的想清了，因为findOne方法中已经实现。
     * @param buyerOpenId   买家openid
     * @param pageable      分页对象
     * @return
     */
    @Override
    public Page<OrderDTO> findList(String buyerOpenId, Pageable pageable) {
        Page<OrderMaster> orderMasterPage=orderMasterRepository.findByBuyerOpenid(buyerOpenId,pageable);
        //不需要判空，因为是否为空都可以返回，不会出现错误。
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTO.convert(orderMasterPage.getContent());
        Page<OrderDTO> orderDTOPage=new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());
        return orderDTOPage;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster=new OrderMaster();
        //BeanUtils.copyProperties(orderDTO,orderMaster);

        //1.查询和判断订单状态-完结状态不能取消
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){//新订单
            log.error("【取消订单】订单状态不正确，orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //2.修改订单状态;先修改DTO状态，再赋值给master，否则只修改了master的状态，并不会修改dto的状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCLE.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult==null){
            log.error("【取消订单】更新失败，orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }

        //3.扣减的库存应恢复,先判断是否有商品
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【取消订单】订单中无商品详情，orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList=orderDTO.getOrderDetailList().stream().
                map(e->new CartDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.increaseStock(cartDTOList);

        //4.查询支付状态，若已支付需要退款
        if(orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            payService.refund(orderDTO);
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        //1.查询和判断订单状态-新下单的订单可以被完结
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【完结订单】订单状态不正确,orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //2.修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISH.getCode());
        OrderMaster orderMaster=new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        //3.保存
        OrderMaster resultUpdate=orderMasterRepository.save(orderMaster);
        if(resultUpdate==null){
            log.error("【完结订单】更新失败,orderMaster={}",orderMaster);
        }

        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        //1.查询和判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【订单支付完成】订单状态不正确,orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //2.查询和判断订单的支付状态
        if(!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.error("【订单支付完成】订单支付状态不正确,orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_PAY_ERROR);
        }

        //3.修改订单的支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster=new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        //4.保存
        OrderMaster updateResult=orderMasterRepository.save(orderMaster);
        if(updateResult==null){
            log.error("【订单支付完成】更新失败,orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(pageable);
        List<OrderDTO> orderDTOList=OrderMaster2OrderDTO.convert(orderMasterPage.getContent());
        return new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());
    }
}
