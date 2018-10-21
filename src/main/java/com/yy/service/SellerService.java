package com.yy.service;

import com.yy.dataobject.SellerInfo;

/**
 * Created by 稻草人 on 2018/10/21.
 */
public interface SellerService {

    /**
     * 根据openid获取卖家信息
     * @param openid
     * @return
     */
    SellerInfo findSellerInfoByOpenid(String openid);

}
