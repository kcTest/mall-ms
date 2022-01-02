package com.zkc.mall.auth.controller;

import com.zkc.mall.auth.domain.Oauth2TokenDto;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.common.constant.AuthConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.Map;

@Api(tags = "认证中心登录认证")
@RestController
@RequestMapping("/oauth")
public class AuthController {
	
	@Resource
	private TokenEndpoint tokenEndpoint;
	
	@ApiOperation("Oauth2获取token")
	@ApiImplicitParams({
			@ApiImplicitParam(name = AuthConstant.AUTH_CLIENT_ID, value = AuthConstant.AUTH_CLIENT_ID_DESC, required = true),
			@ApiImplicitParam(name = AuthConstant.AUTH_CLIENT_SECRETE, value = AuthConstant.AUTH_CLIENT_SECRETE_DESC, required = true),
			@ApiImplicitParam(name = AuthConstant.AUTH_GRANT_TYPE, value = AuthConstant.AUTH_GRANT_TYPE_DESC, required = true),
			@ApiImplicitParam(name = AuthConstant.AUTH_REFRESH_TOKEN, value = AuthConstant.AUTH_REFRESH_TOKEN_DESC),
			@ApiImplicitParam(name = AuthConstant.AUTH_USERNAME, value = AuthConstant.AUTH_USERNAME_DESC),
			@ApiImplicitParam(name = AuthConstant.AUTH_PASSWORD, value = AuthConstant.AUTH_PASSWORD_DESC)
	})
	@PostMapping("/token")
	public CommonResult<Oauth2TokenDto> postAccessToken(@ApiIgnore Principal principal, @RequestParam Map<String, String> params) throws HttpRequestMethodNotSupportedException {
		
		OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, params).getBody();
		Oauth2TokenDto oauth2TokenDto = Oauth2TokenDto.builder()
				.token(oAuth2AccessToken.getValue())
				.refreshToken(oAuth2AccessToken.getRefreshToken().getValue())
				.tokenHead(AuthConstant.JWT_TOKEN_HEADER)
				.expiresIn(oAuth2AccessToken.getExpiresIn()).build();
		
		return CommonResult.success(oauth2TokenDto);
	}
	
}
