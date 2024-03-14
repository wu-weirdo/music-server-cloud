package com.whf.music.security.service;

import com.whf.music.convert.UserConvert;
import com.whf.music.entity.LoginUser;
import com.whf.music.domain.User;
import com.whf.music.service.ThirdUserService;
import com.whf.music.service.UserService;
import com.whf.music.third.ThirdUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Objects;

/**
 * 第三方登录
 *
 * @author whf
 * @date 2024/3/11
 */
@Service
@Slf4j
public class ThirdUserDetailsServiceImpl implements ThirdUserDetailsService {

    @Resource
    private ThirdUserService thirdUserService;

    @Resource
    private UserService userService;

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
        //通过类型和唯一标识，获取用户id
        Long userId = thirdUserService.getUserIdByOpenTypeAndOpenId(openType, authUser.getUuid());
        if (Objects.isNull(userId)) {
            //创建用户
            User user = userService.saveByAuthUser(authUser);
            //创建三方用户信息
            thirdUserService.saveByAuthUser(user.getId(), openType, authUser);
        }
        //根据用户id获取用户信息
        User user = userService.getById(userId);
        if (Objects.isNull(user)) {
            log.warn("用户不存在");
        }
        LoginUser loginUser = UserConvert.INSTANCE.convert(user);
        loginUser.setPermissionList(Collections.singletonList("admin"));
        return loginUser;
    }
}
