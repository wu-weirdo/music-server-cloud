package com.whf.music.excepetion;

/**
 * 参数错误异常
 *
 * @author whf
 * @date 2023/04/21
 */
public class ParamErrorException extends RuntimeException {

    public ParamErrorException() {
    }

    public ParamErrorException(String message) {
        super(message);
    }
}