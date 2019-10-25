package com.studycloud.server.VO;

import lombok.Data;

@Data
public class ResultVo<T> {
    private Integer code;
    private String msg;
    /**
     * 数据
     */
    private T data;
}
