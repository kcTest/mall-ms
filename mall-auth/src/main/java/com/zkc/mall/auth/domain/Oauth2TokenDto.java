package com.zkc.mall.auth.domain;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Oauth2TokenDto {
	
	@Schema(description = "访问令牌")
	private String token;	@Schema(description = "刷新令牌")
	private String refreshToken;
	
	@Schema(description = "访问令牌前缀")
	private String tokenHead;
	@Schema(description = "有效时间（秒）")
	private int expiresIn;
}
