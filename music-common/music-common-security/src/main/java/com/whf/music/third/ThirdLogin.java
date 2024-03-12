package com.whf.music.third;

import lombok.Data;

import java.io.Serializable;

/**
 * 第三方登录 表单数据
 *
 * @author whf
 * @date 2024/03/11
 */
@Data
public class ThirdLogin implements Serializable {
    /**
     * 开放平台类型
     */
    private String openType;

    /**
     * 开放平台Code
     */
    private String code;

    /**
     * state
     */
    private String state;
}
