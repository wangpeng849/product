package com.studycloud.server.dataobject;

import lombok.Data;

import java.util.Date;

//@Table(name="product_info")
@Data
public class ProductInfo {
    private String productId;
    private String productName;
    private double productPrice;
    private Integer productStock;
    private String productDescription;
    private Integer productStatus;
    private Integer categoryType;
    private Date createTime;
    private Date updateTime;
}
