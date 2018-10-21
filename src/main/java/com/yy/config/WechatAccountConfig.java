package com.yy.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;

/**
 * Created by 稻草人 on 2018/9/9.
 *
 * 读取application.yml配置文件中“WeChat”为前缀的属性
 *
 * 微信账号相关的配置信息
 * @ConfigurationProperties 将application.yml，前缀为“wechat”开头的属性值，
 * 注入到该类中。
 */

@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConfig {

    /**
     * 公众平台appID
     */
    private String mpAppId;

    /**
     * 公众平台密钥appSecret
     */
    private String mpAppSecret;

    /**
     * 商户号
     */
    private String mchId;

    /**
     * 商户密钥
     */
    private String mchKey;

    /**
     * 商户证书路径，需要有访问权限
     */
    private String keyPath;

    /**
     * 微信支付异步通知地址
     */
    private String notifyUrl;

    /**
     * 开放平台 网站应用appId
     */
    private String openAppId;

    /**
     * 开放平台 网站应用密钥
     */
    private String openAppSecret;

}
