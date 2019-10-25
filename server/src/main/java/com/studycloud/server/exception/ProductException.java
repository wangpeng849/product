package com.studycloud.server.exception;


import com.studycloud.server.enums.ResultEnum;

public class ProductException extends RuntimeException {
    private Integer code;

    public ProductException(Integer code,String msg){
        super(msg);
        this.code = code;
    }

    public ProductException(ResultEnum resultEnum){
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
}
