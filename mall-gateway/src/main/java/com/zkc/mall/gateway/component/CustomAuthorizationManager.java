package com.zkc.mall.gateway.component;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.nimbusds.jose.JWSObject;
import com.zkc.mall.common.constant.AuthConstant;
import com.zkc.mall.common.domain.UserDto;
import com.zkc.mall.gateway.config.IgnoreUrlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class CustomAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
	
	@Autowired
	private IgnoreUrlConfig ignoreUrlConfig;
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Override
	public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext authorizationContext) {
		
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
		
		//根据访问路径 获取必须拥有的角色 
		Map<Object, Object> resourceRolesMap = redisTemplate.opsForHash().entries(AuthConstant.RESOURCE_ROLES_MAP_KEY);
		Iterator<Object> iterator = resourceRolesMap.keySet().iterator();
		List<String> authorities = new ArrayList<>();
		while (iterator.hasNext()) {
			String pattern = (String) iterator.next();
			if (pathMatcher.match(pattern, requestPath)) {
				authorities.addAll(Convert.toList(String.class, resourceRolesMap.get(pattern)));
			}
		}
		authorities = authorities.stream().map(i -> i = AuthConstant.AUTHORITY_PREFIX + i).collect(Collectors.toList());
		
		//验证角色
		Mono<AuthorizationDecision> decisionMono = authentication
				.filter(a -> {
					boolean authenticated = a.isAuthenticated();
					return authenticated;
				})
				.flatMapIterable(a -> {
					Collection<? extends GrantedAuthority> grantedAuthorities = a.getAuthorities();
					return grantedAuthorities;
				})
				.map(grantedAuthority -> {
					String authority = grantedAuthority.getAuthority();
					return authority;
				})
				.any(authorities::contains)
				.map((granted) -> {
					AuthorizationDecision authorizationDecision = new AuthorizationDecision(granted);
					return authorizationDecision;
				})
				.defaultIfEmpty(new AuthorizationDecision(false));
		
		return decisionMono;
	}
}
