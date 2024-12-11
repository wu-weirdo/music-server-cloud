package com.whf.music.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Set;

/**
 * api相关配置
 *
 * @author whf
 * @date 2024/12/10
 */
@Data
@RefreshScope
@Configuration
public class ApiProperties {

    /**
     * 监控中心和swagger需要访问的url
     */
    private static final String[] ENDPOINTS = {
            "/login",
            "/logout",
            "/register",
            "/actuator/**",
            "/v3/**",
            "/v3/api-docs/**",
            "/swagger/api-docs/**",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/webjars/**",
            "/resource/**",
    };

    @Value("${ignoreUrls:[]}")
    private Set<String> ignoreUrls;

    /**
     * 令牌自定义标识
     */
    @Value("${jwt.header:Authorization}")
    private String header;

    /**
     * 令牌秘钥
     */
    @Value("${jwt.secret:abcdefghijklmnopqrstuvwxyz}")
    private String secret;

    /**
     * 令牌有效期（默认30分钟）
     */
    @Value("${jwt.expiration:30}")
    private int expiration;


    /**
     * 刷新时间间隔（默认20分钟）
     */
    @Value("${jwt.refreshTime:20}")
    private int refreshTime;

    /**
     * token 前缀
     */
    @Value("${jwt.prefix:Bearer }")
    private String prefix;

    /**
     * 令牌前缀
     */
    public static final String LOGIN_USER_KEY = "login_user_key";

    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login:tokens:";

    /**
     * 首次加载合并ENDPOINTS
     */
    @PostConstruct
    public void initIgnoreUrl() {
        Collections.addAll(ignoreUrls, ENDPOINTS);
    }
}
