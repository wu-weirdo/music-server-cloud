package com.whf.music.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author whf
 * @date 2023/4/21
 */
@Data
@Schema(description = "第三方登录参数")
public class ThirdLoginRequest {

    @Schema(description = "开放平台类型")
    private String openType;

    @Schema(description = "开放平台Code")
    private String code;

    @Schema(description = "state")
    private String state;
}
