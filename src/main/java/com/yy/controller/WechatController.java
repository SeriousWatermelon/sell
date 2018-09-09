package com.yy.controller;

import com.yy.enums.ResultEnum;
import com.yy.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;

/**
 * Created by 稻草人 on 2018/9/9.
 * 使用微信SDK进行微信操作：获取用户授权信息等。
 * 参见 https://github.com/wechat-Group/weixin-java-tools
 * wiki地址： https://github.com/Wechat-Group/weixin-java-tools/wiki
 * 引入公众号开发的maven.
 * 注解使用RestController的话，返回的肯定是json文本；因此此处要使用Controller注解。
 */

@Slf4j
@Controller
@RequestMapping("/wechat")
public class WechatController {

    @Autowired
    private WxMpService wxMpService;

    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returlUrl){

        //1. 配置（WechatMpConfig.java、WechatAccountConfig.java、application.yml）

        //2. 调用方法,url写在配置文件中
        String url="http://syy.com/sell/wechat/userInfo";

        //用户无感知，只获取到用户openid
        // String redirectUrl=wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_BASE, URLEncoder.encode(returlUrl));
        String redirectUrl=wxMpService.oauth2buildAuthorizationUrl(
                url,
                WxConsts.OAuth2Scope.SNSAPI_USERINFO,
                URLEncoder.encode(returlUrl));
        log.info("【微信网页授权】获取code,redirectUrl={}",redirectUrl);

        //重定向到 获取用户信息方法中(/userInfo)
        return "redirect:"+redirectUrl;
    }

    /**
     * 获取用户openid
     */
    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code,
                         @RequestParam("state") String returnUrl){
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try{
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e){
            log.error("【微信网页授权】 {}",e);
            throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(),e.getError().getErrorMsg());
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        return "redirect:"+returnUrl+"?openid="+openId;
    }





}










