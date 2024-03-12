package com.whf.music.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * 第三方登录
 *
 * @author whf
 * @date 2024/03/11
 */

@Data
@TableName("third_user")
public class ThirdUser {
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
     * 开放平台，唯一标识
     */
    private String openId;

    /**
     * 昵称
     */
    private String username;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 租户ID
     */
    private Long tenantId;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}