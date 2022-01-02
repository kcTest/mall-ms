package com.zkc.mall.admin.controller;

import cn.hutool.core.collection.CollUtil;
import com.zkc.mall.admin.dto.UmsAdminLoginParam;
import com.zkc.mall.admin.dto.UmsAdminRegistryParam;
import com.zkc.mall.admin.service.UmsAdminService;
import com.zkc.mall.admin.service.UmsRoleService;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.common.domain.UserDto;
import com.zkc.mall.mbg.model.UmsAdmin;
import com.zkc.mall.mbg.model.UmsRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Api(tags = "后台用户管理")
@RequestMapping(value = "/admin")
@RestController
public class UmsAdminController {
	
	@Resource
	private UmsAdminService adminService;
	@Resource
	private UmsRoleService roleService;
	
	@ApiOperation("用户注册")
	@PostMapping("/registry")
	@ResponseBody
	public CommonResult<UmsAdmin> register(@Validated @RequestBody UmsAdminRegistryParam umsAdminRegistryParam) {
		UmsAdmin umsAdmin = adminService.registry(umsAdminRegistryParam.getUsername(), umsAdminRegistryParam.getPassword());
		if (umsAdmin == null) {
			return CommonResult.failed();
		}
		return CommonResult.success(umsAdmin);
	}
	
	@ApiOperation("登录后返回token")
	@PostMapping("/login")
	@ResponseBody
	public CommonResult login(@Validated @RequestBody UmsAdminLoginParam umsAdminLoginParam) {
		return adminService.login(umsAdminLoginParam.getUsername(), umsAdminLoginParam.getPassword());
	}
	
	@ApiOperation("获取当前登录用户信息")
	@GetMapping("/info")
	@ResponseBody
	public CommonResult getAdminInfo() {
		
		UmsAdmin currentAdmin = adminService.getCurrentAdmin();
		Map<String, Object> data = new HashMap<>(4);
		data.put("username", currentAdmin.getUsername());
		data.put("menus", roleService.getMenuList(currentAdmin.getId()));
		data.put("icon", currentAdmin.getIcon());
		List<UmsRole> roleList = adminService.getRoleList(currentAdmin.getId());
		if (CollUtil.isNotEmpty(roleList)) {
			List<String> roles = roleList.stream().map(UmsRole::getName).collect(Collectors.toList());
			data.put("roles", roles);
		}
		return CommonResult.success(data);
	}
	
	
	@ApiOperation("根据用户名获取通用用户信息")
	@GetMapping(value = "/loadByUsername")
	@ResponseBody
	public UserDto loadByUsername(@RequestParam String username) {
		UserDto userDto = adminService.loadUserByUsername(username);
		return userDto;
	}
	
	@ApiOperation("登出")
	@PostMapping(value = "/logout")
	@ResponseBody
	public CommonResult logout() {
		return CommonResult.success(null);
	}
	
}
