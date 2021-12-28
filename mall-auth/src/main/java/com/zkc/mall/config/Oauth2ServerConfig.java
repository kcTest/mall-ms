package com.zkc.mall.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@AllArgsConstructor
@Configuration
@EnableAuthorizationServer
public class Oauth2ServerConfig extends AuthorizationServerConfigurerAdapter {
	
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) {
		security.allowFormAuthenticationForClients();
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		
		clients.inMemory()
				.withClient("admin-app").secret(passwordEncoder.encode("123456"))
				.scopes("all").authorizedGrantTypes("password", "refresh_token")
				.accessTokenValiditySeconds(3600 * 24)
				.refreshTokenValiditySeconds(3600 * 24 * 7)
				.and()
				.withClient("portal-app").secret(passwordEncoder.encode("123456"))
				.scopes("all").authorizedGrantTypes("password", "refresh_token")
				.accessTokenValiditySeconds(3600 * 24)
				.refreshTokenValiditySeconds(3600 * 24 * 7);
	}
	
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
	}
}
