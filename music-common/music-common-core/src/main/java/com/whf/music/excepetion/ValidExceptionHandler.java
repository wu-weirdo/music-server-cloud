package com.whf.music.excepetion;

import com.whf.music.common.R;
import com.whf.music.enums.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 参数校验异常处理
 *
 * @author whf
 * @date 2023/04/21
 */
@Slf4j
@RestControllerAdvice("club.mydlq.valid")
public class ValidExceptionHandler {

    /**
    * 忽略参数异常处理器
    *
    * @param e 忽略参数异常
    * @return R
    */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public R parameterMissingExceptionHandler(MissingServletRequestParameterException e) {
        log.error("", e);
        return R.error(ResultEnum.PARAMETER_ERROR.getCode(), "请求参数 " + e.getParameterName() + " 不能为空");
    }

    /**
    * 缺少请求体异常处理器
    *
    * @param e 缺少请求体异常
    * @return R
    */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public R parameterBodyMissingExceptionHandler(HttpMessageNotReadableException e) {
        log.error("", e);
        return R.error(ResultEnum.PARAMETER_ERROR.getCode(), "参数体不能为空");
    }

    /**
    * 参数效验异常处理器
    *
    * @param e 参数验证异常
    * @return ResponseInfo
    */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R parameterExceptionHandler(MethodArgumentNotValidException e) {
        log.error("", e);
        // 获取异常信息
        BindingResult exceptions = e.getBindingResult();
        // 判断异常中是否有错误信息，如果存在就使用异常中的消息，否则使用默认消息
        if (exceptions.hasErrors()) {
            List<ObjectError> errors = exceptions.getAllErrors();
            if (!errors.isEmpty()) {
                // 这里列出了全部错误参数，按正常逻辑，只需要第一条错误即可
                FieldError fieldError = (FieldError) errors.get(0);
                return R.error(ResultEnum.PARAMETER_ERROR.getCode(), fieldError.getDefaultMessage());
            }
        }
        return R.error(ResultEnum.PARAMETER_ERROR.getCode(), ResultEnum.PARAMETER_ERROR.getMessage());
    }

    /**
    * 自定义参数错误异常处理器
    *
    * @param e 自定义参数
    * @return ResponseInfo
    */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ParamErrorException.class})
    public R paramExceptionHandler(ParamErrorException e) {
        log.error("ParamErrorException:", e);
        // 判断异常中是否有错误信息，如果存在就使用异常中的消息，否则使用默认消息
        if (StringUtils.hasText(e.getMessage())) {
        return R.error(ResultEnum.PARAMETER_ERROR.getCode(), e.getMessage());
        }
        return R.error(ResultEnum.PARAMETER_ERROR.getCode(), ResultEnum.PARAMETER_ERROR.getMessage());
    }

}