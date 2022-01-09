package com.zkc.mall.portal.controller;

import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.portal.service.UmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api("会员登录注册管理")
@RestController
@RequestMapping("/sso")
public class UmsMemberController {
	
	@Resource
	private UmsMemberService memberService;
	
	@ApiOperation("会员注册")
	@PostMapping("/registry")
	@ResponseBody
	public CommonResult<?> add(@RequestParam("username") String username,
							   @RequestParam("password") String password,
							   @RequestParam("telephone") String telephone,
							   @RequestParam("authCode") String authCode) {
		memberService.register(username, password, telephone, authCode);
		return CommonResult.success(null, "注册成功");
	}
	
}
