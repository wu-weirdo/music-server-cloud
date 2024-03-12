package com.whf.music.service.impl;

import com.whf.music.entity.LoginUser;
import com.whf.music.enums.ResultEnum;
import com.whf.music.excepetion.ServiceException;
import com.whf.music.model.domain.User;
import com.whf.music.model.request.LoginRequest;
import com.whf.music.model.request.RegisterRequest;
import com.whf.music.model.request.ThirdLoginRequest;
import com.whf.music.service.LoginService;
import com.whf.music.service.TokenService;
import com.whf.music.service.UserService;
import com.whf.music.third.ThirdAuthenticationToken;
import com.whf.music.third.ThirdLogin;
import com.whf.music.utils.ExceptionUtils;
import com.whf.music.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

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
    private UserService userService;

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
        User user = new User();
        BeanUtils.copyProperties(request, user);
        if (userService.existUser(request.getUserName())) {
            throw new ServiceException(ResultEnum.PARAMETER_ERROR.getCode(), "用户名已注册");
        }
        String password = SecurityUtils.encryptPassword(request.getPassword());
        user.setPassword(password);
        user.setAvator("/resource/img/avatorImages/user.jpg");
        user.setCreateTime(new Date());
        return userService.save(user);
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
