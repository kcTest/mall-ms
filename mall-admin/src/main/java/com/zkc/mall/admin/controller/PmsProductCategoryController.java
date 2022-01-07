package com.zkc.mall.admin.controller;

import com.zkc.mall.admin.dto.PmsProductCategoryParam;
import com.zkc.mall.admin.dto.PmsProductCategoryWithChildrenItem;
import com.zkc.mall.admin.service.PmsProductCategoryService;
import com.zkc.mall.common.api.CommonPage;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.mbg.model.PmsProductCategory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api("商品分类管理")
@RestController
@RequestMapping(value = "/productCategory")
public class PmsProductCategoryController {
	
	@Resource
	private PmsProductCategoryService productCategoryService;
	
	@ApiOperation("创建商品分类")
	@PostMapping("/create")
	@ResponseBody
	public CommonResult<?> create(@Validated @RequestBody PmsProductCategoryParam productCategoryParam) {
		int count = productCategoryService.create(productCategoryParam);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("修改商品分类")
	@PostMapping("/update/{id}")
	@ResponseBody
	public CommonResult<?> update(@PathVariable Long id, @Validated @RequestBody PmsProductCategoryParam productCategoryParam) {
		int count = productCategoryService.update(id, productCategoryParam);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("分页查询商品分类")
	@GetMapping("/list/{parentId}")
	@ResponseBody
	public CommonResult<CommonPage<PmsProductCategory>> getList(@PathVariable Long parentId,
																@RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
																@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
		List<PmsProductCategory> productList = productCategoryService.getList(parentId, pageSize, pageNum);
		return CommonResult.success(CommonPage.restPage(productList));
	}
	
	@ApiOperation("根据商品ID获取商品分类")
	@GetMapping("/{id}")
	@ResponseBody
	public CommonResult<PmsProductCategory> getItem(@PathVariable Long id) {
		PmsProductCategory productCategory = productCategoryService.getItem(id);
		return CommonResult.success(productCategory);
	}
	
	@ApiOperation("删除分类")
	@PostMapping("/delete/{id}")
	@ResponseBody
	public CommonResult<?> delete(@PathVariable Long id) {
		int count = productCategoryService.delete(id);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	
	@ApiOperation("批量修改导航栏状态")
	@PostMapping("/update/navStatus")
	@ResponseBody
	public CommonResult<?> updateNavStatus(@RequestParam("ids") List<Long> ids,
										   @RequestParam("navStatus") Integer navStatus) {
		int count = productCategoryService.updateNavStatus(ids, navStatus);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("批量修改显示状态")
	@PostMapping("/update/showStatus")
	@ResponseBody
	public CommonResult<?> updateVerifyStatus(@RequestParam("ids") List<Long> ids,
											  @RequestParam("showStatus") Integer showStatus) {
		int count = productCategoryService.updateShowStatus(ids, showStatus);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("查询所有一级分类及子分类")
	@GetMapping("/list/withChildren")
	@ResponseBody
	public CommonResult<List<PmsProductCategoryWithChildrenItem>> listWithChildren() {
		List<PmsProductCategoryWithChildrenItem> productCategoryWithChildrenItemList = productCategoryService.listWithChildren();
		return CommonResult.success(productCategoryWithChildrenItemList);
	}
	
}

