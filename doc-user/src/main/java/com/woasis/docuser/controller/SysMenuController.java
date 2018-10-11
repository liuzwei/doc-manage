/*
 *
 * ************************************************************************
 * Copyright (c) 2015-2018 北京无线绿洲通信技术有限公司.
 *
 * projectName:  nesbp
 *    fileName:  SysMenuController.java
 *       Date:  17-11-20 下午3:29
 *     Author:  Liu
 *
 *  ************************************************************************
 */

package com.woasis.docuser.controller;

import com.alibaba.fastjson.JSONObject;
import com.woasis.docuser.entity.SysMenuEntity;
import com.woasis.doc.common.exception.DocException;
import com.woasis.doc.common.exception.ExceptionEnum;
import com.woasis.doc.common.response.Result;
import com.woasis.docuser.service.SysMenuService;
import com.woasis.docuser.util.Constant;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * 系统菜单
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends ControllerConstrants {

	private static final Logger logger = LoggerFactory.getLogger(SysMenuController.class);

	@Autowired
	private SysMenuService sysMenuService;

	/**
	 * 导航菜单
	 */
	@RequestMapping("/nav")
	public Result nav(Long userId){
		if(logger.isDebugEnabled()){
			logger.debug("SysMenuController_nav [/sys/menu/nav] 导航菜单，request参数为:userId="+ userId);
		}
		List<SysMenuEntity> menuList = sysMenuService.getUserMenuList(userId);
		JSONObject object = new JSONObject();
		object.put("menuList", menuList);
		if(logger.isDebugEnabled()){
			logger.debug("SysMenuController_nav [/sys/menu/nav] 导航菜单，response参数为:"+ JSONObject.toJSONString(menuList));
		}
		return Result.OK(object);
	}
	
	/**
	 * 所有菜单列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<SysMenuEntity> list(){
		List<SysMenuEntity> menuList = sysMenuService.queryList(new HashMap<String, Object>());
		if(logger.isDebugEnabled()){
			logger.debug("SysMenuController_list [/sys/menu/list] 所有菜单列表，response参数为:"+ JSONObject.toJSONString(menuList));
		}
		return menuList;
	}
	
	/**
	 * 选择菜单(添加、修改菜单)
	 */
	@RequestMapping("/select")
	public Result select(){
		//查询列表数据
		List<SysMenuEntity> menuList = sysMenuService.queryNotButtonList();
		//添加顶级菜单
		SysMenuEntity root = new SysMenuEntity();
		root.setMenuId(0L);
		root.setName("一级菜单");
		root.setParentId(-1L);
		root.setOpen(true);
		menuList.add(root);
		JSONObject object = new JSONObject();
		object.put("menuList", menuList);
		if(logger.isDebugEnabled()){
			logger.debug("SysMenuController_select [/sys/menu/select] 选择菜单(添加、修改菜单)，response参数为:"+ JSONObject.toJSONString(menuList));
		}
		return Result.OK(object);
	}
	
	/**
	 * 菜单信息
	 */
	@RequestMapping(value="/info/{menuId}",method = RequestMethod.GET)
	public Result info(@PathVariable("menuId") Long menuId){
		if(logger.isDebugEnabled()){
			logger.debug("SysMenuController_info [/sys/menu/info/] 菜单信息，resquest参数为:menuId="+ menuId);
		}
		SysMenuEntity menu = sysMenuService.queryObject(menuId);
		JSONObject object = new JSONObject();
		object.put("menu", menu);
		if(logger.isDebugEnabled()){
			logger.debug("SysMenuController_info [/sys/menu/info/] 菜单信息，response参数为:"+ JSONObject.toJSONString(menu));
		}
		return Result.OK(menu);
	}
	
	/**
	 * 保存菜单
	 */
	@Transactional
	@RequestMapping(value = "/save",method = RequestMethod.POST)
	public Result save(@RequestBody SysMenuEntity menu) throws DocException {
		if(logger.isDebugEnabled()){
			logger.debug("SysMenuController_save [/sys/menu/save] 保存菜单，resquest参数为:"+ menu);
		}
		//数据校验
		verifyForm(menu);
		sysMenuService.save(menu);
		return Result.OK();
	}
	
	/**
	 * 修改
	 */
	@Transactional
	@RequestMapping(value="/update",method = RequestMethod.POST)
	public Result update(@RequestBody SysMenuEntity menu) throws DocException {
		if(logger.isDebugEnabled()){
			logger.debug("SysMenuController_update [/sys/menu/update] 修改菜单，resquest参数为:"+ menu);
		}
		//数据校验
		verifyForm(menu);
		sysMenuService.update(menu);
		return Result.OK();
	}
	
	/**
	 * 删除菜单
	 */
	@Transactional
	@RequestMapping(value="/delete")
	public Result delete(long menuId) throws DocException {
		if(logger.isDebugEnabled()){
			logger.debug("SysMenuController_delete [/sys/menu/delete] 删除菜单，resquest参数为: menuId="+ menuId);
		}
		if(menuId <= 31){
			throw new DocException(ExceptionEnum.DELETE_ABANDON);
		}
		//判断是否有子菜单或按钮
		List<SysMenuEntity> menuList = sysMenuService.queryListParentId(menuId);
		if(menuList.size() > 0){
			throw new DocException(ExceptionEnum.DELETE_SUB_MENU);
		}
		sysMenuService.deleteById(menuId);
		return Result.OK();
	}
	
	/**
	 * 验证参数是否正确
	 */
	private void verifyForm(SysMenuEntity menu) throws DocException {
		if(StringUtils.isBlank(menu.getName())){
			throw new DocException(ExceptionEnum.MENU_NAME_NOTNULL);
		}
		
		if(menu.getParentId() == null){
			throw new DocException(ExceptionEnum.UP_MENU_NOTNULL);
		}
		
		//菜单
		if(menu.getType() == Constant.MenuType.MENU.getValue()){
			if(StringUtils.isBlank(menu.getUrl())){
				throw new DocException(ExceptionEnum.MENU_URL_NOTNULL);
			}
		}
		
		//上级菜单类型
		int parentType = Constant.MenuType.CATALOG.getValue();
		if(menu.getParentId() != 0){
			SysMenuEntity parentMenu = sysMenuService.queryObject(menu.getParentId());
			parentType = parentMenu.getType();
		}
		
		//目录、菜单
		if(menu.getType() == Constant.MenuType.CATALOG.getValue() ||
				menu.getType() == Constant.MenuType.MENU.getValue()){
			if(parentType != Constant.MenuType.CATALOG.getValue()){
				throw new DocException(ExceptionEnum.UP_MENU_ONLY_CATALOG);
			}
			return ;
		}
		
		//按钮
		if(menu.getType() == Constant.MenuType.BUTTON.getValue()){
			if(parentType != Constant.MenuType.MENU.getValue()){
				throw new DocException(ExceptionEnum.UP_MENU_ONLY_MENU);
			}
			return ;
		}
	}
}
