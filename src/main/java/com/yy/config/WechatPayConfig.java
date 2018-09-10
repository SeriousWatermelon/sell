package com.yy.config;

import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by 稻草人 on 2018/9/10.
 * 根据微信公众号的配置信息，生成BestPayServiceImpl的Bean
 */
@Component
public class WechatPayConfig {

    @Autowired
    private WechatAccountConfig wechatAccountConfig;

    @Bean
    public BestPayServiceImpl bestPayService(){
        WxPayH5Config wxPayH5Config=wxPayH5Config();
        BestPayServiceImpl bestPayService=new BestPayServiceImpl();
        bestPayService.setWxPayH5Config(wxPayH5Config);

        return bestPayService;
    }

    /**
     * 微信公众号的配置信息独立成一个bean
     */
    @Bean
    public WxPayH5Config wxPayH5Config(){
        WxPayH5Config wxPayH5Config=new WxPayH5Config();
        wxPayH5Config.setAppId(wechatAccountConfig.getMpAppId());   //appID
        wxPayH5Config.setAppSecret(wechatAccountConfig.getMpAppSecret());   //appSecret
        wxPayH5Config.setMchId(wechatAccountConfig.getMchId()); //商户号
        wxPayH5Config.setMchKey(wechatAccountConfig.getMchKey());   //商户密钥
        wxPayH5Config.setKeyPath((wechatAccountConfig.getKeyPath()));   //商户证书路径
        wxPayH5Config.setNotifyUrl(wechatAccountConfig.getNotifyUrl()); //微信异步通知地址

        return wxPayH5Config;
    }


}
