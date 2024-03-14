package com.whf.music.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author whf
 * @date 2023/4/21
 */
@Data
public class LoginRequest {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String userName;

    /**
     * 用户密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;
}
