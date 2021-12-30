package com.zkc.mall.component;

import cn.hutool.json.JSONUtil;
import com.zkc.mall.common.api.CommonResult;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class CustomServerAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
	
	@Override
	public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
		
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(HttpStatus.OK);
		response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
		response.getHeaders().set(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
		response.getHeaders().setCacheControl("no-cache");
		String body = JSONUtil.toJsonStr(CommonResult.unauthorized(ex.getMessage()));
		DataBuffer dataBuffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
		return response.writeWith(Mono.just(dataBuffer));
	}
}
