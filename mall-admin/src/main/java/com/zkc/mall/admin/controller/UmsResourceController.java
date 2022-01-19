package com.zkc.mall.admin.controller;

import com.zkc.mall.admin.service.UmsResourceService;
import com.zkc.mall.common.api.CommonPage;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.mbg.model.UmsResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Map;
@Tag(name = "UmsResourceController", description = "后台资源管理")

@RestController
@RequestMapping("/resource")
public class UmsResourceController {
	
	@Autowired
	private UmsResourceService resourceService;
	
	@Operation(summary ="添加后台资源")
	@PostMapping("/create")
	@ResponseBody
	public CommonResult create(@RequestBody UmsResource umsResource) {
		int count = resourceService.create(umsResource);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@Operation(summary ="修改后台资源")
	@PostMapping("/update/{id}")
	@ResponseBody
	public CommonResult update(@PathVariable Long id, @RequestBody UmsResource umsResource) {
		int count = resourceService.update(id, umsResource);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@Operation(summary ="根据ID获取资源详情")
	@GetMapping("/{id}")
	@ResponseBody
	public CommonResult<UmsResource> getItem(@PathVariable Long id) {
		UmsResource umsResource = resourceService.getItem(id);
		return CommonResult.success(umsResource);
	}
	
	@Operation(summary ="根据ID删除资源")
	@GetMapping("/delete/{id}")
	@ResponseBody
	public CommonResult<?> delete(@PathVariable Long id) {
		int count = resourceService.delete(id);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@Operation(summary ="分页模糊查询后台资源")
	@GetMapping("/list")
	@ResponseBody
	public CommonResult<CommonPage<UmsResource>> list(
			@RequestParam(required = false) Long categoryId,
			@RequestParam(required = false) String nameKeyword,
			@RequestParam(required = false) String urlKeyword,
			@RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
		List<UmsResource> resourceList = resourceService.list(categoryId, nameKeyword, urlKeyword, pageSize, pageNum);
		
		return CommonResult.success(CommonPage.restPage(resourceList));
	}
	
	@Operation(summary ="查询后台所有资源")
	@GetMapping("/listAll")
	@ResponseBody
	public CommonResult<List<UmsResource>> listAll() {
		List<UmsResource> umsResourceList = resourceService.listAll();
		return CommonResult.success(umsResourceList);
	}
	
	@Operation(summary ="初始化角色资源关联数据")
	@GetMapping("/initResourceRolesMap")
	@ResponseBody
	public CommonResult<?> initResourceRolesMap() {
		Map<String, List<String>> resourceRolesMap = resourceService.initResourceRolesMap();
		return CommonResult.success(resourceRolesMap);
	}
}
