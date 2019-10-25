package com.studycloud.server.service;


import com.studycloud.server.dataobject.ProductCategory;

import java.util.List;

public interface CategoryService {
   List<ProductCategory> findByCategoryTypeIn(List<Integer> categorys);
}
