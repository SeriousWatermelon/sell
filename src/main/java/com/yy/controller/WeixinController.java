package com.yy.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by 稻草人 on 2018/9/9.
 * 微信支付接口文档：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842
 * 具体而言，网页授权流程分为四步：
 * 1、引导用户进入授权页面同意授权，获取code
 *  获取code的链接：（链接中参数顺序不可改变。）
 * https://open.weixin.qq.com/connect/oauth2/authorize?
 * appid=wxd898fcb01713c658                         //公众号唯一标识，可在公众平台获取；
 * &redirect_uri=http://syy.com/sell/weixin/auth    //授权后重定向的回调链接地址；
 * &response_type=code                              //返回类型，必填写code；code作为换取access_token的票据，
 *                                     每次用户授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期。
 * &scope=snsapi_base                               //应用授权作用域，snsapi_base （不弹出授权页面，直接跳转，
 *                                     只能获取用户openid），snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、
 *                                     性别、所在地。并且， 即使在未关注的情况下，只要用户授权，也能获取其信息 ）
 * &state=STATE                                     //非必填。重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，
 *                                     最多128字节
 * #wechat_redirect                                 //无论直接打开还是做页面302重定向时候，必须带此参数
 *
 *
 * 2、通过code换取网页授权access_token（与基础支持中的access_token不同）
 * 3、如果需要，开发者可以刷新网页授权access_token，避免过期
 * 4、通过网页授权access_token和openid获取用户基本信息（支持UnionID机制）
 *
 * 请求链接https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
 * 返回openid
 *
 */
@Slf4j
@RestController
@RequestMapping("/weixin")
public class WeixinController {

    /**
     * 微信用户授权
     * 手工获取
     * @param code
     */
    @GetMapping("/auth")
    public void auth(@RequestParam("code") String code){
        log.info("【微信认证】进入auth方法");
        log.info("【微信认证】code={}",code);
        String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid=公众号的APPID&secret=公众号的appsecret&code="+code+"&grant_type=authorization_code";
        RestTemplate restTemplate=new RestTemplate();
        //返回值response是一个json格式的字符串，其中包含字段openid
        String response = restTemplate.getForObject(url,String.class);
        log.info("【微信认证】response={}",response);
    }





}
