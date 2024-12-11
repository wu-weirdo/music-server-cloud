package com.whf.music.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whf.music.entity.LoginUser;
import com.whf.music.enums.ResultEnum;
import com.whf.music.excepetion.ServiceException;
import com.whf.music.properties.ApiProperties;
import com.whf.music.utils.ExceptionUtils;
import com.whf.music.utils.IdUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author whf
 * @date 2024/3/5
 */
@Slf4j
@Component
public class TokenService {

    @Resource
    private ApiProperties apiProperties;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    public LoginUser getLoginUser(HttpServletRequest request) {
        String accessToken = getAccessToken(request);
        if (StringUtils.isBlank(accessToken)) {
            return null;
        }
        Claims claims = getClaimsFromToken(accessToken);
        String token = (String) claims.get(ApiProperties.LOGIN_USER_KEY);
        if (StringUtils.isBlank(token)) {
            return null;
        }
        try {
            String user = redisTemplate.opsForValue().get(ApiProperties.LOGIN_TOKEN_KEY + token);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(user, LoginUser.class);
        } catch (JsonProcessingException e) {
            log.error("getLoginUser error:{}", ExceptionUtils.getExceptionInfo(e));
            throw new ServiceException(ResultEnum.ERROR);
        }
    }

    /**
     * 删除登录用户信息
     * @param loginUser
     */
    public void deleteLoginUser(LoginUser loginUser) {
        redisTemplate.delete(ApiProperties.LOGIN_TOKEN_KEY + loginUser.getToken());
    }

    /**
     * 创建令牌
     *
     * @param loginUser 用户信息
     * @return 令牌
     */
    public String createToken(LoginUser loginUser) {
        String token = IdUtils.fastUUID();
        loginUser.setToken(token);
        refreshToken(loginUser);
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put(ApiProperties.LOGIN_USER_KEY, token);
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, apiProperties.getSecret()).compact();
    }

    /**
     * 验证令牌有效期，相差不足20分钟，自动刷新缓存
     *
     * @param loginUser 登录用户
     */
    public void refreshToken(LoginUser loginUser) {
        long currentTime = System.currentTimeMillis();
        Long expireTime = loginUser.getExpireTime();
        if (expireTime == null) {
            expireTime = 0L;
        }
        if (expireTime - currentTime <= apiProperties.getRefreshTime()) {
            loginUser.setExpireTime(expireTime + (long) apiProperties.getExpiration() * 60 * 1000);
            try {
                ObjectMapper mapper = new ObjectMapper();
                String string = mapper.writeValueAsString(loginUser);
                redisTemplate.opsForValue().set(ApiProperties.LOGIN_TOKEN_KEY + loginUser.getToken(), string, apiProperties.getExpiration(), TimeUnit.MINUTES);
            } catch (JsonProcessingException e) {
                log.error("getLoginUser error:{}", ExceptionUtils.getExceptionInfo(e));
                throw new ServiceException(ResultEnum.ERROR);
            }
        }
    }

    /**
     * 获取 AccessToken
     */
    private String getAccessToken(HttpServletRequest request) {
        String accessToken = request.getHeader(apiProperties.getHeader());
        if (StringUtils.isBlank(accessToken)) {
            return null;
        }
        if (accessToken.startsWith(apiProperties.getPrefix())) {
            accessToken = accessToken.substring(apiProperties.getPrefix().length());
        }
        return accessToken;
    }

    /**
     * 从token中获取JWT中的负载
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(apiProperties.getSecret())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.info("JWT格式验证失败:{}", token);
        }
        return claims;
    }
}
