package com.studycloud.server.controller;

import com.studycloud.server.dataobject.ProductCategory;
import com.studycloud.server.repository.ProductCategoryRepository;
import com.studycloud.server.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class ProductCategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @RequestMapping("/getProductCategory")
    public List<ProductCategory> getProductCategory(){
        return productCategoryRepository.findAllByProductCategoryId(1);
    }

    @RequestMapping("/findByCategoryTypeIn")
    public List<ProductCategory> findByCategoryTypeIn(){
        return categoryService.findByCategoryTypeIn(Arrays.asList(1,2));
    }
}
