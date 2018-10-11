/*
 *
 * ************************************************************************
 * Copyright (c) 2015-2018 北京无线绿洲通信技术有限公司.
 *
 * projectName:  nesbp
 *    fileName:  SysRoleMenuDao.java
 *       Date:  17-11-20 下午3:36
 *     Author:  Liu
 *
 *  ************************************************************************
 */

package com.woasis.docuser.mapper;

import com.woasis.docuser.entity.SysRoleMenuEntity;
import com.woasis.docuser.util.DocMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 角色与菜单对应关系
 *
 */
@Mapper
public interface SysRoleMenuMapper extends DocMapper<SysRoleMenuEntity> {
	
	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<Long> queryMenuIdList(Long roleId);

	/**
	 * 角色和菜单关系保存
	 * @param map
	 */
	void batchSave(Map<String, Object> map);

	/**
	 * 根据角色ID删除菜单
	 */
	int deleteByRoleId(Object id);
}
