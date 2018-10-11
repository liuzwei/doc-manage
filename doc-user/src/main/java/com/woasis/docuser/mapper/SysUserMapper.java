/*
 *
 * ************************************************************************
 * Copyright (c) 2015-2018 北京无线绿洲通信技术有限公司.
 *
 * projectName:  nesbp
 *    fileName:  SysUserDao.java
 *       Date:  17-11-20 下午3:36
 *     Author:  Liu
 *
 *  ************************************************************************
 */

package com.woasis.docuser.mapper;

import com.woasis.docuser.entity.SysUserEntity;
import com.woasis.docuser.util.DocMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 *
 */
@Mapper
public interface SysUserMapper extends DocMapper<SysUserEntity> {
	
	/**
	 * 查询用户的所有权限
	 * @param userId  用户ID
	 */
	List<String> queryAllPerms(Long userId);
	
	/**
	 * 查询用户的所有菜单ID
	 */
	List<Long> queryAllMenuId(Long userId);
	
	/**
	 * 根据用户名，查询系统用户
	 */
	SysUserEntity queryByUserName(String username);

	/**
	 * 根据手机号查询用户
	 * @param mobile
	 * @return
	 */
	SysUserEntity selectUserByMobileNum(String mobile);
	
	/**
	 * 修改密码
	 */
	int updatePassword(Map<String, Object> map);


	SysUserEntity queryObject(Object id);

	List<SysUserEntity> queryList(Map<String, Object> map);


	int queryTotal(Map<String, Object> map);

	/**
	 * 批量删除
	 * @param id
	 * @return
	 */
	int deleteBatch(Object[] id);

	/**
	 * 保存用户
	 * @param sysUserEntity
	 * @return
	 */
	int save(SysUserEntity sysUserEntity);

	/**
	 * 更新用户信息by id
	 * @param sysUserEntity
	 * @return
	 */
	int update(SysUserEntity sysUserEntity);

	/**
	 * 获取电池中心所有用户
	 * @return
	 */
	List<SysUserEntity> getBatteryCenterUser();
}
