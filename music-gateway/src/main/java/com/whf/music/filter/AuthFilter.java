package com.whf.music.filter;

import com.alibaba.fastjson.JSONObject;
import com.whf.music.common.R;
import com.whf.music.properties.ApiProperties;
import com.whf.music.utils.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import jodd.util.StringPool;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Component
public class AuthFilter implements GlobalFilter, Ordered {

    @Resource
    private ApiProperties apiProperties;

    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        //　如果在忽略的url里，则跳过
        String path = exchange.getRequest().getURI().getPath();
        path = path.substring(path.indexOf(StringPool.SLASH, 1));
        String requestUrl = exchange.getRequest().getURI().getRawPath();
        if (ignore(path) || ignore(requestUrl)) {
            return chain.filter(exchange);
        }
        //判断是否已登录
        String accessToken = getLoginUser(request);
        if (Objects.nonNull(accessToken)) {
            //如果存在 判断是否已过期
            String user = redisTemplate.opsForValue().get(ApiProperties.LOGIN_TOKEN_KEY + accessToken);
            if (StringUtils.isEmpty(user)) {
                return unauthorized(response, "登录超时，请重新登录");
            }
        } else {
            //不存在则登录
            return unauthorized(response, "未登录");
        }
        return chain.filter(exchange);
    }

    /**
     * 检查是否忽略url
     * @param path 路径
     * @return boolean
     */
    private boolean ignore(String path) {
        return apiProperties.getIgnoreUrls().stream()
                .map(url -> url.replace("/**", ""))
                .anyMatch(path::startsWith);
    }

    public String getLoginUser(ServerHttpRequest request) {
        String accessToken = null;
        List<String> accessTokens = request.getHeaders().get(apiProperties.getHeader());
        if (CollectionUtils.isEmpty(accessTokens)) {
            return null;
        } else {
            accessToken = accessTokens.get(0);
        }
        if (StringUtils.isBlank(accessToken)) {
            return null;
        }
        Claims claims = jwtTokenUtil.getClaimsFromToken(accessToken);
        String token = (String) claims.get(ApiProperties.LOGIN_USER_KEY);
        if (StringUtils.isBlank(token)) {
            return null;
        }
        return token;
    }


    private Mono<Void> unauthorized(ServerHttpResponse resp, String msg) {
        R<String> result = R.error(HttpStatus.UNAUTHORIZED.value(), msg);
        resp.setStatusCode(HttpStatus.UNAUTHORIZED);
        resp.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        DataBuffer dataBuffer = resp.bufferFactory().wrap(JSONObject.toJSONString(result).getBytes());
        return resp.writeWith(Mono.just(dataBuffer));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
