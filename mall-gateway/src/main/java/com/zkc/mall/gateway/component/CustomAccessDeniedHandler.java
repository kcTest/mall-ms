package com.zkc.mall.gateway.component;

import cn.hutool.json.JSONUtil;
import com.zkc.mall.common.api.CommonResult;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class CustomAccessDeniedHandler implements ServerAccessDeniedHandler {
	
	@Override
	public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
		
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(HttpStatus.OK);
		response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
		response.getHeaders().setAccessControlAllowOrigin("*");
		response.getHeaders().setCacheControl("no-cache");
		String body = JSONUtil.toJsonStr(CommonResult.forbidden(denied.getMessage()));
		DataBuffer dataBuffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
		return response.writeWith(Mono.just(dataBuffer));
	}
}
