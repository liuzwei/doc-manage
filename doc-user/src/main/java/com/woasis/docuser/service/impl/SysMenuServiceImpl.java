/*
 *
 * ************************************************************************
 * Copyright (c) 2015-2018 北京无线绿洲通信技术有限公司.
 *
 * projectName:  nesbp
 *    fileName:  SysMenuServiceImpl.java
 *       Date:  17-11-20 下午3:35
 *     Author:  Liu
 *
 *  ************************************************************************
 */

package com.woasis.docuser.service.impl;


import com.woasis.docuser.entity.SysMenuEntity;
import com.woasis.docuser.mapper.SysMenuMapper;
import com.woasis.docuser.service.SysMenuService;
import com.woasis.docuser.service.SysUserService;
import com.woasis.docuser.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("sysMenuService")
public class SysMenuServiceImpl implements SysMenuService {
	@Autowired
	private SysMenuMapper sysMenuMapper;
	@Autowired
	private SysUserService sysUserService;
	
	@Override
	public List<SysMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList) {
		List<SysMenuEntity> menuList = queryListParentId(parentId);
		if(menuIdList == null){
			return menuList;
		}
		
		List<SysMenuEntity> userMenuList = new ArrayList<>();
		for(SysMenuEntity menu : menuList){
			if(menuIdList.contains(menu.getMenuId())){
				userMenuList.add(menu);
			}
		}
		return userMenuList;
	}

	@Override
	public List<SysMenuEntity> queryListParentId(Long parentId) {
		return sysMenuMapper.queryListParentId(parentId);
	}

	@Override
	public List<SysMenuEntity> queryNotButtonList() {
		return sysMenuMapper.queryNotButtonList();
	}

	@Override
	public List<SysMenuEntity> getUserMenuList(Long userId) {
		//用户菜单列表
		List<Long> menuIdList = sysUserService.queryAllMenuId(userId);
		return getAllMenuList(menuIdList);
	}
	
	@Override
	public SysMenuEntity queryObject(Long menuId) {
		return sysMenuMapper.queryObject(menuId);
	}

	@Override
	public List<SysMenuEntity> queryList(Map<String, Object> map) {
		return sysMenuMapper.queryList(map);
	}

	@Override
	public int queryTotal() {
		return sysMenuMapper.selectCountByExample(null);
	}

	@Override
	public void save(SysMenuEntity menu) {
		sysMenuMapper.save(menu);
	}

	@Override
	public void update(SysMenuEntity menu) {
		sysMenuMapper.update(menu);
	}

	@Override
	@Transactional
	public void deleteBatch(Long[] menuIds) {
		sysMenuMapper.deleteBatch(menuIds);
	}

	@Override
	public void deleteById(long menuId) {
		sysMenuMapper.deleteById(menuId);
	}

	@Override
	public List<SysMenuEntity> queryUserList(Long userId) {
		return sysMenuMapper.queryUserList(userId);
	}

	/**
	 * 获得用户按钮权限列表
	 * @param userId
	 * @return
	 */
	@Override
	public List<SysMenuEntity> getButtonPremisson(Long userId) {
		return sysMenuMapper.queryButtonPremissions(userId);
	}

	/**
	 * 获取所有菜单列表
	 */
	private List<SysMenuEntity> getAllMenuList(List<Long> menuIdList){
		//查询根菜单列表
		List<SysMenuEntity> menuList = queryListParentId(0L, menuIdList);
		//递归获取子菜单
		getMenuTreeList(menuList, menuIdList);
		
		return menuList;
	}

	/**
	 * 递归
	 */
	private List<SysMenuEntity> getMenuTreeList(List<SysMenuEntity> menuList, List<Long> menuIdList){
		List<SysMenuEntity> subMenuList = new ArrayList<SysMenuEntity>();
		
		for(SysMenuEntity entity : menuList){
			if(entity.getType() == Constant.MenuType.CATALOG.getValue()){//目录
				entity.setList(getMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList), menuIdList));
			}
			subMenuList.add(entity);
		}
		
		return subMenuList;
	}
}
