package com.zkc.mall.auth.config;

import com.zkc.mall.auth.component.JwtTokenEnhancer;
import com.zkc.mall.auth.service.impl.UserDetailServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Configuration
@EnableAuthorizationServer
public class Oauth2ServerConfig extends AuthorizationServerConfigurerAdapter {
	
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final UserDetailServiceImpl userDetailService;
	private final JwtTokenEnhancer jwtTokenEnhancer;
	
	
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
		
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		List<TokenEnhancer> delegates = new ArrayList<>();
		delegates.add(jwtTokenEnhancer);
		delegates.add(accessTokenConverter());
		tokenEnhancerChain.setTokenEnhancers(delegates);
		
		endpoints.authenticationManager(authenticationManager)
				.userDetailsService(userDetailService)
				.accessTokenConverter(accessTokenConverter())
				.tokenStore(tokenStore()).tokenEnhancer(tokenEnhancerChain);
	}
	
	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}
	
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		
		JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
		tokenConverter.setKeyPair(getKeyPair());
		return tokenConverter;
	}
	
	@Bean
	public KeyPair getKeyPair() {
		
		char[] pwdCharArr = "123456".toCharArray();
		KeyStoreKeyFactory factory = new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), pwdCharArr);
		return factory.getKeyPair("jwt", pwdCharArr);
	}
}
