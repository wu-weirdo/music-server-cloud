package com.whf.music.service;

import com.whf.music.entity.LoginUser;
import com.whf.music.utils.IdUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
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

    /**
     * 令牌自定义标识
     */
    @Value("${jwt.header}")
    private String header;

    /**
     * 令牌秘钥
     */
    @Value("${jwt.secret}")
    private String secret;

    /**
     * 令牌有效期（默认30分钟）
     */
    @Value("${jwt.expiration}")
    private int expiration;

    private static final Long MILLIS_MINUTE_TWENTY = 20 * 60 * 1000L;

    /**
     * token 前缀
     */
    private static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 令牌前缀
     */
    public static final String LOGIN_USER_KEY = "login_user_key";

    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login:tokens:";

    @Resource
    private RedisTemplate<String, LoginUser> redisTemplate;

    public LoginUser getLoginUser(HttpServletRequest request) {
        String accessToken = getAccessToken(request);
        if (StringUtils.isBlank(accessToken)) {
            return null;
        }
        Claims claims = getClaimsFromToken(accessToken);
        String token = (String) claims.get(LOGIN_USER_KEY);
        if (StringUtils.isBlank(token)) {
            return null;
        }
        return redisTemplate.opsForValue().get(LOGIN_TOKEN_KEY + token);
    }

    /**
     * 删除登录用户信息
     * @param loginUser
     */
    public void deleteLoginUser(LoginUser loginUser) {
        redisTemplate.delete(LOGIN_TOKEN_KEY + loginUser.getToken());
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
        claims.put(LOGIN_USER_KEY, token);
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
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
        if (expireTime - currentTime <= MILLIS_MINUTE_TWENTY) {
            loginUser.setExpireTime(expireTime + (long) expiration * 60 * 1000);
            redisTemplate.opsForValue().set(LOGIN_TOKEN_KEY + loginUser.getToken(), loginUser, expiration, TimeUnit.MINUTES);
        }
    }

    /**
     * 获取 AccessToken
     */
    private String getAccessToken(HttpServletRequest request) {
        String accessToken = request.getHeader(header);
        if (StringUtils.isBlank(accessToken)) {
            return null;
        }
        if (accessToken.startsWith(TOKEN_PREFIX)) {
            accessToken = accessToken.substring(TOKEN_PREFIX.length());
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
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.info("JWT格式验证失败:{}", token);
        }
        return claims;
    }
}
