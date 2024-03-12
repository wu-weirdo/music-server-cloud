package com.whf.music.security.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.whf.music.convert.UserConvert;
import com.whf.music.entity.LoginUser;
import com.whf.music.model.domain.User;
import com.whf.music.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Objects;

/**
 * @author whf
 * @date 2024/3/6
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, username);
        User user = userService.getOne(queryWrapper);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("用户不存在");
        }
        LoginUser loginUser = UserConvert.INSTANCE.convert(user);
        loginUser.setPermissionList(Collections.singletonList("admin"));
        return loginUser;
    }
}
