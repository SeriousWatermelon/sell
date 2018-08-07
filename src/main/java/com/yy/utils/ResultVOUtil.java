package com.yy.utils;

import com.yy.VO.ResultVO;

/**
 * resultVO数据处理工具类
 * Created by 稻草人 on 2018/8/5.
 */
public class ResultVOUtil {

    /**
     * 请求成功的数据返回
     * @param object 返回的数据
     * @return
     */
    public static ResultVO success(Object object){
        ResultVO resultVO=new ResultVO();
        resultVO.setData(object);
        resultVO.setMsg("成功");
        resultVO.setCode(0);
        return resultVO;
    }

    public static ResultVO success(){
        return success(null);
    }

    public static ResultVO error(Integer code,String msg){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }


}
