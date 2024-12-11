package com.whf.music.service.impl;

import com.whf.music.domin.UserRemote;
import com.whf.music.dubbo.UserRemoteService;
import com.whf.music.entity.LoginUser;
import com.whf.music.excepetion.ServiceException;
import com.whf.music.request.LoginRequest;
import com.whf.music.request.RegisterRequest;
import com.whf.music.request.ThirdLoginRequest;
import com.whf.music.service.LoginService;
import com.whf.music.service.TokenService;
import com.whf.music.third.ThirdAuthenticationToken;
import com.whf.music.third.ThirdLogin;
import com.whf.music.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author whf
 * @date 2023/4/21
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private TokenService tokenService;

    @Resource
    private UserRemoteService userRemoteService;

    /**
     * 登录
     *
     * @param request 请求
     * @return {@code String}
     */
    @Override
    public LoginUser login(LoginRequest request) {
        // 用户验证
        Authentication authenticate = null;

        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword());
            authenticate = authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {
            log.error("login error:{}", ExceptionUtils.getExceptionInfo(e));
            throw new ServiceException("用户名或密码错误");
        }
        // 生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String token = tokenService.createToken(loginUser);
        loginUser.setToken(token);
        return loginUser;
    }

    /**
     * 注册
     *
     * @param request 请求
     * @return {@code Object}
     */
    @Override
    public Boolean register(RegisterRequest request) {
        UserRemote user = new UserRemote();
        BeanUtils.copyProperties(request, user);
        return userRemoteService.register(user);
    }

    /**
     * 第三方登录
     *
     * @param request 请求
     * @return {@code LoginUser}
     */
    @Override
    public LoginUser thirdLogin(ThirdLoginRequest request) {
        Authentication authenticate;
        try {
            //转换对象
            ThirdLogin thirdLogin = new ThirdLogin();
            BeanUtils.copyProperties(request, thirdLogin);
            //用户认证
            authenticate = authenticationManager.authenticate(new ThirdAuthenticationToken(thirdLogin));
        } catch (BadCredentialsException e) {
            throw new ServiceException("第三方登录失败");
        }
        //获取用户信息
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        //生成token
        String token = tokenService.createToken(loginUser);
        loginUser.setToken(token);
        return loginUser;
    }
}
