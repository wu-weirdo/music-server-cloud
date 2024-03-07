package com.whf.music.enums;

/**
 * 结果枚举
 *
 * @author whf
 * @date 2023/04/21
 */
public enum ResultEnum {

    SUCCESS(200, "请求成功"),
    ERROR(500, "请求失败"),
    PARAMETER_ERROR(1001, "请求参数有误!"),

    FILE_UPLOAD_ERROR(2001, "文件上传失败!"),

    USER_NOT_EXIST(3001, "用户不存在!"),
    USERNAME_PASSWORD_ERROR(3002, "账号或密码错误!"),

    UNKNOWN_ERROR(9999, "未知的错误!");

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}