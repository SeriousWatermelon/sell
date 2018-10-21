package com.yy.service.impl;

import com.yy.dataobject.SellerInfo;
import com.yy.repository.SellerInfoRepository;
import com.yy.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 卖家信息验证业务逻辑层
 * Created by 稻草人 on 2018/10/21.
 */
@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoRepository repository;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return repository.findByOpenid(openid);
    }
}
