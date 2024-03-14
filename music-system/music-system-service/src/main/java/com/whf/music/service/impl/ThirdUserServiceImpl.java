package com.whf.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whf.music.mapper.ThirdUserMapper;
import com.whf.music.domain.ThirdUser;
import com.whf.music.service.ThirdUserService;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthUser;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 第三方登录用户
 *
 * @author whf
 * @date 2024/3/11
 */
@Service
@Slf4j
public class ThirdUserServiceImpl extends ServiceImpl<ThirdUserMapper, ThirdUser> implements ThirdUserService {

    /**
     * 通过第三方登录类型和第三方登录id获取用户id
     *
     * @param openType
     * @param openId
     * @return
     */
    @Override
    public Long getUserIdByOpenTypeAndOpenId(String openType, String openId) {
        LambdaQueryWrapper<ThirdUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ThirdUser::getOpenType, openType).eq(ThirdUser::getOpenId, openId);
        ThirdUser thirdUser = baseMapper.selectOne(wrapper);
        if (Objects.isNull(thirdUser)) {
            log.warn("三方用户不存在");
            return null;
        }
        return thirdUser.getUserId();
    }

    /**
     * 保存第三方用户信息
     *
     * @param userId
     * @param openType
     * @param authUser
     */
    @Override
    public void saveByAuthUser(Long userId, String openType, AuthUser authUser) {
        ThirdUser thirdUser = new ThirdUser();
        thirdUser.setOpenType(openType);
        thirdUser.setOpenId(authUser.getUuid());
        thirdUser.setUsername(authUser.getUsername());
        thirdUser.setUserId(userId);
        baseMapper.insert(thirdUser);
    }
}
