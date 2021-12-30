package com.zkc.mall.config;

import cn.hutool.core.util.ArrayUtil;
import com.zkc.mall.common.constant.AuthConstant;
import com.zkc.mall.component.CustomAccessDeniedHandler;
import com.zkc.mall.component.CustomAuthorizationManager;
import com.zkc.mall.component.CustomServerAuthenticationEntryPoint;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Configuration
@EnableWebFluxSecurity
public class ResourceServerConfig {
	
	private IgnoreUrlConfig ignoreUrlConfig;
	private CustomAuthorizationManager authorizationManager;
	private CustomAccessDeniedHandler accessDeniedHandler;
	private CustomServerAuthenticationEntryPoint authenticationEntryPoint;
	
	@Bean
	public SecurityWebFilterChain springSecurityWebFilterChan(ServerHttpSecurity http) {
		
		http.oauth2ResourceServer(oauth2 -> oauth2
						.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
//				.authenticationEntryPoint(authenticationEntryPoint)
		);
		
		
		return http
				.authorizeExchange()
				.pathMatchers(ArrayUtil.toArray(ignoreUrlConfig.getUrls(), String.class)).permitAll()
				.anyExchange().access(authorizationManager)
				
				.and()
				.exceptionHandling()
				.authenticationEntryPoint(authenticationEntryPoint)
				.accessDeniedHandler(accessDeniedHandler)
				
				.and()
				.csrf()
				.disable()
				
				.build();
	}
	
	
	@Bean
	public Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
		
		JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
		
		JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
		jwtGrantedAuthoritiesConverter.setAuthorityPrefix(AuthConstant.AUTHORITY_PREFIX);
		jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName(AuthConstant.AUTHORITY_CLAIM_NAME);
		
		converter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
		
		return new ReactiveJwtAuthenticationConverterAdapter(converter);
	}
}
