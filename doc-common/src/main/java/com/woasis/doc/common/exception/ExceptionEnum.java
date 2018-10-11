package com.woasis.doc.common.exception;

public enum ExceptionEnum {

    ILLEGAL_REQUEST(1001,"非法请求"),
    EXPIRATION_TIME(1002,"token过期"),
    SIGNATUREEXCEPTION(1003,"签名异常"),

    ILLEGAL_CHARACTERS_INCLUDED(2101, "包含非法字符"),

    //权限相关
    DELETE_SUB_DEPT(3010, "请先删除子部门"),
    DELETE_SUB_MENU(3011,"请先删除子菜单或按钮"),
    DELETE_ABANDON(3012,"系统菜单，不能删除"),
    MENU_NAME_NOTNULL(3013,"菜单名称不能为空"),
    UP_MENU_NOTNULL(3014,"上级菜单不能为空"),
    MENU_URL_NOTNULL(3015,"菜单URL不能为空"),
    UP_MENU_ONLY_CATALOG(3016,"上级菜单只能为目录类型"),
    UP_MENU_ONLY_MENU(3017,"上级菜单只能为菜单类型"),
    DELETE_ADMIN_USER_ABANDON(3020,"系统管理员不能删除"),

    /**用户不存在**/
    USER_NOT_EXIST(8001, "用户不存在"),
    /**密码错误**/
    PASSWORD_IS_WRONG(8002, "密码错误"),
    /**登陆成功**/
    LOGIN_IN_SUCCEED(8003, "登陆成功"),
    //用户相关
    /**用户已存在，提示填写其他手机号**/
    USER_MOBILE_EXIST(8004,"用户已存在，请填写其他手机号"),
    /**用户手机号不能为空**/
    USER_MOBILE_CAN_NOT_EMPTY(8005,"用户手机号不能为空"),
    /**账号被禁用**/
    USER_IS_DISABLED(8006,"账号被禁用"),

    //参数为空
    PARAMS_NOT_NULL(3001, "参数不能为空"),
    //动态信息返回
    DYNAMIC_INFO_BACK(9001, "");

    private String msg;
    private Integer code;

    ExceptionEnum(Integer code, String msg) {
        this.msg = msg;
        this.code = code;
    }

    public static String getName(int code) {
        for (ExceptionEnum e : ExceptionEnum.values()) {
            if (e.getCode() == code) {
                return e.getMsg();
            }
        }
        return null;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
