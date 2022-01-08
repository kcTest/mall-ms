package com.zkc.mall.monitor.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class SecuritySecureConfig extends WebSecurityConfigurerAdapter {
	
	private final String adminContextPath;
	
	public SecuritySecureConfig(AdminServerProperties adminServerProperties) {
		this.adminContextPath = adminServerProperties.getContextPath();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
		successHandler.setTargetUrlParameter("redirectTo");
		successHandler.setDefaultTargetUrl(adminContextPath + "/");
		
		http.authorizeRequests()
				//静态资源和等领页可公开访问
				.antMatchers(adminContextPath + "/assets/**").permitAll()
				.antMatchers(adminContextPath + "/login").permitAll()
				.anyRequest().authenticated()
				//登录登出页面
				.and()
				.formLogin().loginPage(adminContextPath + "/login").successHandler(successHandler)
				.and().logout().logoutUrl(adminContextPath + "/logout")
				.and()
				// admin-clients注册时使用
				.httpBasic()
				.and()
				//基于cookie的csrf保护
				.csrf()
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
				//忽略
				.ignoringAntMatchers(adminContextPath + "/instances", adminContextPath + "/actuator/**");
	}
}
