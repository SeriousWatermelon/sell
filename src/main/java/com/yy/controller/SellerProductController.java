package com.yy.controller;

import com.yy.dataobject.ProductInfo;
import com.yy.dto.OrderDTO;
import com.yy.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 卖家端商品
 * Created by 稻草人 on 2018/10/17.
 */
@Controller
@RequestMapping("/seller/product")
@Slf4j
public class SellerProductController {

    @Autowired
    private ProductService productService;

    /**
     * 获取商品列表
     * @param page  页码
     * @param size  每页记录数
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value="page",defaultValue = "1") Integer page,
                             @RequestParam(value="size",defaultValue = "3") Integer size,
                             Map<String,Object> map){
        PageRequest pageRequest = new PageRequest(page-1,size);
        Page<ProductInfo> productInfoPage = productService.findAll(pageRequest);
        map.put("productInfoPage",productInfoPage);
        map.put("currentPage",page);
        map.put("size",size);

        return new ModelAndView("product/list",map);
    }


    @GetMapping("/on_sale")
    public ModelAndView onSale(@RequestParam(value="productId") String productId,
                               Map<String,Object> map){
        productService.onSale(productId);
        map.put("msg","商品上架操作成功");
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }

    @GetMapping("/off_sale")
    public ModelAndView offSale(@RequestParam(value="productId") String productId,
                               Map<String,Object> map){
        productService.offSale(productId);
        map.put("msg","商品下架操作成功");
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }

}
