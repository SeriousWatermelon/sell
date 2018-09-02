package com.yy.converter;

import com.yy.dataobject.OrderMaster;
import com.yy.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 稻草人 on 2018/9/2.
 * 转换器：OrderMaster转成OrderDTO
 */
public class OrderMaster2OrderDTO {

    public static OrderDTO convert(OrderMaster orderMaster){
        OrderDTO orderDTO=new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        return orderDTO;
    }

    public static List<OrderDTO> convert(List<OrderMaster> orderMasterList){
        return orderMasterList.stream().map(e->
            convert(e)
        ).collect(Collectors.toList());
    }

}
