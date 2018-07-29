package com.yy.controller;

import com.yy.VO.ProductInfoVO;
import com.yy.VO.ProductVO;
import com.yy.VO.ResultVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * Created by 稻草人 on 2018/7/29.
 */

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @GetMapping("/list")
    public ResultVO list(){
        ResultVO resultVO=new ResultVO();
        ProductVO productVO=new ProductVO();
        ProductInfoVO productInfoVO=new ProductInfoVO();
        productVO.setProductInfoVOList(Arrays.asList(productInfoVO));

        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(Arrays.asList(productVO));

        return resultVO;
    }






}









