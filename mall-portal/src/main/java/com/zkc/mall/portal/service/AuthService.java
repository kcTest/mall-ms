package com.zkc.mall.portal.service;

import com.zkc.mall.common.api.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * 远程服务调用
 */
@FeignClient("mall-auth")
public interface AuthService {
	
	@PostMapping("/oauth/token")
	CommonResult<?> getAccessToken(@RequestParam Map<String, String> params);
	
}
