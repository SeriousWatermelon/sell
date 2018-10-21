package com.yy.controller;

import com.lly835.bestpay.rest.type.Get;
import com.lly835.bestpay.rest.type.Post;
import com.yy.dataobject.ProductCategory;
import com.yy.dataobject.ProductInfo;
import com.yy.dto.OrderDTO;
import com.yy.exception.SellException;
import com.yy.form.ProductForm;
import com.yy.service.CategoryService;
import com.yy.service.ProductService;
import com.yy.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
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

    @Autowired
    private CategoryService categoryService;

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
        try{
            productService.onSale(productId);
            map.put("msg","商品上架操作成功");
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/success",map);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }

    }

    @GetMapping("/off_sale")
    public ModelAndView offSale(@RequestParam(value="productId") String productId,
                               Map<String,Object> map){
        try{
            productService.offSale(productId);
            map.put("msg","商品下架操作成功");
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/success",map);
        }catch(SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }

    }

    /**
     * 跳转到修改和新增商品页面
     * @param productId
     * @param map
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId",required = false) String productId,
                      Map<String,Object> map){
        if(StringUtils.isNotEmpty(productId)){//id不为空，则为修改；否则为新增
            ProductInfo productInfo = productService.findOne(productId);
            map.put("productInfo",productInfo);
        }
        //查询所有的类目
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList",categoryList);
        return new ModelAndView(("product/index"));
    }

    /**
     * 商品新增和修改
     * @param productForm   提交的表单信息实体类
     * @param bindingResult 参数验证
     * @param map
     * @return
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid ProductForm productForm,
                             BindingResult bindingResult,
                             Map<String,Object> map){
        if(bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/sell/seller/product/index");
            return new ModelAndView("common/error",map);
        }

        ProductInfo productInfo = new ProductInfo();
        try{
            if(!StringUtils.isEmpty(productForm.getProductId())){//产品id不为空，即修改操作
                productInfo = productService.findOne(productForm.getProductId());
            }else{//新增商品手动设置id
                productForm.setProductId(KeyUtil.getUniqueKey());
            }
            BeanUtils.copyProperties(productForm,productInfo);
            productService.save(productInfo);
        }catch(SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/index");
            return new ModelAndView("common/error",map);
        }

        map.put("url","/sell/seller/product/list");
        map.put("msg","操作成功");
        return new ModelAndView("common/success",map);
    }

}
