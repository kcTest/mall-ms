package com.zkc.mall.admin.controller;

import com.zkc.mall.admin.dto.UmsMenuNode;
import com.zkc.mall.admin.service.UmsMenuService;
import com.zkc.mall.common.api.CommonPage;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.mbg.model.UmsMenu;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api("后台菜单管理")
@RestController
@RequestMapping("/menu")
public class UmsMenuController {
	
	@Resource
	private UmsMenuService menuService;
	
	
	@ApiOperation("添加后台管理菜单")
	@PostMapping("/create")
	@ResponseBody
	public CommonResult<?> create(@RequestBody UmsMenu umsmenu) {
		int count = menuService.create(umsmenu);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("修改后台菜单")
	@PostMapping("/update/{id}")
	@ResponseBody
	public CommonResult<?> update(@PathVariable Long id, @RequestBody UmsMenu umsmenu) {
		int count = menuService.update(id, umsmenu);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("根据ID获取菜单详情")
	@GetMapping("/{id}")
	@ResponseBody
	public CommonResult<UmsMenu> getItem(@PathVariable Long id) {
		UmsMenu umsMenu = menuService.getItem(id);
		return CommonResult.success(umsMenu);
	}
	
	@ApiOperation("根据ID获取菜单详情")
	@GetMapping("/delete/{id}")
	@ResponseBody
	public CommonResult<?> delete(@PathVariable Long id) {
		int count = menuService.delete(id);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("分页模糊查询后台资源")
	@GetMapping("/list/{parentId}")
	@ResponseBody
	public CommonResult<CommonPage<UmsMenu>> list(
			@PathVariable Long parentId,
			@RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
		List<UmsMenu> resourceList = menuService.list(parentId, pageSize, pageNum);
		return CommonResult.success(CommonPage.restPage(resourceList));
	}
	
	@ApiOperation("树形结构返回所有菜单列表")
	@GetMapping("/treeList")
	@ResponseBody
	public CommonResult<List<UmsMenuNode>> treeList() {
		List<UmsMenuNode> menuNodeList = menuService.treeList();
		return CommonResult.success(menuNodeList);
	}
	
	
	@ApiOperation("修改菜单显示状态")
	@GetMapping("/updateHidden/{id}")
	@ResponseBody
	public CommonResult<?> updateHidden(@PathVariable Long id, @RequestParam("hidden") Integer hidden) {
		int count = menuService.updateHidden(id, hidden);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
}
