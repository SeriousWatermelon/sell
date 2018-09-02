package com.yy.service.impl;

import com.yy.dto.CartDTO;
import com.yy.enums.ResultEnum;
import com.yy.exception.SellException;
import com.yy.service.ProductService;
import com.yy.dataobject.ProductInfo;
import com.yy.enums.ProductStatusEnum;
import com.yy.repository.ProductInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.transform.Result;
import java.util.List;

/**
 * Created by 稻草人 on 2018/7/29.
 * 查询商品信息service层
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository repository;

    @Override
    public ProductInfo findOne(String productId) {
        return repository.findOne(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for(CartDTO cartDTO:cartDTOList){
            ProductInfo productInfo=repository.findOne(cartDTO.getProductId());
            if(productInfo==null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //原有的+扣除的
            Integer result=cartDTO.getProductQuantity()+productInfo.getProductStock();
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }

    /**
     * 多线程操作时，同时查出库存足够，然后同时扣库存，可能发生超卖(卖的比库存多，无法发货)
     * 暂时未处理，在后期将进行优化处理。
     * @param cartDTOList
     */
    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for(CartDTO cartDTO:cartDTOList){
            ProductInfo productInfo=repository.findOne(cartDTO.getProductId());
            if(productInfo==null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result=productInfo.getProductStock()-cartDTO.getProductQuantity();
            if(result<0){
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);//设置新库存数量

            repository.save(productInfo);//保存
        }
    }
}
