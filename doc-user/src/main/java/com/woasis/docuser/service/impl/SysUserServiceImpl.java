/*
 *
 * ************************************************************************
 * Copyright (c) 2015-2018 北京无线绿洲通信技术有限公司.
 *
 * projectName:  nesbp
 *    fileName:  SysUserServiceImpl.java
 *       Date:  17-11-20 下午3:35
 *     Author:  Liu
 *
 *  ************************************************************************
 */

package com.woasis.docuser.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.woasis.docuser.entity.SysUserEntity;
import com.woasis.docuser.mapper.SysUserMapper;
import com.woasis.docuser.service.SysMenuService;
import com.woasis.docuser.service.SysUserRoleService;
import com.woasis.docuser.service.SysUserService;
import com.woasis.doc.common.exception.DocException;
import com.woasis.doc.common.exception.ExceptionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 系统用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:46:09
 */
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {
	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysMenuService sysMenuService;

	@Override
	public List<String> queryAllPerms(Long userId) {
		return sysUserMapper.queryAllPerms(userId);
	}

	@Override
	public List<Long> queryAllMenuId(Long userId) {
		return sysUserMapper.queryAllMenuId(userId);
	}

	@Override
	public SysUserEntity queryByUserName(String username) {
		return sysUserMapper.queryByUserName(username);
	}
	
	@Override
	public SysUserEntity queryObject(Long userId) {
		return sysUserMapper.queryObject(userId);
	}

	@Override
	public List<SysUserEntity> queryList(Map<String, Object> map){
		return sysUserMapper.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map) {
		return sysUserMapper.queryTotal(map);
	}

	@Override
	@Transactional
	public void save(SysUserEntity user) throws DocException {
		user.setCreateTime(new Date());
		if(user.getPassword()!=null && !user.getPassword().equals("")){
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String hashedPassword = passwordEncoder.encode(user.getPassword().trim());
			user.setPassword(hashedPassword);
		}
		if(user.getMobile()==null || user.getMobile().equals("")){
			throw new DocException(ExceptionEnum.USER_MOBILE_CAN_NOT_EMPTY);
		}
		/**
		 * 判断此手机号是否已经存在
		 */
		SysUserEntity isExistUser=sysUserMapper.selectUserByMobileNum(user.getMobile());
		if(isExistUser!=null){
			throw new DocException(ExceptionEnum.USER_MOBILE_EXIST);
		}
		sysUserMapper.save(user);
		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}

	@Override
	@Transactional
	public void update(SysUserEntity user) throws DocException {
		if(user.getPassword()!=null && !user.getPassword().equals("")){
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String hashedPassword = passwordEncoder.encode(user.getPassword().trim());
			user.setPassword(hashedPassword);
		}
		if(user.getMobile()==null || user.getMobile().equals("")){
			throw new DocException(ExceptionEnum.USER_MOBILE_CAN_NOT_EMPTY);
		}
		/**
		 * 判断此手机号是否已经存在
		 */
		SysUserEntity isExistUser=sysUserMapper.selectUserByMobileNum(user.getMobile());
		if(isExistUser!=null && isExistUser.getUserId().longValue()!= user.getUserId().longValue()){
			throw new DocException(ExceptionEnum.USER_MOBILE_EXIST);
		}
		sysUserMapper.update(user);
		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}

	@Override
	@Transactional
	public void deleteBatch(Long[] userId) {
		sysUserMapper.deleteBatch(userId);
	}

	@Override
	public int updatePassword(Long userId, String password, String newPassword){
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("password", password);
		map.put("newPassword", newPassword);
		return sysUserMapper.updatePassword(map);
	}

	@Override
	public JSONObject login(String mobile) throws DocException{
		JSONObject loginResult=new JSONObject();
		if(mobile==null || mobile==""){
			throw new DocException(ExceptionEnum.PARAMS_NOT_NULL);
		}
		SysUserEntity user=sysUserMapper.selectUserByMobileNum(mobile);
		if(user==null){
			throw new DocException(ExceptionEnum.USER_NOT_EXIST);
		}

        loginResult.put("userId",user.getUserId());
        loginResult.put("userName",user.getUsername());
		loginResult.put("account",user.getMobile());
		loginResult.put("pwd",user.getPassword());
		loginResult.put("status",user.getStatus());
		return loginResult;
	}

	@Override
	public List<SysUserEntity> getBatteryCenterUser(){
		List<SysUserEntity> list = sysUserMapper.getBatteryCenterUser();
		return list;
	}

}
