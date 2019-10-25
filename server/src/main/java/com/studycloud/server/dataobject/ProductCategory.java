package com.studycloud.server.dataobject;

import lombok.Data;

import java.util.Date;

@Data
public class ProductCategory {
    private Integer categoryId;
    private String  categoryName;
    private int categoryType;
    private Date createTime;
    private Date updateTime;
}
