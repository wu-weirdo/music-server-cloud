package com.whf.music.aspect;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.whf.music.annotation.Log;
import com.whf.music.entity.LoginUser;
import com.whf.music.enums.BusinessStatus;
import com.whf.music.event.OperationLogEvent;
import com.whf.music.utils.ExceptionUtils;
import com.whf.music.utils.HttpContextUtils;
import com.whf.music.utils.IpUtils;
import com.whf.music.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author whf
 * @date 2024/3/13
 */
@Aspect
@Configuration
@Slf4j
public class LogAspect {

    @Resource
    private ApplicationContext applicationContext;

    /**
     * 排除敏感属性字段
     */
    public static final String[] EXCLUDE_PROPERTIES = {"password", "oldPassword", "newPassword", "confirmPassword"};

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "@annotation(log)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Log log, Object jsonResult) {
        handleLog(joinPoint, log, null, jsonResult);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(pointcut = "@annotation(log)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Log log, Exception e) {
        handleLog(joinPoint, log, e, null);
    }

    private void handleLog(JoinPoint joinPoint, Log operationLog, Exception e, Object jsonResult) {
        try {
            OperationLogEvent event = new OperationLogEvent();
            // 设置模块
            event.setModule(operationLog.module());
            // 设置描述
            event.setDescription(operationLog.description());
            // 设置业务类型
            event.setBusinessType(operationLog.businessType().ordinal());
            // 设置操作类型
            event.setOperatorType(operationLog.operatorType().ordinal());

            LoginUser user = SecurityUtils.getUser();
            if (Objects.nonNull(user)) {
                // 设置操作人信息
                event.setOperatorId(user.getId());
                event.setOperatorName(user.getUsername());
            }

            // 请求相关
            HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
            if (request != null) {
                //设置ip
                event.setIp(IpUtils.getIpAddr(request));
                // 设置ip对应的地址
                event.setLocation(IpUtils.getAddressByIP(event.getIp()));
                // 设置请求地址
                event.setReqUrl(request.getRequestURI());
                // 设置方法名称
                String className = joinPoint.getTarget().getClass().getName();
                String methodName = joinPoint.getSignature().getName();
                event.setMethod(className + "." + methodName + "()");
                // 设置请求方式
                event.setReqMethod(request.getMethod());

                // 是否需要保存request，参数和值
                if (operationLog.isSaveRequestData()) {
                    // 获取参数的信息，传入到数据库中。
                    setRequestValue(joinPoint, event, request, operationLog.excludeParamNames());
                }
                // 是否需要保存response，参数和值
                if (operationLog.isSaveResponseData() && ObjectUtil.isNotNull(jsonResult)) {
                    event.setRespResult(StringUtils.substring(JSONObject.toJSONString(jsonResult), 0, 2000));
                }
            }
            // 设置状态
            event.setStatus(BusinessStatus.SUCCESS.ordinal());
            if (e != null) {
                event.setStatus(BusinessStatus.FAIL.ordinal());
                event.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            }
            event.setOperationTime(new Date());
            // 发布事件
            applicationContext.publishEvent(event);
        } catch (Exception ex) {
            log.error("save log error:{}", ExceptionUtils.getExceptionInfo(ex));
        }
    }

    /**
     * 获取请求的参数，放到log中
     *
     * @throws Exception 异常
     */
    private void setRequestValue(JoinPoint joinPoint, OperationLogEvent event, HttpServletRequest request, String[] excludeParamNames) throws Exception {
        Map<String, String> paramsMap = HttpContextUtils.getParameterMap(request);
        String requestMethod = event.getReqMethod();
        if (MapUtil.isEmpty(paramsMap)
                && HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
            String params = argsArrayToString(joinPoint.getArgs(), excludeParamNames);
            event.setReqParam(StringUtils.substring(params, 0, 2000));
        } else {
            MapUtil.removeAny(paramsMap, EXCLUDE_PROPERTIES);
            MapUtil.removeAny(paramsMap, excludeParamNames);
            event.setReqParam(StringUtils.substring(JSONObject.toJSONString(paramsMap), 0, 2000));
        }
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray, String[] excludeParamNames) {
        StringJoiner params = new StringJoiner(" ");
        if (ArrayUtil.isEmpty(paramsArray)) {
            return params.toString();
        }
        for (Object o : paramsArray) {
            if (ObjectUtil.isNotNull(o) && !isFilterObject(o)) {
                String str = JSONObject.toJSONString(o);
                Dict dict = JSONObject.parseObject(str, Dict.class);
                if (MapUtil.isNotEmpty(dict)) {
                    MapUtil.removeAny(dict, EXCLUDE_PROPERTIES);
                    MapUtil.removeAny(dict, excludeParamNames);
                    str = JSONObject.toJSONString(dict);
                }
                params.add(str);
            }
        }
        return params.toString();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    @SuppressWarnings("rawtypes")
    public boolean isFilterObject(final Object o) {
        Class<?> clazz = o.getClass();
        if (clazz.isArray()) {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        } else if (Collection.class.isAssignableFrom(clazz)) {
            Collection collection = (Collection) o;
            for (Object value : collection) {
                return value instanceof MultipartFile;
            }
        } else if (Map.class.isAssignableFrom(clazz)) {
            Map map = (Map) o;
            for (Object value : map.values()) {
                return value instanceof MultipartFile;
            }
        }
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse
                || o instanceof BindingResult;
    }
}
