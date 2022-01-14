package com.zkc.mall.admin.config;

import com.zkc.mall.common.config.BaseSwaggerConfig;
import com.zkc.mall.common.domain.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {
	
	@Override
	public SwaggerProperties swaggerProperties() {
		return SwaggerProperties.builder()
				.apiBasePackage("com.zkc.mall.admin.controller")
				.title("mall后台管理系统")
				.description("mall后台相关接口文档")
				.version("1.0")
				.enableSecurity(true)
				.build();
	}
}
