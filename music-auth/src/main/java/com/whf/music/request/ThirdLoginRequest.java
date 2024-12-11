package com.whf.music.request;

import lombok.Data;

/**
 * @author whf
 * @date 2023/4/21
 */
@Data
public class ThirdLoginRequest {

    private String openType;

    private String code;

    private String state;
}
