package com.zkc.mall.portal.controller;

import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.common.domain.UserDto;
import com.zkc.mall.mbg.model.UmsMember;
import com.zkc.mall.portal.service.UmsMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
@Tag(name = "UmsMemberController", description = "会员登录注册管理")
@CrossOrigin
@RestController
@RequestMapping("/sso")
public class UmsMemberController {
	
	@Autowired
	private UmsMemberService memberService;
	
	@Operation(summary ="会员注册")
	@PostMapping("/registry")
	@ResponseBody
	public CommonResult<?> add(@RequestParam("username") String username,
							   @RequestParam("password") String password,
							   @RequestParam("telephone") String telephone,
							   @RequestParam("authCode") String authCode) {
		memberService.register(username, password, telephone, authCode);
		return CommonResult.success(null, "注册成功");
	}
	
	@Operation(summary ="会员登录")
	@PostMapping("/login")
	@ResponseBody
	public CommonResult<?> add(@RequestParam("username") String username,
							   @RequestParam("password") String password) {
		return memberService.login(username, password);
	}
	
	@Operation(summary ="获取会员信息")
	@GetMapping("/info")
	@ResponseBody
	public CommonResult<UmsMember> info(@RequestParam("username") String username,
										@RequestParam("password") String password) {
		UmsMember member = memberService.getCurrentMember();
		return CommonResult.success(member);
	}
	
	@Operation(summary ="获取验证码")
	@GetMapping("/getAuthCode")
	@ResponseBody
	public CommonResult<?> getAuthCode(@RequestParam("telephone") String telephone) {
		String authCode = memberService.generateAuthCode(telephone);
		return CommonResult.success(authCode, "获取验证码成功");
	}
	
	@Operation(summary ="修改密码")
	@PostMapping("/updatePassword")
	@ResponseBody
	public CommonResult<?> updatePassword(@RequestParam("telephone") String telephone,
										  @RequestParam("password") String password,
										  @RequestParam("authCode") String authCode) {
		memberService.updatePassword(telephone, password, authCode);
		return CommonResult.success(null, "密码修改成功");
	}
	
	@Operation(summary ="根据用户名获取通用用户信息")
	@GetMapping("/loadByUsername")
	@ResponseBody
	public UserDto loadByUsername(@RequestParam("username") String username) {
		return memberService.loadByUsername(username);
	}
	
}
