package com.studycloud.server.service.impl;

import com.studycloud.common.ProductInfoOutput;
import com.studycloud.server.DTO.CartDTO;
import com.studycloud.server.dataobject.ProductInfo;
import com.studycloud.server.enums.ProductStatusEnum;
import com.studycloud.server.enums.ResultEnum;
import com.studycloud.server.exception.ProductException;
import com.studycloud.server.repository.ProductInfoRepository;
import com.studycloud.server.service.ProductService;
import com.studycloud.server.util.JsonUtil;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {


    @Autowired
    ProductInfoRepository productInfoRepository;

    @Autowired
    AmqpTemplate amqpTemplate;

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findAllByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public List<ProductInfo> findByProductIdIn(List<String> idList) {
        return productInfoRepository.findByProductIdIn(idList);
    }

    /**
     * @Transactional(rollbackFor = RuntimeException.class)
     * @Override public void desStock(List<CartDTO> cartDTOList) {
     * for(CartDTO cartDTO:cartDTOList){
     * ProductInfo productInfo = productInfoRepository.findById(cartDTO.getProductId());
     * if(productInfo == null){
     * throw new ProductException(ResultEnum.PRODUCT_NOT_EXIST);
     * }
     * Integer stock = productInfo.getProductStock()-cartDTO.getProductQuantity();
     * if(stock<0){
     * throw new ProductException(ResultEnum.STOCK_ERROR);
     * }
     * productInfo.setProductStock(stock);
     * productInfoRepository.saveProductInfo(productInfo);
     * <p>
     * //发送mq消息
     * /**
     * 此处若第一件商品扣库存成功
     * 第二件抛出异常
     * 但是第一件商品已经发送消息队列会产生错误
     * 此处为Bug
     * <p>
     * 修改为以下两个方法
     * <p>
     * ProductInfoOutput productInfoOutput = new ProductInfoOutput();
     * BeanUtils.copyProperties(productInfo,productInfoOutput);
     * amqpTemplate.convertAndSend("productInfo", JsonUtil.toJson(productInfoOutput));
     * }
     * }
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public List<ProductInfo> desStockProcess(List<CartDTO> cartDTOList) {
        List<ProductInfo> productInfoList = new ArrayList<>();
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = productInfoRepository.findById(cartDTO.getProductId());
            if (productInfo == null) {
                throw new ProductException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer stock = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if (stock < 0) {
                throw new ProductException(ResultEnum.STOCK_ERROR);
            }
            productInfo.setProductStock(stock);
            productInfoRepository.saveProductInfo(productInfo);
            productInfoList.add(productInfo);
        }
        return productInfoList;
    }

    @Override
    public void desStock(List<CartDTO> cartDTOList) {
        List<ProductInfo> productInfoList = desStockProcess(cartDTOList);
        //发送整个购物车消息
        List<ProductInfoOutput>productInfoOutputs = productInfoList.stream().map(e->{
            ProductInfoOutput productInfoOutput = new ProductInfoOutput();
             BeanUtils.copyProperties(e,productInfoOutput);
            return productInfoOutput;
        }).collect(Collectors.toList() );

        amqpTemplate.convertAndSend("productInfo",JsonUtil.toJson(productInfoOutputs));
    }
}
