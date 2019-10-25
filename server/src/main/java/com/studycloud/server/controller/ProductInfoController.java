package com.studycloud.server.controller;

import com.studycloud.server.dataobject.ProductInfo;
import com.studycloud.server.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductInfoController {

    @Autowired
    ProductService productService;

    @RequestMapping("/getProductInfo")
    public List<ProductInfo> getProducts(){
        return productService.findUpAll();
    }
}
