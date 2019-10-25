package com.studycloud.server.service.impl;

import com.studycloud.server.dataobject.ProductCategory;
import com.studycloud.server.repository.ProductCategoryRepository;
import com.studycloud.server.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categorys) {
        return productCategoryRepository.findByCategoryTypeIn(categorys);
    }
}
