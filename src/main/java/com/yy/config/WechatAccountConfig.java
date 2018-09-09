package com.yy.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

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

    private String mpAppId;

    private String mpAppSecret;

}
