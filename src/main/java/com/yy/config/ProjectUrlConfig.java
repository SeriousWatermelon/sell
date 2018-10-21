package com.yy.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by 稻草人 on 2018/10/21.
 * 配置信息类
 * 配置项目中用到的url
 */
@Component
@Data
@ConfigurationProperties(prefix = "projectUrl")
public class ProjectUrlConfig {

    /**
     * 微信公众平台账号授权url
     */
    public String wechatMpAuthorize;


    /**
     * 微信开放平台账号授权url
     */
    public String wechatOpenAuthorize;


    /**
     * 点餐系统url
     */
    public String sell;

}
