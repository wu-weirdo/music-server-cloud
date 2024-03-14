package com.whf.music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whf.music.domain.ThirdUser;
import me.zhyd.oauth.model.AuthUser;

/**
 * @author whf
 * @date 2024/3/11
 */
public interface ThirdUserService extends IService<ThirdUser> {
    /**
     * 根据第三方登录类型和openId获取用户id
     * @param openType
     * @param openId
     * @return
     */
    Long getUserIdByOpenTypeAndOpenId(String openType, String openId);

    /**
     * 保存第三方用户信息
     * @param userId
     * @param openType
     * @param authUser
     */
    void saveByAuthUser(Long userId, String openType, AuthUser authUser);
}
