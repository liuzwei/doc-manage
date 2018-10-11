package com.woasis.docuser.controller;

import com.alibaba.fastjson.JSONObject;
import com.woasis.doc.common.exception.DocException;
import com.woasis.doc.common.response.Result;
import com.woasis.docuser.entity.SysMenuEntity;
import com.woasis.docuser.service.SysMenuService;
import com.woasis.docuser.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * \* Created: liuhuichao
 * \* Date: 2017/11/30
 * \* Time: 下午1:50
 * \* Description:用户登陆授权相关的接口
 * \
 */
@RestController
@RequestMapping("/auth")
public class SysAuthorController extends ControllerConstrants {

    private static final Logger logger = LoggerFactory.getLogger(SysAuthorController.class);

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 登陆接口
     * @param  mobile
     * @return
     */
    @RequestMapping(value = "/login",produces="application/json;charset=UTF-8")
    public Result login(String mobile) throws DocException {
        if(logger.isDebugEnabled()){
            logger.debug("SysAuthorController_login [/auth/login] 登陆接口，request参数为：mobile="+ mobile);
        }
        JSONObject loginResult=sysUserService.login(mobile);
        if(logger.isDebugEnabled()){
            logger.debug("SysAuthorController_login [/auth/login] 登陆接口，reponse参数为:"+ loginResult);
        }
        return Result.OK(loginResult);
    }

    /**
     * 根据用户id获取用户的菜单权限列表
     * @return
     * @throws DocException
     */
    @RequestMapping(value = "/leftmenu",produces="application/json;charset=UTF-8")
    public Result getMenuByUserId(long userId){
        if(logger.isDebugEnabled()){
            logger.debug("SysAuthorController_getMenuByUserId [/auth/leftmenu] 根据用户id获取用户的菜单权限列表，request参数为：userId="+ userId);
        }
        List<SysMenuEntity> menuEntityList= sysMenuService.getUserMenuList(userId);
        if(logger.isDebugEnabled()){
            logger.debug("SysAuthorController_getMenuByUserId [/auth/leftmenu] 根据用户id获取用户的菜单权限列表，response参数为: "+ JSONObject.toJSONString(menuEntityList));
        }
        return Result.OK(menuEntityList);
    }

    /**
     * 根据用户id获取按钮权限数据
     * @param userId
     * @return
     */
    @RequestMapping(value = "/buttenpremissions",produces="application/json;charset=UTF-8")
    public Result getButtonPremission(long userId){
        if(logger.isDebugEnabled()){
            logger.debug("SysAuthorController_getButtonPremission [/auth/buttenpremissions] 根据用户id获取按钮权限数据，request参数为：userId="+ userId);
        }
        List<SysMenuEntity> menuEntityList=sysMenuService.getButtonPremisson(userId);
        if(logger.isDebugEnabled()){
            logger.debug("SysAuthorController_getButtonPremission [/auth/buttenpremissions] 根据用户id获取按钮权限数据，response参数为："+ JSONObject.toJSONString(menuEntityList));
        }
        return Result.OK(menuEntityList);
    }
}
