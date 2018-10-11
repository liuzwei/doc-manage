/*
 *
 * ************************************************************************
 * Copyright (c) 2015-2018 北京无线绿洲通信技术有限公司.
 *
 * projectName:  nesbp
 *    fileName:  SysRoleDeptDao.java
 *       Date:  17-11-20 下午3:36
 *     Author:  Liu
 *
 *  ************************************************************************
 */

package com.woasis.docuser.mapper;

import com.woasis.docuser.entity.SysRoleDeptEntity;
import com.woasis.docuser.util.DocMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 角色与部门对应关系
 *
 */
@Mapper
public interface SysRoleDeptMapper extends DocMapper<SysRoleDeptEntity> {
	
	/**
	 * 根据角色ID，获取部门ID列表
	 */
	List<Long> queryDeptIdList(Long roleId);

	void batchSave(Map<String, Object> map);

	int deleteByRoleId(Object id);
}
