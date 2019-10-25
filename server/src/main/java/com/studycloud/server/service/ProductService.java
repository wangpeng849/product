package com.studycloud.server.service;

import com.studycloud.server.DTO.CartDTO;
import com.studycloud.server.dataobject.ProductInfo;

import java.util.List;

public interface ProductService {
    /**
     * 查询所有在架商品列表
     */
    List<ProductInfo> findUpAll();

    List<ProductInfo> findByProductIdIn(List<String> idList);

    /**
     * 扣库存
     * @param cartDTOList
     */
    void desStock(List<CartDTO> cartDTOList);
}
