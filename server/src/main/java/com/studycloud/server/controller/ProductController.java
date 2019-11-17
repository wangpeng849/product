package com.studycloud.server.controller;


import com.studycloud.server.DTO.CartDTO;
import com.studycloud.server.VO.ProductInfoVo;
import com.studycloud.server.VO.ProductVo;
import com.studycloud.server.VO.ResultVo;
import com.studycloud.server.dataobject.ProductCategory;
import com.studycloud.server.dataobject.ProductInfo;
import com.studycloud.server.service.CategoryService;
import com.studycloud.server.service.ProductService;
import com.studycloud.server.util.ResultVoUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/list")
    public ResultVo<ProductVo> list() {
        //查询所有上架商品
        List<ProductInfo> productInfoList = productService.findUpAll();
        //读取类别列表
        List<Integer> categoryTypeList = productInfoList.stream()
                .map(ProductInfo::getCategoryType)
                .collect(Collectors.toList());
        //读取类目
        List<ProductCategory> categoryList = categoryService.findByCategoryTypeIn(categoryTypeList);
        //构造数据
        List<ProductVo> productVoList = new ArrayList<>();
        for (ProductCategory productCategory : categoryList) {
            ProductVo productVo = new ProductVo();
            productVo.setCategoryName(productCategory.getCategoryName());
            productVo.setCategoryType(productCategory.getCategoryType());

            List<ProductInfoVo> productInfoVos = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList) {
                if (productCategory.getCategoryType() == productInfo.getCategoryType()) {

                    ProductInfoVo productInfoVo = new ProductInfoVo();
                    BeanUtils.copyProperties(productInfo, productInfoVo);
                    productInfoVos.add(productInfoVo);
                }
            }
            productVo.setProductInfoVoList(productInfoVos);
            productVoList.add(productVo);
        }

        return ResultVoUtil.success(productVoList);
    }



    /**
     * 提供给订单服务
     * @return
     */
    @PostMapping(value="/listForOrder")
    public List<ProductInfo> listForOrder(@RequestBody List<String> productIdList){
       return productService.findByProductIdIn(productIdList);
    }

    @PostMapping("/decreaseStock")
    public String desStock(@RequestBody List<CartDTO> cartDTOList){
        productService.desStock(cartDTOList);
        return "ok";
    }
    /*    public static void main(String[] args) {
        List<Staff> staff = Arrays.asList(
                new Staff("ricky", 30, new BigDecimal(10000)),
                new Staff("jack", 27, new BigDecimal(20000)),
                new Staff("lawrence", 33, new BigDecimal(30000))
        );

        //Before Java 8
        List<String> result = new ArrayList<>();
        for (Staff x : staff) {
            result.add(x.getName());
        }
        System.out.println(result); //[ricky, jack, lawrence]

        //Java 8
        List<String> collect = staff.stream().map(x -> x.getName()).collect(Collectors.toList());
        System.out.println(collect); //[ricky, jack, lawrence]
    }

    @Data
    @AllArgsConstructor
    static class Staff {

        private String name;
        private int age;
        private BigDecimal salary;
        //...
    }*/
}
