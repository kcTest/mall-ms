package com.zkc.mall.config;

import cn.hutool.core.util.ArrayUtil;
import com.zkc.mall.component.CustomAuthorizationManager;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@AllArgsConstructor
@Configuration
@EnableWebFluxSecurity
public class ResourceServerConfig {
	
	private IgnoreUrlConfig ignoreUrlConfig;
	private CustomAuthorizationManager authorizationManager;
	
	@Bean
	public SecurityWebFilterChain springSecurityWebFilterChan(ServerHttpSecurity http) {
		
		http.authorizeExchange()
				.pathMatchers(ArrayUtil.toArray(ignoreUrlConfig.getUrls(), String.class)).permitAll()
				.anyExchange().access(authorizationManager);
		
		return null;
	}
}
