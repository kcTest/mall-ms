package com.zkc.mall.gateway.filter;


import cn.hutool.core.util.StrUtil;
import com.zkc.mall.common.constant.AuthConstant;
import com.zkc.mall.gateway.config.IgnoreUrlConfig;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Component
public class IgnoreUrlRemoveJwtFilter implements WebFilter {
	
	@Autowired
	private IgnoreUrlConfig ignoreUrlConfig;
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		
		ServerHttpRequest request = exchange.getRequest();
		String requestPath = request.getURI().getPath();
		PathMatcher matcher = new AntPathMatcher();
		List<String> urls = ignoreUrlConfig.getUrls();
		for (String url : urls) {
			if (matcher.match(url, requestPath)) {
				request = request.mutate().header(AuthConstant.JWT_TOKEN_HEADER, StrUtil.EMPTY).build();
				exchange = exchange.mutate().request(request).build();
				return chain.filter(exchange);
			}
		}
		return chain.filter(exchange);
	}
}
