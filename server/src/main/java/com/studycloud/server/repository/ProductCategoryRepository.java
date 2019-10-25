package com.studycloud.server.repository;


import com.studycloud.server.dataobject.ProductCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProductCategoryRepository {

     @Select("select " +
             "category_id as categoryId," +
             "category_name as categoryName," +
             "category_type as categoryType," +
             "create_time as createTime," +
             "update_time as  updateTime" +
             " from product_category where category_id=#{id}")
     List<ProductCategory> findAllByProductCategoryId(Integer id);

     @Select("<script>" +
             "select " +
             "category_id as categoryId," +
             "category_name as categoryName," +
             "category_type as categoryType," +
             "create_time as createTime," +
             "update_time as updateTime" +
             " from product_category where category_type in " +
             "<foreach item='item'  collection='categoryTypeList' open='(' separator=',' close=')'>" +
             "#{item}" +
             "</foreach>" +
             "</script>")
     List<ProductCategory> findByCategoryTypeIn(@Param("categoryTypeList") List<Integer> categoryTypeList);
}
