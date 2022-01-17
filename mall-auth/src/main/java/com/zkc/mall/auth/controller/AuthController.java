package com.zkc.mall.auth.controller;

import com.zkc.mall.auth.domain.Oauth2TokenDto;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.common.constant.AuthConstant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@Tag(name = "AuthController", description = "认证中心登录认证")
@CrossOrigin
@RestController
@RequestMapping("/oauth")
public class AuthController {
	
	@Autowired
	private TokenEndpoint tokenEndpoint;
	
	@Operation(summary = "Oauth2获取token")
	@Parameters({
			@Parameter(name = AuthConstant.AUTH_CLIENT_ID, description = AuthConstant.AUTH_CLIENT_ID_DESC, required = true),
			@Parameter(name = AuthConstant.AUTH_CLIENT_SECRET, description = AuthConstant.AUTH_CLIENT_SECRET_DESC, required = true),
			@Parameter(name = AuthConstant.AUTH_GRANT_TYPE, description = AuthConstant.AUTH_GRANT_TYPE_DESC, required = true),
			@Parameter(name = AuthConstant.AUTH_REFRESH_TOKEN, description = AuthConstant.AUTH_REFRESH_TOKEN_DESC),
			@Parameter(name = AuthConstant.AUTH_USERNAME, description = AuthConstant.AUTH_USERNAME_DESC),
			@Parameter(name = AuthConstant.AUTH_PASSWORD, description = AuthConstant.AUTH_PASSWORD_DESC)
	})
	@PostMapping("/token")
	public CommonResult<Oauth2TokenDto> postAccessToken(@Parameter(hidden = true) Principal principal, @Parameter(hidden = true) @RequestParam Map<String, String> params) throws HttpRequestMethodNotSupportedException {
		
		OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, params).getBody();
		Oauth2TokenDto oauth2TokenDto = Oauth2TokenDto.builder()
				.token(oAuth2AccessToken.getValue())
				.refreshToken(oAuth2AccessToken.getRefreshToken().getValue())
				.tokenHead(AuthConstant.JWT_TOKEN_HEADER)
				.expiresIn(oAuth2AccessToken.getExpiresIn()).build();
		
		return CommonResult.success(oauth2TokenDto);
	}
	
}
