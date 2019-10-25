package com.studycloud.server.service.impl;

import com.studycloud.server.DTO.CartDTO;
import com.studycloud.server.dataobject.ProductInfo;
import com.studycloud.server.enums.ProductStatusEnum;
import com.studycloud.server.enums.ResultEnum;
import com.studycloud.server.exception.ProductException;
import com.studycloud.server.repository.ProductInfoRepository;
import com.studycloud.server.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {


    @Autowired
    ProductInfoRepository productInfoRepository;

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findAllByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public List<ProductInfo> findByProductIdIn(List<String> idList) {
        return productInfoRepository.findByProductIdIn(idList);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void desStock(List<CartDTO> cartDTOList) {
        for(CartDTO cartDTO:cartDTOList){
            ProductInfo productInfo = productInfoRepository.findById(cartDTO.getProductId());
            if(productInfo == null){
                throw new ProductException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer stock = productInfo.getProductStock()-cartDTO.getProductQuantity();
            if(stock<0){
                throw new ProductException(ResultEnum.STOCK_ERROR);
            }
            productInfo.setProductStock(stock);
            productInfoRepository.saveProductInfo(productInfo);
        }
    }
}
