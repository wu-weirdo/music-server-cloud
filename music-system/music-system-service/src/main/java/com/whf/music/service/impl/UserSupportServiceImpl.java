package com.whf.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whf.music.mapper.UserSupportMapper;
import com.whf.music.domain.UserSupport;
import com.whf.music.request.UserSupportRequest;
import com.whf.music.service.UserSupportService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author asus
 * @description 针对表【user_support】的数据库操作Service实现
 * @createDate 2022-06-11 16:06:28
 */
@Service
public class UserSupportServiceImpl extends ServiceImpl<UserSupportMapper, UserSupport>
        implements UserSupportService {

    @Autowired
    private UserSupportMapper userSupportMapper;

    @Override
    public Boolean isUserSupportComment(UserSupportRequest userSupportRequest) {
        QueryWrapper<UserSupport> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("comment_id", userSupportRequest.getCommentId());
        queryWrapper.eq("user_id", userSupportRequest.getUserId());
        return userSupportMapper.selectCount(queryWrapper) > 0;
    }

    @Override
    public Boolean insertCommentSupport(UserSupportRequest userSupportRequest) {
        UserSupport userSupport = new UserSupport();
        BeanUtils.copyProperties(userSupportRequest, userSupport);
        return userSupportMapper.insert(userSupport) > 0;
    }

    @Override
    public Boolean deleteCommentSupport(UserSupportRequest userSupportRequest) {
        QueryWrapper<UserSupport> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("comment_id", userSupportRequest.getCommentId());
        queryWrapper.eq("user_id", userSupportRequest.getUserId());
       return userSupportMapper.delete(queryWrapper) > 0;
    }
}
