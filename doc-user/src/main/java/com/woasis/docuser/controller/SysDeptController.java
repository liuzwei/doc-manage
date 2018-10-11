/*
 *
 * ************************************************************************
 * Copyright (c) 2015-2018 北京无线绿洲通信技术有限公司.
 *
 * projectName:  nesbp
 *    fileName:  SysDeptController.java
 *       Date:  17-11-20 下午3:29
 *     Author:  Liu
 *
 *  ************************************************************************
 */

package com.woasis.docuser.controller;

import com.alibaba.fastjson.JSONObject;
import com.woasis.docuser.entity.SysDeptEntity;
import com.woasis.docuser.service.SysDeptService;
import com.woasis.docuser.util.Constant;
import com.woasis.doc.common.exception.DocException;
import com.woasis.doc.common.exception.ExceptionEnum;
import com.woasis.doc.common.response.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


/**
 * 部门管理
 */
@RestController
@RequestMapping("/sys/dept")
public class SysDeptController extends ControllerConstrants  {

	private static final Logger logger = LoggerFactory.getLogger(SysDeptController.class);

	@Autowired
	private SysDeptService sysDeptService;
	
	/**
	 * 部门列表
	 */
	@RequestMapping("/list")
	public List<SysDeptEntity> list(){
		List<SysDeptEntity> deptList = sysDeptService.queryList(new HashMap<String, Object>());
		if(logger.isDebugEnabled()){
			logger.debug("SysDeptController_list [/sys/dept/list] 部门列表，reponse参数为:"+ JSONObject.toJSONString(deptList));
		}
		return deptList;
	}

	/**
	 * 选择部门(添加、修改菜单)
	 */
	@RequestMapping("/select")
	public String select(){
		List<SysDeptEntity> deptList = sysDeptService.queryList(new HashMap<String, Object>());
		//添加一级部门
		SysDeptEntity root = new SysDeptEntity();
		root.setDeptId(0L);
		root.setName("一级部门");
		root.setParentId(-1L);
		root.setOpen(true);
		deptList.add(root);
		Result result = new Result();
		result.setCode(1000);
		result.setData(deptList);
		if(logger.isDebugEnabled()){
			logger.debug("SysDeptController_select [/sys/dept/select] 选择部门(添加、修改菜单)，reponse参数为:"+ JSONObject.toJSONString(result));
		}
		return toJsonString(result);
	}

	/**
	 * 上级部门Id(管理员则为0)
	 */
	@RequestMapping("/info")
	public String info(Integer userId, Long deptId){
		if(logger.isDebugEnabled()){
			logger.debug("SysDeptController_info [/sys/dept/info] 上级部门Id(管理员则为0)，request参数为:userId="+ userId+" ;deptId="+deptId);
		}
		if(userId != Constant.SUPER_ADMIN){
			SysDeptEntity dept = sysDeptService.queryObject(deptId);
			deptId = dept.getParentId();
		}
		JSONObject object = new JSONObject();
		object.put("deptId", deptId);
		Result result = new Result();
		result.setCode(1000);
		result.setData(object);
		if(logger.isDebugEnabled()){
			logger.debug("SysDeptController_info [/sys/dept/info] 上级部门Id(管理员则为0)，reponse参数为:"+ JSONObject.toJSONString(result));
		}
		return toJsonString(result);
	}
	
	/**
	 * 部门信息
	 */
	@RequestMapping("/info/{deptId}")
	public Result info(@PathVariable("deptId") Long deptId){
		if(logger.isDebugEnabled()){
			logger.debug("SysDeptController_info [/sys/dept/info/] 部门信息，request参数为:"+"deptId="+deptId);
		}
		SysDeptEntity dept = sysDeptService.queryObject(deptId);
		JSONObject object = new JSONObject();
		object.put("dept", dept);
		if(logger.isDebugEnabled()){
			logger.debug("SysDeptController_info [/sys/dept/info/] 部门信息，response参数为:"+JSONObject.toJSONString(dept));
		}
		return Result.OK(dept);
	}
	
	/**
	 * 保存部门信息
	 */
	@Transactional
	@RequestMapping(value="/save", method = RequestMethod.POST)
	public String save(@RequestBody SysDeptEntity dept){
		if(logger.isDebugEnabled()){
			logger.debug("SysDeptController_save [/sys/dept/save] 保存部门信息，request参数为:"+JSONObject.toJSONString(dept));
		}
		sysDeptService.save(dept);
		Result result = new Result();
		result.setCode(1000);
		if(logger.isDebugEnabled()){
			logger.debug("SysDeptController_save [/sys/dept/save] 保存部门信息，response参数为:"+JSONObject.toJSONString(result));
		}
		return toJsonString(result);
	}
	
	/**
	 * 修改部门信息
	 */
	@Transactional
	@RequestMapping(value="/update", method = RequestMethod.POST)
	public String update(@RequestBody SysDeptEntity dept){
		if(logger.isDebugEnabled()){
			logger.debug("SysDeptController_update [/sys/dept/update] 修改部门信息，request参数为:"+JSONObject.toJSONString(dept));
		}
		sysDeptService.update(dept);
		Result result = new Result();
		result.setCode(1000);
		if(logger.isDebugEnabled()){
			logger.debug("SysDeptController_update [/sys/dept/update] 修改部门信息，response参数为:"+JSONObject.toJSONString(result));
		}
		return toJsonString(result);
	}
	
	/**
	 * 删除
	 */
	@Transactional
	@RequestMapping("/delete")
	public Result delete(long deptId) throws DocException {
		if(logger.isDebugEnabled()){
			logger.debug("SysDeptController_delete [/sys/dept/delete] 删除部门，request参数为: deptId="+deptId);
		}
		//判断是否有子部门
		List<Long> deptList = sysDeptService.queryDetpIdList(deptId);
		if(deptList.size() > 0){
			throw new DocException(ExceptionEnum.DELETE_SUB_DEPT);
		}
		sysDeptService.delete(deptId);
		return Result.OK();
	}
	
}
