package com.yy.controller;

import com.yy.VO.ResultVO;
import com.yy.converter.OrderForm2OrderDTO;
import com.yy.dataobject.OrderMaster;
import com.yy.dto.OrderDTO;
import com.yy.enums.ResultEnum;
import com.yy.exception.SellException;
import com.yy.form.OrderForm;
import com.yy.service.BuyerService;
import com.yy.service.OrderService;
import com.yy.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.awt.print.Pageable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 买家详情api
 * Created by 稻草人 on 2018/9/2.
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    /**
     * 创建订单
     * @param orderForm 订单参数，@Valid校验错误
     * @param bindingResult 校验结果对象
     * @return
     */
    @PostMapping("/create")
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm,
                                               BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("【创建订单】参数错误，orderForm={}",orderForm);
            //参数错误的话，会把OrderForm类中注解NotEmpty属性message的值抛出
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDTO orderDTO= OrderForm2OrderDTO.convert(orderForm);
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderDTO orderDTOResult = orderService.create(orderDTO);
        Map<String,String> map=new HashMap<>();
        map.put("orderId",orderDTO.getOrderId());
        return ResultVOUtil.success(map);
    }

    /**
     * 订单列表
     * @param openid
     * @param page 页码 默认为0页
     * @param size 每页记录数 默认为10条记录
     * @return
     * 参数较少，参数和校验方法可以直接写在方法中
     */
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value="page", defaultValue="0") Integer page,
                                         @RequestParam(value="size", defaultValue="10") Integer size){
        if(StringUtils.isEmpty(openid)){
            log.error("【查询订单列表】openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        PageRequest request=new PageRequest(page,size);
        Page<OrderDTO> orderDTOPage = orderService.findList(openid,request);

        //createTime和updateTime转类型Data->long类型，参见OrderDTO实体类注释

        return ResultVOUtil.success(orderDTOPage.getContent());
    }




    /**
     * 查询订单详情
     * 查询订单详情只需订单id即可，但是会存在越权问题，因此需要对openid进行
     * 判断，所以需要openid作为参数。此处暂未处理，标记为TODO。
     * （改进方法，新建买家service，进行查询订单详情和取消订单的逻辑操作）
     * @param openid 用户openid
     * @param orderId 订单id
     * @return 订单列表详情
     */
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                        @RequestParam("orderId") String orderId){
        OrderDTO orderDTO = buyerService.findOrderOne(openid,orderId);
        return ResultVOUtil.success(orderDTO);
    }


    //取消订单
    @PostMapping("/cancle")
    public ResultVO cancle(@RequestParam("openid") String openid,
                             @RequestParam("orderId") String orderId){
        buyerService.cancelOrder(openid,orderId);
        return ResultVOUtil.success();
    }

}




