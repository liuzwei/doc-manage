/*
 *
 * ************************************************************************
 * Copyright (c) 2015-2018 北京无线绿洲通信技术有限公司.
 *
 * projectName:  nesbp
 *    fileName:  ControllerConstrants.java
 *       Date:  17-11-20 下午4:54
 *     Author:  Liu
 *
 *  ************************************************************************
 */

package com.woasis.docuser.controller;

import com.alibaba.fastjson.JSONObject;

public class ControllerConstrants {

    public String toJsonString(Object obj){

        return JSONObject.toJSONString(obj);
    }
}
