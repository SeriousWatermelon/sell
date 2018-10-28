package com.yy.service.impl;

import com.yy.config.WechatAccountConfig;
import com.yy.dto.OrderDTO;
import com.yy.service.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created by 稻草人 on 2018/10/28.
 */
@Service
@Slf4j
public class PushMessageServiceImpl implements PushMessageService {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WechatAccountConfig accountConfig;

    @Override
    public void orderStatus(OrderDTO orderDTO) {
        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        //设置微信公众平台设置的微信模板id
        templateMessage.setTemplateId(accountConfig.getTemplateId().get("orderStatus"));
        //设置消息接收人
        templateMessage.setToUser(orderDTO.getBuyerOpenid());
        //设置发送的消息内容
        List<WxMpTemplateData> dataList = Arrays.asList(
                new WxMpTemplateData("first","亲，请记得收货"),
                new WxMpTemplateData("keyword1","微信点餐"),
                new WxMpTemplateData("keyword2","18866669999"),
                new WxMpTemplateData("keyword3",orderDTO.getOrderId()),
                new WxMpTemplateData("keyword4",orderDTO.getOrderStatusEnum().getMessage()),
                new WxMpTemplateData("keyword5","￥"+orderDTO.getOrderAmount()),
                new WxMpTemplateData("remark","欢迎再次光临!")
        );
        templateMessage.setData(dataList);
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (WxErrorException e) {
            /**
             * 只是记录日志但不抛出异常。如果抛出异常，会影响订单完结 orderService.finish()方法
             * 使得操作回滚。不能因为消息推送失败就使得客户无法完成订单。
             */
            log.error("【微信模板消息】发送失败，{}",e);
        }
    }
}
