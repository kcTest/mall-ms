package com.zkc.mall.admin.controller;

import cn.hutool.core.collection.CollUtil;
import com.zkc.mall.admin.dto.UmsAdminLoginParam;
import com.zkc.mall.admin.dto.UmsAdminRegistryParam;
import com.zkc.mall.admin.dto.UpdatePasswordParam;
import com.zkc.mall.admin.service.UmsAdminService;
import com.zkc.mall.admin.service.UmsRoleService;
import com.zkc.mall.common.api.CommonPage;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.common.domain.UserDto;
import com.zkc.mall.mbg.model.UmsAdmin;
import com.zkc.mall.mbg.model.UmsRole;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Tag(name = "UmsAdminController", description = "后台用户管理")
@RequestMapping(value = "/admin")
@CrossOrigin
@RestController
public class UmsAdminController {
	
	@Autowired
	private UmsAdminService adminService;
	@Autowired
	private UmsRoleService roleService;
	
	@Operation(summary ="用户注册")
	@PostMapping("/registry")
	@ResponseBody
	public CommonResult<UmsAdmin> register(@Validated @RequestBody UmsAdminRegistryParam umsAdminRegistryParam) {
		UmsAdmin umsAdmin = adminService.registry(umsAdminRegistryParam.getUsername(), umsAdminRegistryParam.getPassword());
		if (umsAdmin == null) {
			return CommonResult.failed();
		}
		return CommonResult.success(umsAdmin);
	}
	
	@Operation(summary ="登录后返回token")
	@PostMapping("/login")
	@ResponseBody
	public CommonResult<?> login(@Validated @RequestBody UmsAdminLoginParam umsAdminLoginParam) {
		return adminService.login(umsAdminLoginParam.getUsername(), umsAdminLoginParam.getPassword());
	}
	
	@Operation(summary ="获取当前登录用户信息")
	@GetMapping("/info")
	@ResponseBody
	public CommonResult<?> getAdminInfo() {
		
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
	
	@Operation(summary ="登出")
	@PostMapping(value = "/logout")
	@ResponseBody
	public CommonResult<?> logout() {
		return CommonResult.success(null);
	}
	
	@Operation(summary ="根据用户名或姓名分页获取用户列表")
	@GetMapping("/list")
	@ResponseBody
	public CommonResult<CommonPage<UmsAdmin>> list(@RequestParam(value = "keyword", required = false) String keyword,
												   @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
												   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
		List<UmsAdmin> adminList = adminService.list(keyword, pageSize, pageNum);
		return CommonResult.success(CommonPage.restPage(adminList));
	}
	
	@Operation(summary ="获取指定用户信息")
	@GetMapping("/{id}")
	@ResponseBody
	public CommonResult<UmsAdmin> getItem(@PathVariable Long id) {
		UmsAdmin admin = adminService.getItem(id);
		return CommonResult.success(admin);
	}
	
	@Operation(summary ="修改指定用户的信息")
	@PostMapping("/update/{id}")
	@ResponseBody
	public CommonResult<?> update(@PathVariable Long id, @RequestBody UmsAdmin admin) {
		int count = adminService.update(id, admin);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@Operation(summary ="修改指定用户密码")
	@PostMapping("/updatePassword")
	@ResponseBody
	public CommonResult<?> updatePassword(@RequestBody UpdatePasswordParam param) {
		int status = adminService.updatePassword(param);
		if (status > 0) {
			return CommonResult.success(status);
		}
		switch (status) {
			case -1:
				return CommonResult.failed("提交参数不合法");
			case -2:
				return CommonResult.failed("找不到该用户");
			case -3:
				return CommonResult.failed("旧密码错误");
			default:
				return CommonResult.failed();
		}
	}
	
	@Operation(summary ="删除指定用户信息")
	@PostMapping("/delete/{id}")
	@ResponseBody
	public CommonResult<?> delete(@PathVariable Long id) {
		int count = adminService.delete(id);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@Operation(summary ="修改账户状态")
	@PostMapping("/updateStatus/{id}")
	@ResponseBody
	public CommonResult<?> updateStatus(@PathVariable Long id, @RequestParam("status") Integer status) {
		UmsAdmin umsAdmin = new UmsAdmin();
		umsAdmin.setStatus(status);
		int count = adminService.update(id, umsAdmin);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@Operation(summary ="给用户分配角色")
	@PostMapping("/role/update")
	@ResponseBody
	public CommonResult<?> updateRole(@RequestParam("adminId") Long adminId,
									  @RequestParam("roleIds") List<Long> roleIds) {
		int count = adminService.updateRole(adminId, roleIds);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@Operation(summary ="获取指定用户角色")
	@GetMapping("/role/{adminId}")
	@ResponseBody
	public CommonResult<List<UmsRole>> getRoleList(@PathVariable Long adminId) {
		List<UmsRole> roleList = adminService.getRoleList(adminId);
		return CommonResult.success(roleList);
	}
	
	@Operation(summary ="根据用户名获取通用用户信息")
	@GetMapping(value = "/loadByUsername")
	@ResponseBody
	public UserDto loadByUsername(@RequestParam String username) {
		return adminService.loadUserByUsername(username);
	}
	
}
