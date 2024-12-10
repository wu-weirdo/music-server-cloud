package com.whf.music.config;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class SentinelConfig {

    @PostConstruct
    public void init() {
        BlockRequestHandler blockRequestHandler = (serverWebExchange, throwable) -> {
            Map<String, Object> result = new HashMap<>();
            if (throwable instanceof FlowException) {
                result.put("code", 100001);
                result.put("msg", "接口已被限流");
            }
            if (throwable instanceof DegradeException) {
                result.put("code", 100002);
                result.put("msg", "服务已被降级");
            }
            if (throwable instanceof ParamFlowException) {
                result.put("code", 100003);
                result.put("msg", "参数限流");
            }
            if (throwable instanceof SystemBlockException) {
                result.put("code", 100004);
                result.put("msg", "触发系统保护规则");
            }
            if (throwable instanceof AuthorityException) {
                result.put("code", 100004);
                result.put("msg", "未被授权,请稍后再试");
            }
            return ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(result));
        };
        GatewayCallbackManager.setBlockHandler(blockRequestHandler);
    }
}
