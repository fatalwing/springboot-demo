package com.townmc.boot.model.enums;

/**
 * 统一管理的异常错误
 * @author meng
 */
public enum Err {
    // 系统相关
    SYSTEM_ERROR("999999", "系统异常"),
    APPLICATION_ERROR("999998", "应用异常"),
    // 通用的错误
    REQUIRED_PARAMETER("999001", "请输入必填的参数"),
    PARAMETER_ERROR("999002", "参数有误，请检查"),
    NULL_OBJECT("999003", "处理的对象为空"),
    UNSUPPORT_REQUEST("999004", "不支持的请求类型"),
    REQUEST_FAIL("999005", "请求失败"),
    VALID_CODE_ERROR("999006", "验证码错误"),
    VALID_CODE_EXPIRED("999007", "验证码已过期"),
    PASSWORD_ERROR("999008", "密码错误"),
    SMS_SEND_FAIL("999009", "短信发送失败"),
    REQUEST_LIMIT("999010", "请求过于频繁，请稍后再试"),
    UPLOAD_ERROR("999011", "文件上传失败"),
    PERMISSION_DENIED("999012", "该操作没有权限"),
    LOCK_ERROR("999013", "信息同步错误，请稍后再试"),
    SMS_ERROR("999014", "短信初始化"),
    FILE_NOT_EXISTS("999015", "文件不存在"),
    // 与访问令牌有关
    TOKEN_ERROR("999101", "访问令牌错误"),
    TOKEN_EXPIRE("999102", "访问令牌过期"),
    // 与账户有关
    ACCOUNT_NOT_EXISTS("100001", "账户不存在"),
    ACCOUNT_EXISTS("100002", "账户已存在"),
    ACCOUNT_NAME_ERROR("100003", "账户名不符合要求或已经存在"),
    NICKNAME_ERROR("100004", "昵称不符合要求或已经存在"),
    REQUIRED_NAME_AND_PWD("100005", "请输入账号和密码"),
    REQUIRED_MOBILE_AND_CODE("100006", "请输入游戏手机号和验证码"),
    NAME_OR_PWD_ERROR("100007", "账号或密码错误"),
    REQUIRED_MOBILE("100008", "请输入正确的手机号"),
    REQUIRED_VALID_CODE("100009", "请输入验证码"),
    ERROR_PARAM_AWATAR("100010", "提交的头像参数错误"),
    ERROR_PARAM_INVITE_CODE("100011", "邀请码参数错误"),
    MOBILE_EXISTS("100012", "手机号已被注册"),
    ;

    private String error;
    private String message;

    private Err(String error, String message) {
        this.error = error;
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
