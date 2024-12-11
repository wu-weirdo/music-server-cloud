package com.whf.music.security.service;

import com.whf.music.dubbo.ThirdLoginRemoteService;
import com.whf.music.third.ThirdUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthUser;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 第三方登录
 *
 * @author whf
 * @date 2024/3/11
 */
@Service
@Slf4j
public class ThirdUserDetailsServiceImpl implements ThirdUserDetailsService {

    @DubboReference
    private ThirdLoginRemoteService thirdLoginRemoteService;

    /**
     * 通过开放平台类型和唯一标识，加载用户信息
     *
     * @param openType 开放平台类型
     * @param authUser 三方用户信息
     * @return 用户信息
     * @throws UsernameNotFoundException 不存在异常
     */
    @Override
    public UserDetails loadUserByOpenTypeAndOpenId(String openType, AuthUser authUser) throws UsernameNotFoundException {
        return thirdLoginRemoteService.loadUserByOpenTypeAndOpenId(openType, authUser);
    }
}
