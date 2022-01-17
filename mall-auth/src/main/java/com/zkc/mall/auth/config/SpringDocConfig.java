package com.zkc.mall.auth.config;

import com.zkc.mall.common.config.BaseSpringDocConfig;
import com.zkc.mall.common.domain.SwaggerProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig extends BaseSpringDocConfig {
	
	@Override
	public SwaggerProperties swaggerProperties() {
		return SwaggerProperties.builder()
				.apiBasePackage("com.zkc.mall.auth.controller")
				.title("mall认证中心")
				.description("mall认证中心相关接口文档")
				.enableSecurity(true)
				.version("1.0")
				.build();
	}
}
