package com.whf.music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whf.music.model.domain.ThirdLoginConfig;
import com.whf.music.model.domain.ThirdUser;
import me.zhyd.oauth.request.AuthRequest;

/**
 * @author whf
 * @date 2024/3/11
 */
public interface ThirdLoginConfigService extends IService<ThirdLoginConfig> {

    /**
     * 根据类型，获取授权请求
     *
     * @param openType 第三方登录类型
     */
    AuthRequest getAuthRequest(String openType);
}
