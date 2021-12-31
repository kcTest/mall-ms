package com.zkc.mall.admin.controller;

import com.zkc.mall.admin.service.UmsAdminService;
import com.zkc.mall.common.domain.UserDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UmsAdminController {
	
	@Resource
	private UmsAdminService adminService;
	
	
	@RequestMapping(value = "/loadByUsername")
	@ResponseBody
	public UserDto loadByUsername(@RequestParam String username) {
		
		return null;
	}
}
