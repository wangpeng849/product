package com.studycloud.server.repository;


import com.studycloud.server.dataobject.ProductInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProductInfoRepository {

     @Select("select * from product_info where product_status=#{productStatus}")
     @Results(id="resultMap", value={
             @Result(column="product_id", property="productId", id=true),
             @Result(column="product_name", property="productName"),
             @Result(column="product_price", property="productPrice"),
             @Result(column="product_stock", property="productStock"),
             @Result(column="product_description", property="productDescription"),
             @Result(column="product_status", property="productStatus"),
             @Result(column="category_type", property="categoryType"),
             @Result(column="create_time", property="createTime"),
             @Result(column="update_time", property="updateTime")
     })
     List<ProductInfo> findAllByProductStatus(Integer productStatus);

    @Select("<script>" +
            "select " +
            "product_id as productId," +
            "product_name as productName," +
            "product_price as productPrice," +
            "product_stock as productStock," +
            "product_description as  productDescription," +
            "product_status as  productStatus," +
            "category_type as  categoryType" +
            " from product_info where product_id in " +
            "<foreach item='item'  collection='idList' open='(' separator=',' close=')'>" +
            "#{item}" +
            "</foreach>" +
            "</script>")
    List<ProductInfo> findByProductIdIn(@Param("idList") List<String> idList);

    @Select("select * from product_info where product_id = #{productId}")
    @ResultMap("resultMap")
    ProductInfo findById(String productId);

    @Update("update product_info set product_stock = #{productInfo.productStock} where product_id = #{productInfo.productId}")
    void saveProductInfo(@Param("productInfo") ProductInfo productInfo);
}
