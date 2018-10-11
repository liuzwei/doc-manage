/*
 *
 * ************************************************************************
 * Copyright (c) 2015-2018 北京无线绿洲通信技术有限公司.
 *
 * projectName:  nesbp
 *    fileName:  SysMenuDao.java
 *       Date:  17-11-20 下午3:36
 *     Author:  Liu
 *
 *  ************************************************************************
 */

package com.woasis.docuser.mapper;


import com.woasis.docuser.entity.SysMenuEntity;
import com.woasis.docuser.util.DocMapper;

import java.util.List;
import java.util.Map;

/**
 * 菜单管理
 * 
 * @author liuzhaowei
 */
public interface SysMenuMapper extends DocMapper<SysMenuEntity> {
	
	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 */
	List<SysMenuEntity> queryListParentId(Long parentId);
	
	/**
	 * 获取不包含按钮的菜单列表
	 */
	List<SysMenuEntity> queryNotButtonList();
	
	/**
	 * 查询用户的权限列表
	 */
	List<SysMenuEntity> queryUserList(Long userId);

	/**
	 * 查询按钮权限列表
	 * @param userId
	 * @return
	 */
	List<SysMenuEntity> queryButtonPremissions(Long userId);

	List<SysMenuEntity> queryList(Map<String, Object> map);

	/**
	 * 根据menuId查询菜单项
	 * @param menuId
	 * @return
	 */
	SysMenuEntity queryObject(Long menuId);

	/**
	 * 批量删除
	 * @param id
	 * @return
	 */
	int deleteBatch(Object[] id);

	/**
	 * 根据id删除
	 * @param id
	 * @return
	 */
	int deleteById(long id);

	/****
	 * 保存
	 * @param sysMenuEntity
	 * @return
	 */
	int save(SysMenuEntity sysMenuEntity);

	/**
	 * 更新
	 * @param sysMenuEntity
	 * @return
	 */
	int update(SysMenuEntity sysMenuEntity);


}
