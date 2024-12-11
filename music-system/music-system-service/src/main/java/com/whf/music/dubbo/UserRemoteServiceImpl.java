package com.whf.music.dubbo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.whf.music.convert.UserConvert;
import com.whf.music.domain.User;
import com.whf.music.domin.UserRemote;
import com.whf.music.entity.LoginUser;
import com.whf.music.enums.ResultEnum;
import com.whf.music.excepetion.ServiceException;
import com.whf.music.service.UserService;
import com.whf.music.utils.SecurityUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;

/**
 * @author whf
 * @date 2024/12/11
 */
@Service
@DubboService
public class UserRemoteServiceImpl implements UserRemoteService {

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
        loginUser.setUsername(user.getUserName());
        loginUser.setPermissionList(Collections.singletonList("admin"));
        return loginUser;
    }

    /**
     * 注册
     *
     * @param userRemote 用户
     * @return {@link Boolean}
     */
    @Override
    public Boolean register(UserRemote userRemote) {
        User user = UserConvert.INSTANCE.RemoteConvert(userRemote);
        if (userService.existUser(user.getUserName())) {
            throw new ServiceException(ResultEnum.PARAMETER_ERROR.getCode(), "用户名已注册");
        }
        String password = SecurityUtils.encryptPassword(user.getPassword());
        user.setPassword(password);
        user.setAvator("/resource/img/avatorImages/user.jpg");
        user.setCreateTime(new Date());
        return userService.save(user);
    }
}
