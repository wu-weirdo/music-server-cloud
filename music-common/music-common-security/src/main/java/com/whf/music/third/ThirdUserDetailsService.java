package com.whf.music.third;

import me.zhyd.oauth.model.AuthUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 第三方登录
 *
 * @author whf
 * @date 2024/03/11
 */
public interface ThirdUserDetailsService {

    /**
     * 通过开放平台类型和唯一标识，加载用户信息
     *
     * @param openType 开放平台类型
     * @param authUser 三方用户信息
     * @return 用户信息
     * @throws UsernameNotFoundException 不存在异常
     */
    UserDetails loadUserByOpenTypeAndOpenId(String openType, AuthUser authUser) throws UsernameNotFoundException;
}
