package com.studycloud.server.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    PRODUCT_NOT_EXIST(1,"商品部存在"),
    STOCK_ERROR(2,"库存不足"),
        SUCCESS(0,"成功");

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
