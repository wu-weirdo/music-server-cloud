package com.whf.music.filter;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class AuthFilter implements GlobalFilter, Ordered {

    /**
     * 令牌自定义标识
     */
    @Value("${jwt.header:Authorization}")
    private String header;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        //判断是否已登录
        String accessToken = getAccessToken(request);
        if (Objects.nonNull(accessToken)) {
            //如果存在.....
        } else {
            //不存在则登录
            return unauthorized(response, "未登录");
        }
        return chain.filter(exchange);
    }

    public String getAccessToken(ServerHttpRequest request) {
        String accessToken = null;
        List<String> accessTokens = request.getHeaders().get(header);
        if (CollectionUtils.isEmpty(accessTokens)) {
            return null;
        } else {
            accessToken = accessTokens.get(0);
        }
        if (StringUtils.isBlank(accessToken)) {
            return null;
        }
        return accessToken;
    }


    private Mono<Void> unauthorized(ServerHttpResponse resp, String msg) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", HttpStatus.UNAUTHORIZED.value());
        result.put("msg", msg);
        resp.setStatusCode(HttpStatus.UNAUTHORIZED);
        resp.getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
        DataBuffer dataBuffer = resp.bufferFactory().wrap(JSONObject.toJSONString(result).getBytes());
        return resp.writeWith(Mono.just(dataBuffer));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
