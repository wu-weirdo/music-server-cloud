package com.whf.music.third;

import me.zhyd.oauth.model.AuthUser;

/**
 * 第三方登录服务获取唯一标识
 * @author whf
 * @date 2024/3/11
 */
public interface ThirdOpenIdService {

    AuthUser getOpenId(ThirdLogin login);

}
