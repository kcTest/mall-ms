package com.zkc.mall.admin.controller;

import com.zkc.mall.admin.service.UmsResourceCategoryService;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.mbg.model.UmsResourceCategory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
@Tag(name = "UmsResourceCategoryController", description = "后台资源分类管理")
@CrossOrigin
@RestController
@RequestMapping("/resourceCategory")
public class UmsResourceCategoryController {
	
	@Autowired
	private UmsResourceCategoryService resourceCategoryService;
	
	@Operation(summary ="查询后台所有资源分类")
	@GetMapping("/listAll")
	@ResponseBody
	public CommonResult<List<UmsResourceCategory>> listAll() {
		List<UmsResourceCategory> umsResourceCategoryList = resourceCategoryService.listAll();
		return CommonResult.success(umsResourceCategoryList);
	}
	
	@Operation(summary ="添加后台资源分类")
	@PostMapping("/create")
	@ResponseBody
	public CommonResult<?> create(@RequestBody UmsResourceCategory umsResourceCategory) {
		int count = resourceCategoryService.create(umsResourceCategory);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@Operation(summary ="修改后台资源分类")
	@PostMapping("/update/{id}")
	@ResponseBody
	public CommonResult<?> update(@RequestParam Long id, @RequestBody UmsResourceCategory umsResourceCategory) {
		int count = resourceCategoryService.update(id, umsResourceCategory);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@Operation(summary ="删除后台资源分类")
	@PostMapping("/delete/{id}")
	@ResponseBody
	public CommonResult<?> update(@RequestParam Long id) {
		int count = resourceCategoryService.delete(id);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
}
