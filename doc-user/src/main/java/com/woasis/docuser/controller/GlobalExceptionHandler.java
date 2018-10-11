/*
 *
 * ************************************************************************
 * Copyright (c) 2015-2018 北京无线绿洲通信技术有限公司.
 *
 * projectName:  nesbp
 *    fileName:  GlobalExceptionHandler.java
 *       Date:  17-11-20 下午5:08
 *     Author:  Liu
 *
 *  ************************************************************************
 */

package com.woasis.docuser.controller;

import com.woasis.doc.common.exception.DocException;
import com.woasis.doc.common.response.Result;
import com.woasis.doc.common.util.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result jsonErrorHandler(HttpServletRequest req, Exception e){
        Result<String> r = new Result<>();
        if (e instanceof  DocException) {
            DocException DocException = (DocException) e;
            r.setMsg(DocException.getExceptionEnum().getMsg());
            r.setCode(DocException.getExceptionEnum().getCode());
        }else {
            r.setMsg("未知异常，联系系统管理员");
            r.setCode(2001);
            logger.error("esbp-author,堆栈:"+ ExceptionUtil.getMessage(e));
        }
        return r;
    }
}
