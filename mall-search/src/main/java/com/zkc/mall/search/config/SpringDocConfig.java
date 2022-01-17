package com.zkc.mall.search.config;

import com.zkc.mall.common.config.BaseSpringDocConfig;
import com.zkc.mall.common.domain.SwaggerProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig extends BaseSpringDocConfig {
	
	@Override
	public SwaggerProperties swaggerProperties() {
		return SwaggerProperties.builder()
				.apiBasePackage("com.zkc.mall.search.controller")
				.title("mall搜索系统")
				.description("mall搜索相关接口文档")
				.version("1.0")
				.enableSecurity(true)
				.build();
	}
}
