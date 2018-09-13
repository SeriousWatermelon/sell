package com.yy.controller;

import com.lly835.bestpay.model.PayResponse;
import com.yy.dto.OrderDTO;
import com.yy.enums.ResultEnum;
import com.yy.exception.SellException;
import com.yy.service.OrderService;
import com.yy.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * Created by 稻草人 on 2018/9/10.
 * 不需要返回json格式，而是返回页面。因此
 * 注解使用Controller
 */

@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;

    /**
     * 发起微信支付并进入h5支付页面
     * @param orderId   订单id
     * @param returnUrl 回调url
     * @param map       给freemaker模板传递的参数。模板中使用${key}的方式进行数据渲染
     *                  key为对象时，可以使用object.property获取其属性的值。
     * @return  modelAndView    h5页面路径（此处指向freemaker数据渲染的h5支付模板）
     */
    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                       @RequestParam("returnUrl") String returnUrl,
                               Map<String,Object> map){
        //1.查询订单
        OrderDTO orderDTO=orderService.findOne(orderId);
        if(orderDTO==null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        //2.发起支付，业务逻辑放在service中（PayServiceImpl.java）。
        PayResponse payResponse=payService.create(orderDTO);
        map.put("payResponse",payResponse);
        map.put("returnUrl",returnUrl);

        return new ModelAndView("pay/create",map);
    }


    /**
     *  微信异步通知；当支付成功后进行异步通知。
     * @param notifyData 将json格式的数据转为java对象。
     */
    @GetMapping("/notify")
    public ModelAndView notify(@RequestBody String notifyData){

        payService.notify(notifyData);
        //将处理结果返回给微信端
        return new ModelAndView("pay/success");

    }


}
