package com.whf.music.dubbo;

import com.whf.music.domin.UserRemote;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户远程服务
 *
 * @author whf
 * @date 2024/12/11
 */
public interface UserRemoteService {

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return {@link UserDetails}
     */
    UserDetails loadUserByUsername(String username);

    /**
     * 注册
     *
     * @param user 用户
     * @return {@link Boolean}
     */
    Boolean register(UserRemote user);
}
