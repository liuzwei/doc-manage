/*
 *
 * ************************************************************************
 * Copyright (c) 2015-2018 北京无线绿洲通信技术有限公司.
 *
 * projectName:  nesbp
 *    fileName:  SysRoleDao.java
 *       Date:  17-11-20 下午3:36
 *     Author:  Liu
 *
 *  ************************************************************************
 */

package com.woasis.docuser.mapper;

import com.woasis.docuser.entity.SysRoleEntity;
import com.woasis.docuser.util.DocMapper;

import java.util.List;
import java.util.Map;

/**
 * 角色管理
 */
public interface SysRoleMapper extends DocMapper<SysRoleEntity> {

    SysRoleEntity queryObject(Object id);

    List<SysRoleEntity> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    /**
     * 批量删除
     */
    int deleteBatch(Object[] id);

    /**
     * 保存
     * @param sysRoleEntity
     * @return
     */
    int save(SysRoleEntity sysRoleEntity);

    /**
     * 更新 by pk
     * @param sysRoleEntity
     * @return
     */
    int update(SysRoleEntity sysRoleEntity);
}
