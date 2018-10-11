package com.woasis.doc.common.exception;

public class DocException extends Exception {

    private ExceptionEnum exceptionEnum;

    public DocException(ExceptionEnum exceptionEnum){
        super(exceptionEnum.getMsg());
        this.exceptionEnum = exceptionEnum;
    }

    public ExceptionEnum getExceptionEnum() {
        return exceptionEnum;
    }

    public void setExceptionEnum(ExceptionEnum exceptionEnum) {
        this.exceptionEnum = exceptionEnum;
    }
}
