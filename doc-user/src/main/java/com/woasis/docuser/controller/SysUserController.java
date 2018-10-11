/*
 *
 * ************************************************************************
 * Copyright (c) 2015-2018 北京无线绿洲通信技术有限公司.
 *
 * projectName:  nesbp
 *    fileName:  SysUserController.java
 *       Date:  17-11-20 下午3:29
 *     Author:  Liu
 *
 *  ************************************************************************
 */

package com.woasis.docuser.controller;


import com.alibaba.fastjson.JSONObject;
import com.woasis.docuser.entity.SysUserEntity;
import com.woasis.docuser.service.SysUserRoleService;
import com.woasis.docuser.service.SysUserService;
import com.woasis.docuser.util.PageUtils;
import com.woasis.docuser.util.Query;
import com.woasis.doc.common.exception.DocException;
import com.woasis.doc.common.exception.ExceptionEnum;
import com.woasis.doc.common.response.Result;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends ControllerConstrants {

	private static final Logger logger = LoggerFactory.getLogger(SysUserController.class);

	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	
	/**
	 * 所有用户列表
	 */
	@RequestMapping(value = "/list",produces="application/json;charset=UTF-8")
	public Result list(@RequestBody Map<String, Object> params) throws DocException {
		if(logger.isDebugEnabled()){
			logger.debug("SysUserController_list [/sys/user/list] 所有用户列表，request参数为:"+ JSONObject.toJSONString(params));
		}
		if (null==params.get("page")){
			params.put("page", 1);
		}
		if (null == params.get("limit")){
			params.put("limit", 10);
		}
		//查询列表数据
		Query query = new Query(params);
		List<SysUserEntity> userList = sysUserService.queryList(query);
		int total = sysUserService.queryTotal(query);
		PageUtils pageUtil = new PageUtils(userList, total, query.getLimit(), query.getPage());
		if(logger.isDebugEnabled()){
			logger.debug("SysUserController_list [/sys/user/list] 所有用户列表，response参数为:"+ JSONObject.toJSONString(pageUtil));
		}
		return Result.OK(pageUtil);
	}


	/**
	 * 修改登录用户密码
	 */
	@Transactional
	@RequestMapping("/password")
	public Result password(String password, String newPassword){
		if(logger.isDebugEnabled()){
			logger.debug("SysUserController_password [/sys/user/password] 修改登录用户密码，request参数为: password="+ password+"; newPassword"+newPassword);
		}
		return Result.OK();
	}
	
	/**
	 * 用户信息
	 */
	@RequestMapping("/info/{userId}")
	public Result info(@PathVariable("userId") Long userId){
		if(logger.isDebugEnabled()){
			logger.debug("SysUserController_info [/sys/user/info/] 查询用户信息，request参数为: userId="+ userId);
		}
		SysUserEntity user = sysUserService.queryObject(userId);
		//获取用户所属的角色列表
		List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
		user.setRoleIdList(roleIdList);
		JSONObject object = new JSONObject();
		object.put("user", user);
		if(logger.isDebugEnabled()){
			logger.debug("SysUserController_info [/sys/user/info/] 查询用户信息，response参数为: "+ JSONObject.toJSONString(user));
		}
		return Result.OK(object);
	}
	
	/**
	 * 保存用户
	 */
	@Transactional
	@RequestMapping(value="/save",method = RequestMethod.POST)
	public Result save(@RequestBody SysUserEntity user)throws DocException {
		if(logger.isDebugEnabled()){
			logger.debug("SysUserController_save [/sys/user/save] 保存用户，request参数为:"+ JSONObject.toJSONString(user));
		}
		sysUserService.save(user);
		return Result.OK();
	}
	
	/**
	 * 修改用户
	 */
	@Transactional
	@RequestMapping(value="/update",method = RequestMethod.POST)
	public Result update(@RequestBody SysUserEntity user)throws DocException {
		if(logger.isDebugEnabled()){
			logger.debug("SysUserController_update [/sys/user/update] 修改用户，request参数为:"+ JSONObject.toJSONString(user));
		}
		sysUserService.update(user);
		return Result.OK();
	}
	
	/**
	 * 删除用户
	 */
	@Transactional
	@RequestMapping("/delete")
	public Result delete(@RequestBody Long[] userIds) throws DocException {
		if(logger.isDebugEnabled()){
			logger.debug("SysUserController_delete [/sys/user/delete] 删除用户，request参数为:userIds="+ JSONObject.toJSONString(userIds));
		}
		if(ArrayUtils.contains(userIds, 1L)){
			throw new DocException(ExceptionEnum.DELETE_ADMIN_USER_ABANDON);
		}
		sysUserService.deleteBatch(userIds);
		return Result.OK();
	}

	@RequestMapping(value="/getBatteryCenterUser", produces="application/json;charset=UTF-8")
	public List<SysUserEntity> getBatteryCenterUser(){
		if(logger.isDebugEnabled()){
			logger.debug("SysUserController_list [/sys/user/getBatteryCenterUser] 所有用户列表");
		}

		//查询列表数据
		List<SysUserEntity> userList = sysUserService.getBatteryCenterUser();
		if(logger.isDebugEnabled()){
			logger.debug("SysUserController_list [/sys/user/getBatteryCenterUser] 所有用户列表，response参数为:"+ JSONObject.toJSONString(userList));
		}
		return userList;
//		return Result.OK(userList);
	}
}
