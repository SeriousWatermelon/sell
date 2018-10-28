package com.yy.handler;


import com.yy.VO.ResultVO;
import com.yy.config.ProjectUrlConfig;
import com.yy.exception.ResponseBankException;
import com.yy.exception.SellException;
import com.yy.exception.SellerAuthorizeException;
import com.yy.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by 稻草人 on 2018/10/27.
 * 卖家身份验证
 * SellerAuthorizeException异常处理
 * 以及接口异常处理类
 */
@ControllerAdvice
public class SellerExceptionHandler {

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    //拦截登录异常
    //http://sell.com/sell/wechat.qrAuthorize?returnUrl=http://sell.com/seller/login
    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handlerAuthorizeException(){
        //跳转到登录界面（微信开放平台授权地址）
        return new ModelAndView("redirect:"
                .concat(projectUrlConfig.getWechatOpenAuthorize())
                .concat("/sell/wechat/qrAuthorize")
                .concat("?returnUrl=")
                .concat(projectUrlConfig.getSell())
                .concat("/sell/seller/login")
        );
    }


    /**
     * ExceptionHandler: 处理抛出的SellException异常
     * ResponseBody: 返回值是json
     */
    @ExceptionHandler(value = SellException.class)
    @ResponseBody
    public ResultVO handlerSellException(SellException e){
        return ResultVOUtil.error(e.getCode(),e.getMessage());
    }


    /**
     * ResponseStatus: 返回的status状态码为403
     */
    @ExceptionHandler(value = ResponseBankException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handlerResponseBankException(){

    }



}
