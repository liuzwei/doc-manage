/*
 *
 * ************************************************************************
 * Copyright (c) 2015-2018 北京无线绿洲通信技术有限公司.
 *
 * projectName:  nesbp
 *    fileName:  SysDeptServiceImpl.java
 *       Date:  17-11-20 下午3:35
 *     Author:  Liu
 *
 *  ************************************************************************
 */

package com.woasis.docuser.service.impl;

import com.woasis.docuser.entity.SysDeptEntity;
import com.woasis.docuser.mapper.SysDeptMapper;
import com.woasis.docuser.service.SysDeptService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("sysDeptService")
public class SysDeptServiceImpl implements SysDeptService {
	@Autowired
	private SysDeptMapper sysDeptMapper;
	
	@Override
	public SysDeptEntity queryObject(Long deptId){
		return sysDeptMapper.queryObject(deptId);
	}
	
	@Override
	public List<SysDeptEntity> queryList(Map<String, Object> map){
		return sysDeptMapper.queryList(map);
	}
	
	@Override
	public void save(SysDeptEntity sysDept){
		sysDeptMapper.save(sysDept);
	}
	
	@Override
	public void update(SysDeptEntity sysDept){
		sysDeptMapper.update(sysDept);
	}
	
	@Override
	public void delete(Long deptId){
		sysDeptMapper.deleteById(deptId);
	}

	@Override
	public List<Long> queryDetpIdList(Long parentId) {
		return sysDeptMapper.queryDetpIdList(parentId);
	}

	@Override
	public String getSubDeptIdList(Long deptId){
		//部门及子部门ID列表
		List<Long> deptIdList = new ArrayList<>();

		//获取子部门ID
		List<Long> subIdList = queryDetpIdList(deptId);
		getDeptTreeList(subIdList, deptIdList);

		//添加本部门
		deptIdList.add(deptId);

		String deptFilter = StringUtils.join(deptIdList, ",");
		return deptFilter;
	}

	/**
	 * 递归
	 */
	private void getDeptTreeList(List<Long> subIdList, List<Long> deptIdList){
		for(Long deptId : subIdList){
			List<Long> list = queryDetpIdList(deptId);
			if(list.size() > 0){
				getDeptTreeList(list, deptIdList);
			}

			deptIdList.add(deptId);
		}
	}
}
