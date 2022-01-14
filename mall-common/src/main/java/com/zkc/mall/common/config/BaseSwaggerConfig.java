package com.zkc.mall.common.config;

import com.zkc.mall.common.domain.SwaggerProperties;
import io.swagger.models.auth.In;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 * swagger基础配置
 */
public abstract class BaseSwaggerConfig {
	
	@Bean
	public Docket createRestApi() {
		
		SwaggerProperties swaggerProperties = swaggerProperties();
		
		Docket docket = new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo(swaggerProperties))
				.select()
				.apis(RequestHandlerSelectors.basePackage(swaggerProperties.getApiBasePackage()))
				.paths(PathSelectors.any())
				.build();
		
		if (swaggerProperties.isEnableSecurity()) {
			docket.securitySchemes(securitySchemes()).securityContexts(securityContext());
		}
		
		return docket;
	}
	
	/**
	 * 自定义swagger配置信息
	 */
	public abstract SwaggerProperties swaggerProperties();
	
	private ApiInfo apiInfo(SwaggerProperties swaggerProperties) {
		return new ApiInfoBuilder()
				.title(swaggerProperties.getTitle())
				.description(swaggerProperties.getDescription())
				.build();
	}
	
	private List<SecurityScheme> securitySchemes() {
		//设置请求头信息
		List<SecurityScheme> result = new ArrayList<>();
		
		ApiKey apiKey = new ApiKey(HttpHeaders.AUTHORIZATION, HttpHeaders.AUTHORIZATION, In.HEADER.name());
		
		result.add(apiKey);
		return result;
	}
	
	private List<SecurityContext> securityContext() {
		//设置需要登录认证的路径
		List<SecurityContext> result = new ArrayList<>();
		result.add(getContextPath("/*/.*"));
		return result;
	}
	
	private SecurityContext getContextPath(String pathRegx) {
		return SecurityContext.builder()
				.securityReferences(defaultAuth())
				.operationSelector(o -> o.requestMappingPattern().matches(pathRegx))
				.build();
	}
	
	private List<SecurityReference> defaultAuth() {
		List<SecurityReference> result = new ArrayList<>();
		
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEveryThing");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		
		result.add(new SecurityReference("Authorization", authorizationScopes));
		
		return result;
	}
	
	
}
