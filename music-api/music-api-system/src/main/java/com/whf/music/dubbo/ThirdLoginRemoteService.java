package com.whf.music.dubbo;

import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 三方登录服务
 *
 * @author whf
 * @date 2024/12/11
 */
public interface ThirdLoginRemoteService {

    /**
     * 根据类型，获取授权请求
     *
     * @param openType 第三方登录类型
     */
    AuthRequest getAuthRequest(String openType);

    /**
     * 通过开放平台类型和唯一标识，加载用户信息
     *
     * @param openType 开放平台类型
     * @param authUser 三方用户信息
     * @return 用户信息
     */
    UserDetails loadUserByOpenTypeAndOpenId(String openType, AuthUser authUser);
}
