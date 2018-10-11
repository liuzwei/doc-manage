/*
 *
 * ************************************************************************
 * Copyright (c) 2015-2018 北京无线绿洲通信技术有限公司.
 *
 * projectName:  nesbp
 *    fileName:  DocMapper.java
 *       Date:  17-11-20 下午3:37
 *     Author:  Liu
 *
 *  ************************************************************************
 */

package com.woasis.docuser.util;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface DocMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
