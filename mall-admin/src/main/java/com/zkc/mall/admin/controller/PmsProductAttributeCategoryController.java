package com.zkc.mall.admin.controller;

import com.zkc.mall.admin.dto.PmsProductAttributeCategoryItem;
import com.zkc.mall.admin.service.PmsProductAttributeCategoryService;
import com.zkc.mall.common.api.CommonPage;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.mbg.model.PmsProductAttributeCategory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api("商品属性分类管理")
@RestController
@RequestMapping(value = "/productAttribute/category")
public class PmsProductAttributeCategoryController {
	
	@Resource
	private PmsProductAttributeCategoryService attributeCategoryService;
	
	@ApiOperation("添加商品属性分类信息")
	@PostMapping("/create")
	@ResponseBody
	public CommonResult<?> create(@RequestParam String name) {
		int count = attributeCategoryService.create(name);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	
	@ApiOperation("修改商品属性分类")
	@PostMapping("/update/{id}")
	@ResponseBody
	public CommonResult<?> update(@PathVariable Long id, @RequestParam String name) {
		int count = attributeCategoryService.update(id, name);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("删除商品属性分类")
	@PostMapping("/delete/{id}")
	@ResponseBody
	public CommonResult<?> delete(@PathVariable Long id) {
		int count = attributeCategoryService.delete(id);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("查询单个商品属性分类")
	@GetMapping("/{id}")
	@ResponseBody
	public CommonResult<PmsProductAttributeCategory> getItem(@PathVariable Long id) {
		PmsProductAttributeCategory productAttribute = attributeCategoryService.getItem(id);
		return CommonResult.success(productAttribute);
	}
	
	@ApiOperation("根据分类查询属性或参数列表")
	@GetMapping("/list/{cid}")
	@ResponseBody
	public CommonResult<CommonPage<PmsProductAttributeCategory>> getList(@RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
																		 @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
		List<PmsProductAttributeCategory> productAttributeCategoryList = attributeCategoryService.getList(pageSize, pageNum);
		return CommonResult.success(CommonPage.restPage(productAttributeCategoryList));
	}
	
	@ApiOperation("查询所有商品属性分类及其下属性")
	@GetMapping("/list/withAttr")
	@ResponseBody
	public CommonResult<List<PmsProductAttributeCategoryItem>> getListWithAttr() {
		List<PmsProductAttributeCategoryItem> productAttrInfoList = attributeCategoryService.getListWithAttr();
		return CommonResult.success(productAttrInfoList);
	}
	
}

