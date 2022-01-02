package com.zkc.mall.admin.service;

import com.zkc.mall.common.api.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "mall-admin")
public interface AuthService {
	
	@PostMapping(value = "/oauth/token")
	CommonResult getAccessToken(@RequestParam Map<String, String> parameters);
}
