package com.whf.music.controller;

import com.whf.music.annotation.Log;
import com.whf.music.common.R;
import com.whf.music.entity.LoginUser;
import com.whf.music.enums.BusinessType;
import com.whf.music.enums.OperatorType;
import com.whf.music.request.LoginRequest;
import com.whf.music.request.RegisterRequest;
import com.whf.music.request.ThirdLoginRequest;
import com.whf.music.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author whf
 * @date 2023/4/21
 */
@RestController
@Tag(name = "登录管理")
public class LoginController {

    @Resource
    private LoginService loginService;

    @PostMapping("/login")
    @Operation(summary = "登录")
    @Log(module = "登录管理", description = "登录", businessType = BusinessType.INSERT, operatorType = OperatorType.CLIENT)
    public R<LoginUser> login(@Valid @RequestBody LoginRequest request) {
        return R.success(loginService.login(request), "登录成功");
    }

    @PostMapping("/register")
    @Operation(summary = "注册")
    @Log(module = "登录管理", description = "注册", businessType = BusinessType.INSERT, operatorType = OperatorType.CLIENT)
    public R<Boolean> register(@Valid @RequestBody RegisterRequest request) {
        Boolean result = loginService.register(request);
        if (result) {
            return R.success(true, "注册成功");
        } else {
            return R.error("注册失败");
        }
    }

    @PostMapping("/login/third")
    @Operation(summary = "第三方登录")
    @Log(module = "登录管理", description = "第三方登录", businessType = BusinessType.INSERT, operatorType = OperatorType.CLIENT)
    public R<LoginUser> third(@RequestBody ThirdLoginRequest request) {
        return R.success(loginService.thirdLogin(request), "登录成功");
    }
}
