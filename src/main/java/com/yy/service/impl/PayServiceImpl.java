package com.yy.service.impl;

import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.yy.config.WechatPayConfig;
import com.yy.dto.OrderDTO;
import com.yy.service.PayService;
import com.yy.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public PayResponse create(OrderDTO orderDTO) {

        PayRequest payRequest=new PayRequest();
        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());//BigDecimal类型转Double类型
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);    //支付类型
        log.info("【微信支付】payRequest={}",JsonUtil.toJson(payRequest));

        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("【微信支付】payResponse={}", JsonUtil.toJson(payResponse));

        return payResponse;
    }



}
