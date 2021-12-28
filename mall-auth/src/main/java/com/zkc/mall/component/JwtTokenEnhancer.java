package com.zkc.mall.component;

import com.zkc.mall.domain.CustomUserDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenEnhancer implements TokenEnhancer {
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		
		DefaultOAuth2AccessToken defaultOAuth2AccessToken = (DefaultOAuth2AccessToken) accessToken;
		
		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
		Map<String, Object> info = new HashMap<>();
		info.put("id", customUserDetails.getId());
		info.put("client_id", customUserDetails.getClientId());
		
		defaultOAuth2AccessToken.setAdditionalInformation(info);
		
		return defaultOAuth2AccessToken;
	}
}
