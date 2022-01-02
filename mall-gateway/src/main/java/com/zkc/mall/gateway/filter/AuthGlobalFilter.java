package com.zkc.mall.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.nimbusds.jose.JWSObject;
import com.zkc.mall.common.constant.AuthConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.text.ParseException;

/**
 * 将登录用户的JWT转化成用户信息
 */
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {
	
	private static Logger LOGGER = LoggerFactory.getLogger(AuthGlobalFilter.class);
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		
		String token = exchange.getRequest().getHeaders().getFirst(AuthConstant.JWT_TOKEN_HEADER);
		if (StrUtil.isEmpty(token)) {
			return chain.filter(exchange);
		}
		
		String realToken = token.replace(AuthConstant.JWT_TOKEN_PREFIX, StrUtil.EMPTY);
		try {
			JWSObject jwsObject = JWSObject.parse(realToken);
			String userInfo = jwsObject.getPayload().toString();
			LOGGER.info("AuthGlobalFilter.filter() user:{}", userInfo);
			ServerHttpRequest request = exchange.getRequest().mutate().header(AuthConstant.USER_TOKEN_HEADER, userInfo).build();
			exchange = exchange.mutate().request(request).build();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return chain.filter(exchange);
	}
	
	@Override
	public int getOrder() {
		return 0;
	}
}
