package com.whf.music.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 * 注册请求
 *
 * @author whf
 * @date 2023/04/21
 */
@Data
public class RegisterRequest {

    @NotBlank(message = "用户名不能为空")
    private String userName;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotNull(message = "性别不能为空")
    private Integer sex;

    private String phoneNum;

    private String email;

    @NotNull(message = "生日不能为空")
    private Date birth;

    private String introduction;

    private String location;
}
