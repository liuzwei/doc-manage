/*
 *
 * ************************************************************************
 * Copyright (c) 2015-2018 北京无线绿洲通信技术有限公司.
 *
 * projectName:  nesbp
 *    fileName:  SysDeptDao.java
 *       Date:  17-11-20 下午3:36
 *     Author:  Liu
 *
 *  ************************************************************************
 */

package com.woasis.docuser.mapper;


import com.woasis.docuser.entity.SysDeptEntity;
import com.woasis.docuser.util.DocMapper;

import java.util.List;
import java.util.Map;

/**
 * 部门管理
 *
 */
public interface SysDeptMapper extends DocMapper<SysDeptEntity> {

    /**
     * 查询子部门ID列表
     * @param parentId  上级部门ID
     */
    List<Long> queryDetpIdList(Long parentId);

    List<SysDeptEntity> queryList(Map<String, Object> map);

    /**
     * 保存
     * @param sysDeptEntity
     */
    void save(SysDeptEntity sysDeptEntity);

    /**
     * 根据id更新部门信息
     * @param sysDeptEntity
     */
    void update(SysDeptEntity sysDeptEntity);

    /**
     * 根据id查询部门信息
     * @param deptId
     * @return
     */
    SysDeptEntity queryObject(long deptId);

    /**
     * 根据id删除部门
     * @param deptId
     */
    void deleteById(long deptId);
}
