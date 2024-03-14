package com.whf.music.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * 第三方登录配置
 *
 * @author whf
 * @date 2024/03/11
 */
@Data
@TableName("third_login_config")
public class ThirdLoginConfig {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 开放平台类型
     */
    private String openType;

    /**
     * ClientID
     */
    private String clientId;

    /**
     * ClientSecret
     */
    private String clientSecret;

    /**
     * RedirectUri
     */
    private String redirectUri;

    /**
     * AgentID
     */
    private String agentId;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 状态 0.正常 1.已删除
     */
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}