package com.whf.music.common;

import com.whf.music.enums.ResultEnum;
import lombok.Data;

import java.io.Serializable;


/**
 * 响应结果
 *
 * @author whf
 * @date 2024/03/05
 */
@Data
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code;

    private String message;

    private T data;

    private Boolean success;

    private String type;

    public static <T> R<T> success() {
        return restResult(null, ResultEnum.SUCCESS.getCode(), null, true);
    }

    public static <T> R<T> success(T data) {
        return restResult(data, ResultEnum.SUCCESS.getCode(), null, true);
    }

    public static <T> R<T> success(T data, String msg) {
        return restResult(data, ResultEnum.SUCCESS.getCode(), msg, true);
    }

    public static <T> R<T> error() {
        return restResult(null, ResultEnum.ERROR.getCode(), null, false);
    }

    public static <T> R<T> error(String msg) {
        return restResult(null, ResultEnum.ERROR.getCode(), msg, false);
    }

    public static <T> R<T> error(Integer code, String msg) {
        return restResult(null, code, msg, false);
    }

    public static <T> R<T> error(ResultEnum resultEnum) {
        return restResult(null, resultEnum.getCode(), resultEnum.getMessage(), false);
    }

    public static <T> R<T> fatal(String msg) {
        return restResult(null, ResultEnum.ERROR.getCode(), msg, false);
    }

    public static <T> R<T> fatal(T data) {
        return restResult(data, ResultEnum.ERROR.getCode(), null, false);
    }

    public static <T> R<T> fatal(T data, String msg) {
        return restResult(data, ResultEnum.ERROR.getCode(), msg, false);
    }

    private static <T> R<T> restResult(T data, int code, String msg, boolean success) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMessage(msg);
        apiResult.setSuccess(success);
        if (success) {
            apiResult.setType("success");
        } else {
            apiResult.setType("error");
        }
        return apiResult;
    }

}
