package com.zkc.mall.component;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.nimbusds.jose.JWSObject;
import com.zkc.mall.common.constant.AuthConstant;
import com.zkc.mall.common.domain.UserDto;
import com.zkc.mall.config.IgnoreUrlConfig;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

@Component
public class CustomAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
	
	@Resource
	private IgnoreUrlConfig ignoreUrlConfig;
	
	@Override
	public Mono<AuthorizationDecision> check(Mono authentication, AuthorizationContext authorizationContext) {
		
		ServerHttpRequest request = authorizationContext.getExchange().getRequest();
		String requestPath = request.getURI().getPath();
		AntPathMatcher pathMatcher = new AntPathMatcher();
		
		List<String> ignoreUrls = ignoreUrlConfig.getUrls();
		for (String ignoreUrl : ignoreUrls) {
			if (pathMatcher.match(ignoreUrl, requestPath)) {
				return Mono.just(new AuthorizationDecision(true));
			}
		}
		
		if (request.getMethod() == HttpMethod.OPTIONS) {
			return Mono.just(new AuthorizationDecision(true));
		}
		
		String token = request.getHeaders().getFirst(AuthConstant.JWT_TOKEN_HEADER);
		if (StrUtil.isEmpty(token)) {
			return Mono.just(new AuthorizationDecision(false));
		}
		String realToken = token.replace(AuthConstant.JWT_TOKEN_PREFIX, StrUtil.EMPTY);
		JWSObject jwsObject;
		try {
			jwsObject = JWSObject.parse(realToken);
		} catch (ParseException e) {
			e.printStackTrace();
			return Mono.just(new AuthorizationDecision(false));
		}
		String userStr = jwsObject.getPayload().toString();
		UserDto userDto = JSONUtil.toBean(userStr, UserDto.class);
		if (userDto.getClientId().equals(AuthConstant.ADMIN_CLIENT_ID) && !pathMatcher.match(AuthConstant.ADMIN_URL_PATTERN, requestPath)) {
			return Mono.just(new AuthorizationDecision(false));
		}
		if (userDto.getClientId().equals(AuthConstant.PORTAL_CLIENT_ID) && pathMatcher.match(AuthConstant.ADMIN_URL_PATTERN, requestPath)) {
			return Mono.just(new AuthorizationDecision(false));
		}
		
		if (!pathMatcher.match(AuthConstant.ADMIN_URL_PATTERN, requestPath)) {
			return Mono.just(new AuthorizationDecision(true));
		}
		
		
		return null;
	}
}
