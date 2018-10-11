/*
 *
 * ************************************************************************
 * Copyright (c) 2015-2018 北京无线绿洲通信技术有限公司.
 *
 * projectName:  nesbp
 *    fileName:  SysRoleController.java
 *       Date:  17-11-20 下午3:29
 *     Author:  Liu
 *
 *  ************************************************************************
 */

package com.woasis.docuser.controller;

import com.alibaba.fastjson.JSONObject;
import com.woasis.docuser.entity.SysRoleEntity;
import com.woasis.docuser.service.SysRoleDeptService;
import com.woasis.docuser.service.SysRoleMenuService;
import com.woasis.docuser.service.SysRoleService;
import com.woasis.docuser.util.Constant;
import com.woasis.docuser.util.PageUtils;
import com.woasis.docuser.util.Query;
import com.woasis.doc.common.exception.DocException;
import com.woasis.doc.common.response.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色管理
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends ControllerConstrants {

	private static final Logger logger = LoggerFactory.getLogger(SysRoleController.class);

	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	@Autowired
	private SysRoleDeptService sysRoleDeptService;
	
	/**
	 * 角色列表
	 */
	@RequestMapping("/list")
	public Result list(@RequestBody Map<String, Object> params) throws DocException {
		if(logger.isDebugEnabled()){
			logger.debug("SysRoleController_list [/sys/role/list] 角色列表，request参数为:"+ JSONObject.toJSONString(params));
		}
		//查询列表数据
		Query query = new Query(params);
		List<SysRoleEntity> list = sysRoleService.queryList(query);
		int total = sysRoleService.queryTotal(query);

		PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());
		if(logger.isDebugEnabled()){
			logger.debug("SysRoleController_list [/sys/role/list] 角色列表，response参数为:"+ JSONObject.toJSONString(pageUtil));
		}
		return Result.OK(pageUtil);
	}
	
	/**
	 * 根据用户id查询角色
	 */
	@RequestMapping("/select")
	public Result select(Integer userId){
		if(logger.isDebugEnabled()){
			logger.debug("SysRoleController_select [/sys/role/select] 根据用户id查询角色，request参数为:userId="+ userId);
		}
		Map<String, Object> map = new HashMap<>();
		//如果不是超级管理员，则只查询自己所拥有的角色列表
		if((userId!=null) && (userId != Constant.SUPER_ADMIN)){
			map.put("createUserId", userId);
		}
		List<SysRoleEntity> list = sysRoleService.queryList(map);
		JSONObject object = new JSONObject();
		object.put("list", list);
		if(logger.isDebugEnabled()){
			logger.debug("SysRoleController_select [/sys/role/select] 根据用户id查询角色，response参数为:"+ JSONObject.toJSONString(object));
		}
		return Result.OK(object);
	}
	
	/**
	 * 角色信息
	 */
	@RequestMapping("/info/{roleId}")
	public Result info(@PathVariable("roleId") Long roleId){
		if(logger.isDebugEnabled()){
			logger.debug("SysRoleController_info [/sys/role/info/] 根据id查询角色信息，request参数为:roleId="+ roleId);
		}
		SysRoleEntity role = sysRoleService.queryObject(roleId);
		//查询角色对应的菜单
		List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
		role.setMenuIdList(menuIdList);
		//查询角色对应的部门
		List<Long> deptIdList = sysRoleDeptService.queryDeptIdList(roleId);
		role.setDeptIdList(deptIdList);
		JSONObject object = new JSONObject();
		object.put("role", role);
		if(logger.isDebugEnabled()){
			logger.debug("SysRoleController_info [/sys/role/info/] 根据id查询角色信息，response参数为:"+ JSONObject.toJSONString(role));
		}
		return Result.OK(object);
	}
	
	/**
	 * 保存角色
	 */
	@Transactional
	@RequestMapping(value="/save",method = RequestMethod.POST)
	public Result save(@RequestBody SysRoleEntity role){
		if(logger.isDebugEnabled()){
			logger.debug("SysRoleController_save [/sys/role/save] 保存角色，request参数为:"+ JSONObject.toJSONString(role));
		}
		sysRoleService.save(role);
		return Result.OK();
	}
	
	/**
	 * 修改角色
	 */
	@Transactional
	@RequestMapping("/update")
	public Result update(@RequestBody SysRoleEntity role){
		if(logger.isDebugEnabled()){
			logger.debug("SysRoleController_update [/sys/role/update] 修改角色，request参数为:"+ JSONObject.toJSONString(role));
		}
		sysRoleService.update(role);
		return Result.OK();
	}
	
	/**
	 * 删除角色
	 */
	@Transactional
	@RequestMapping("/delete")
	public Result delete(@RequestBody Long[] roleIds){
		if(logger.isDebugEnabled()){
			logger.debug("SysRoleController_delete [/sys/role/delete] 删除角色，request参数为:"+ JSONObject.toJSONString(roleIds));
		}
		sysRoleService.deleteBatch(roleIds);
		return Result.OK();
	}
}
