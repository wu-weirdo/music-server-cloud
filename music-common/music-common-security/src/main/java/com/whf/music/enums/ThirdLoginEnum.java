package com.whf.music.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * 第三方登录枚举
 *
 * @author 阿沐 babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Getter
@AllArgsConstructor
public enum ThirdLoginEnum {
    /**
     * 企业微信
     */
    WECHAT_WORK("wechat_work"),

    /**
     * 钉钉
     */
    DING_TALK("dingtalk"),
    /**
     * 飞书
     */
    FEI_SHU("feishu"),

    /**
     * 微信开放平台
     */
    WECHAT_OPEN("wechat_open");

    private final String value;

    public static ThirdLoginEnum toEnum(String value) {
        for (ThirdLoginEnum item : values()) {
            if (StringUtils.equalsIgnoreCase(item.getValue(), value)) {
                return item;
            }
        }

        throw new IllegalArgumentException("Unsupported third login type: " + value);
    }
}