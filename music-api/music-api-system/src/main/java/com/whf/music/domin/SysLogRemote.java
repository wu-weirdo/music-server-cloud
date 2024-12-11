package com.whf.music.domin;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 操作日志
 *
 * @author whf
 * @date 2024/03/13
 */

@Data
public class SysLogRemote implements Serializable {

    /**
     * 日志主键
     */
    private Long id;

    /**
     * 操作模块
     */
    private String module;

    /**
     * 操作描述
     */
    private String description;

    /**
     * 业务类型（0其它 1新增 2修改 3删除）
     */
    private Integer businessType;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求方式
     */
    private String reqMethod;

    /**
     * 操作类别（0其它 1后台用户 2客户端用户）
     */
    private Integer operatorType;

    /**
     * 操作者ID
     */
    private Long operatorId;

    /**
     * 操作人员姓名
     */
    private String operatorName;

    /**
     * 请求url
     */
    private String reqUrl;

    /**
     * 请求ip
     */
    private String ip;

    /**
     * 请求地址
     */
    private String location;

    /**
     * 请求参数
     */
    private String reqParam;

    /**
     * 返回参数
     */
    private String respResult;

    /**
     * 操作状态（0正常 1异常）
     */
    private Integer status;

    /**
     * 错误消息
     */
    private String errorMsg;

    private Date createTime;

    private Date updateTime;
}
