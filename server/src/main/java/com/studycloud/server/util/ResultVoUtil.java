package com.studycloud.server.util;


import com.studycloud.server.VO.ResultVo;

public class ResultVoUtil {
    public static ResultVo success(Object object){
        ResultVo resultVo = new ResultVo();
        resultVo.setData(object);
        resultVo.setCode(100);
        resultVo.setMsg("success!");
        return  resultVo;
    }
}
