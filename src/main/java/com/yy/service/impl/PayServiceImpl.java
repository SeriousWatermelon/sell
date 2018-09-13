package com.yy.service.impl;

import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.yy.config.WechatPayConfig;
import com.yy.dto.OrderDTO;
import com.yy.enums.ResultEnum;
import com.yy.exception.SellException;
import com.yy.service.OrderService;
import com.yy.service.PayService;
import com.yy.utils.JsonUtil;
import com.yy.utils.MathUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by 稻草人 on 2018/9/10.
 * 使用best-pay-sdk步骤：https://github.com/search?q=best-pay-api
 * 1. pom.xml中引入依赖
 * 2. 配置微信公众号信息：appID、appSecret、商户号、商密钥、商户证书路径
 *
 */
@Service
@Slf4j
public class PayServiceImpl implements PayService {

    private static final String ORDER_NAME="微信点餐订单";

    @Autowired
    private BestPayServiceImpl bestPayService;

    @Autowired
    private OrderService orderService;

    @Override
    public PayResponse create(OrderDTO orderDTO) {

        PayRequest payRequest=new PayRequest();
        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());//BigDecimal类型转Double类型
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);    //支付类型
        log.info("【微信支付】发起支付，payRequest={}",JsonUtil.toJson(payRequest));

        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("【微信支付】发起支付，payResponse={}", JsonUtil.toJson(payResponse));

        return payResponse;
    }


    /**
     * 涉及四个步骤：
     *      1.签名验证；
     *      2.支付状态；
     *      3.查询支付金额；
     *      4.判断支付人；
     *      5.修改订单状态；
     *      6.异步通知微信支付成功。
     * 其中签名验证和支付状态的判断，bestpay会处理，另外两个需要手动处理。
     *
     * @param notifyData 异步通知支付结果
     * @return
     */
    @Override
    public PayResponse notify(String notifyData) {
         PayResponse payResponse=bestPayService.asyncNotify(notifyData);
        log.info("【微信支付】异步通知，payResponse={}",JsonUtil.toJson(payResponse));

        //先查询订单
        OrderDTO orderDTO=orderService.findOne(payResponse.getOrderId());
        //判断订单是否存在
        if(orderDTO==null){
            log.error("【微信支付】异步通知，订单不存在，orderId={}",payResponse.getOrderId());
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //判断订单金额是否一致。相减=0的做法最好，避开了比较时的精度转换。
        if(!MathUtil.equals(orderDTO.getOrderAmount().doubleValue(),payResponse.getOrderAmount())){
            log.error("【微信支付】异步通知，订单金额不一致，orderId={},微信通知金额={},系统金额={}",
                    payResponse.getOrderId(),payResponse.getOrderAmount(),orderDTO.getOrderAmount());
            throw new SellException(ResultEnum.WXPAY_NOTIFY_MONEY_VERIFY_ERROR);
        }

        //修改支付状态
        orderService.paid(orderDTO);

        return payResponse;
    }


}
