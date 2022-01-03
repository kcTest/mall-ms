package com.zkc.mall.admin.controller;

import com.zkc.mall.admin.service.UmsResourceCategoryService;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.mbg.model.UmsResourceCategory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api("后台资源分类管理")
@RestController
@RequestMapping("/resourceCategory")
public class UmsResourceCategoryController {
	
	@Resource
	private UmsResourceCategoryService resourceCategoryService;
	
	@ApiOperation("查询后台所有资源分类")
	@GetMapping("/listAll")
	@ResponseBody
	public CommonResult<List<UmsResourceCategory>> listAll() {
		List<UmsResourceCategory> umsResourceCategoryList = resourceCategoryService.listAll();
		return CommonResult.success(umsResourceCategoryList);
	}
	
	@ApiOperation("添加后台资源分类")
	@PostMapping("/create")
	@ResponseBody
	public CommonResult<?> create(@RequestBody UmsResourceCategory umsResourceCategory) {
		int count = resourceCategoryService.create(umsResourceCategory);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("修改后台资源分类")
	@PostMapping("/update/{id}")
	@ResponseBody
	public CommonResult<?> update(@RequestParam Long id, @RequestBody UmsResourceCategory umsResourceCategory) {
		int count = resourceCategoryService.update(id, umsResourceCategory);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("删除后台资源分类")
	@PostMapping("/delete/{id}")
	@ResponseBody
	public CommonResult<?> update(@RequestParam Long id) {
		int count = resourceCategoryService.delete(id);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
}
