package com.whf.music.security.service;

import com.whf.music.dubbo.UserRemoteService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * @author whf
 * @date 2024/3/6
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @DubboReference
    private UserRemoteService userRemoteService;
    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRemoteService.loadUserByUsername(username);
    }
}
