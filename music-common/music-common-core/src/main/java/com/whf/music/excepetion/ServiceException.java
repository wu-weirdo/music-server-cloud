package com.whf.music.excepetion;

import com.whf.music.enums.ResultEnum;
import lombok.Data;

/**
 * 服务异常
 *
 * @author whf
 * @date 2023/04/21
 */
@Data
public class ServiceException extends RuntimeException {

    private Integer code;

    private String message;

    public ServiceException(String message) {
        this.message = message;
    }

    public ServiceException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ServiceException(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
    }
}