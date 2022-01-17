package com.zkc.mall.admin.controller;

import com.zkc.mall.admin.service.UmsRoleService;
import com.zkc.mall.common.api.CommonPage;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.mbg.model.UmsMenu;
import com.zkc.mall.mbg.model.UmsResource;
import com.zkc.mall.mbg.model.UmsRole;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
@Tag(name = "UmsRoleController", description = "用户后台角色管理")
@CrossOrigin
@RestController
@RequestMapping("/role")
public class UmsRoleController {
	
	@Autowired
	private UmsRoleService roleService;
	
	@Operation(summary ="添加角色")
	@PostMapping("/create")
	@ResponseBody
	public CommonResult create(@RequestBody UmsRole role) {
		int count = roleService.create(role);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@Operation(summary ="修改角色")
	@PostMapping("/update/{id}")
	@ResponseBody
	public CommonResult update(@PathVariable Long id, @RequestBody UmsRole role) {
		int count = roleService.update(id, role);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@Operation(summary ="批量删除角色")
	@PostMapping("/delete")
	@ResponseBody
	public CommonResult<?> delete(@RequestParam("ids") List<Long> ids) {
		int count = roleService.delete(ids);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@Operation(summary ="获取所有角色")
	@GetMapping("/listAll")
	@ResponseBody
	public CommonResult<List<UmsRole>> getRoleList() {
		List<UmsRole> roleList = roleService.list();
		return CommonResult.success(roleList);
	}
	
	@Operation(summary ="根据角色名称分页获取角色列表")
	@GetMapping("/list")
	@ResponseBody
	public CommonResult<CommonPage<UmsRole>> list(@RequestParam(value = "keyword", required = false) String keyword,
												  @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
												  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
		List<UmsRole> roleList = roleService.list(keyword, pageSize, pageNum);
		return CommonResult.success(CommonPage.restPage(roleList));
	}
	
	@Operation(summary ="修改角色状态")
	@PostMapping("updateStatus/{id}")
	@ResponseBody
	public CommonResult<?> updateStatus(@PathVariable Long id, @RequestParam(value = "status") Integer status) {
		UmsRole umsRole = new UmsRole();
		umsRole.setStatus(status);
		int count = roleService.update(id, umsRole);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@Operation(summary ="获取角色相关菜单")
	@GetMapping("/listMenu/{roleId}")
	@ResponseBody
	public CommonResult<List<UmsMenu>> listMenu(@PathVariable Long roleId) {
		List<UmsMenu> roleList = roleService.listMenu(roleId);
		return CommonResult.success(roleList);
	}
	
	@Operation(summary ="获取角色相关资源")
	@GetMapping("/listResource/{roleId}")
	@ResponseBody
	public CommonResult<List<UmsResource>> listResource(@PathVariable Long roleId) {
		List<UmsResource> resourceList = roleService.listResource(roleId);
		return CommonResult.success(resourceList);
	}
	
	@Operation(summary ="给角色分配菜单")
	@PostMapping("/allocMenu")
	@ResponseBody
	public CommonResult<?> allocMenu(@RequestParam Long roleId, @RequestParam List<Long> menuIds) {
		int count = roleService.allocMenu(roleId, menuIds);
		return CommonResult.success(count);
	}
	
	@Operation(summary ="给角色分配资源")
	@PostMapping("/allocResource")
	@ResponseBody
	public CommonResult<?> allocResource(@RequestParam Long roleId, @RequestParam List<Long> resourceIds) {
		int count = roleService.allocResource(roleId, resourceIds);
		return CommonResult.success(count);
	}
}
