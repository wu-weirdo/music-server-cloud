package com.whf.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whf.music.enums.ThirdLoginEnum;
import com.whf.music.excepetion.ServiceException;
import com.whf.music.mapper.ThirdLoginConfigMapper;
import com.whf.music.domain.ThirdLoginConfig;
import com.whf.music.service.ThirdLoginConfigService;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.request.*;
import org.springframework.stereotype.Service;

import java.util.Objects;


/**
 * 第三方登录用户
 *
 * @author whf
 * @date 2024/3/11
 */
@Service
public class ThirdLoginConfigServiceImpl extends ServiceImpl<ThirdLoginConfigMapper, ThirdLoginConfig> implements ThirdLoginConfigService {


    /**
     * 根据类型，获取授权请求
     *
     * @param openType 第三方登录类型
     */
    @Override
    public AuthRequest getAuthRequest(String openType) {
        //获取第三方登录配置
        LambdaQueryWrapper<ThirdLoginConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ThirdLoginConfig::getOpenType, openType);
        ThirdLoginConfig thirdLoginConfig = this.getOne(wrapper);
        if (Objects.isNull(thirdLoginConfig)) {
            throw new ServiceException("第三方登录配置不存在");
        }
        switch (ThirdLoginEnum.toEnum(openType)) {
            case WECHAT_WORK:
                return new AuthWeChatEnterpriseQrcodeRequest(AuthConfig.builder()
                        .clientId(thirdLoginConfig.getClientId())
                        .clientSecret(thirdLoginConfig.getClientSecret())
                        .redirectUri(thirdLoginConfig.getRedirectUri())
                        .agentId(thirdLoginConfig.getAgentId())
                        .build());
            case DING_TALK:
                return new AuthDingTalkRequest(AuthConfig.builder()
                        .clientId(thirdLoginConfig.getClientId())
                        .clientSecret(thirdLoginConfig.getClientSecret())
                        .redirectUri(thirdLoginConfig.getRedirectUri())
                        .agentId(thirdLoginConfig.getAgentId())
                        .build());
            case FEI_SHU:
                return new AuthFeishuRequest(AuthConfig.builder()
                        .clientId(thirdLoginConfig.getClientId())
                        .clientSecret(thirdLoginConfig.getClientSecret())
                        .redirectUri(thirdLoginConfig.getRedirectUri())
                        .agentId(thirdLoginConfig.getAgentId())
                        .build());
            case WECHAT_OPEN:
                return new AuthWeChatOpenRequest(AuthConfig.builder()
                        .clientId(thirdLoginConfig.getClientId())
                        .clientSecret(thirdLoginConfig.getClientSecret())
                        .redirectUri(thirdLoginConfig.getRedirectUri())
                        .agentId(thirdLoginConfig.getAgentId())
                        .build());
        }
        return null;
    }
}
