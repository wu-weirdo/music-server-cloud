package com.whf.music.security.service;

import com.whf.music.excepetion.ServiceException;
import com.whf.music.service.ThirdLoginConfigService;
import com.whf.music.third.ThirdLogin;
import com.whf.music.third.ThirdOpenIdService;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author whf
 * @date 2024/3/12
 */
@Service
public class ThirdOpenIdServiceImpl implements ThirdOpenIdService {

    @Resource
    private ThirdLoginConfigService thirdLoginConfigService;

    @Override
    public AuthUser getOpenId(ThirdLogin login) {
        //构建openId请求参数
        AuthRequest authRequest = thirdLoginConfigService.getAuthRequest(login.getOpenType());
        AuthCallback callback = AuthCallback.builder().code(login.getCode()).state(login.getState()).build();
        // 根据code，获取用户信息
        AuthResponse<AuthUser> response = authRequest.login(callback);
        // 判断是否成功
        if (!response.ok()) {
            throw new ServiceException("第三方登录失败");
        }

        return response.getData();
    }
}
