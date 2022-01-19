package com.zkc.mall.auth.service;

import com.zkc.mall.common.domain.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "mall-portal")
public interface UmsMemberService {
	
	@GetMapping("/sso/loadByUsername")
	UserDto loadUserByUsername(@RequestParam String username);
}
