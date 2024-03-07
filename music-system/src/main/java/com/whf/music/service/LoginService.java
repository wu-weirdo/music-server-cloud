package com.whf.music.service;

import com.whf.music.entity.LoginUser;
import com.whf.music.model.request.LoginRequest;
import com.whf.music.model.request.RegisterRequest;

/**
 * @author whf
 * @date 2023/4/21
 */
public interface LoginService {

    /**
     * 登录
     *
     * @param request 请求
     * @return {@code LoginUser}
     */
    LoginUser login(LoginRequest request);

    /**
     * 注册
     *
     * @param request 请求
     * @return {@code Object}
     */
    Boolean register(RegisterRequest request);
}
