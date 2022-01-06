package com.zkc.mall.admin.controller;

import com.zkc.mall.admin.dto.PmsProductCategoryParam;
import com.zkc.mall.admin.dto.PmsProductParam;
import com.zkc.mall.admin.dto.PmsProductQueryParam;
import com.zkc.mall.admin.dto.PmsProductResult;
import com.zkc.mall.admin.service.PmsProductCategoryService;
import com.zkc.mall.common.api.CommonPage;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.mbg.model.PmsProduct;
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
	public CommonResult<?> update(@PathVariable Long id,@Validated @RequestBody PmsProductCategoryParam productCategoryParam) {
		int count = productCategoryService.update(id, productCategoryParam);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
//	@ApiOperation("根据商品ID获取商品编辑信息")
//	@GetMapping("/updateInfo/{id}")
//	@ResponseBody
//	public CommonResult<PmsProductResult> getUpdateInfo(@PathVariable Long pid) {
//		PmsProductResult productResult = productCategoryService.getUpdateInfo(pid);
//		return CommonResult.success(productResult);
//	}
//	
//
//	
//	@ApiOperation("分页查询商品")
//	@GetMapping("/list")
//	@ResponseBody
//	public CommonResult<CommonPage<PmsProduct>> list(@RequestBody PmsProductQueryParam productQueryParam,
//													 @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
//													 @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
//		List<PmsProduct> productList = productCategoryService.list(productQueryParam, pageSize, pageNum);
//		return CommonResult.success(CommonPage.restPage(productList));
//	}
//	
//	@ApiOperation("根据商品名称或货号模糊查询")
//	@GetMapping("/simpleList")
//	@ResponseBody
//	public CommonResult<List<PmsProduct>> getList(@RequestParam("keyword") String keyword) {
//		List<PmsProduct> productList = productCategoryService.getList(keyword);
//		return CommonResult.success(productList);
//	}
//	
//	@ApiOperation("批量修改审核状态")
//	@GetMapping("/update/verifyStatus")
//	@ResponseBody
//	public CommonResult<?> updateVerifyStatus(@RequestParam("ids") List<Long> ids,
//											  @RequestParam("verifyStatus") Integer verifyStatus,
//											  @RequestParam("detail") String detail) {
//		int count = productCategoryService.updateVerifyStatus(ids, verifyStatus, detail);
//		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
//	}
//	
//	@ApiOperation("批量修改上下架状态")
//	@GetMapping("/update/publishStatus")
//	@ResponseBody
//	public CommonResult<?> updatePublishStatus(@RequestParam("ids") List<Long> ids,
//											   @RequestParam("publishStatus") Integer publishStatus) {
//		int count = productCategoryService.updatePublishStatus(ids, publishStatus);
//		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
//	}
//	
//	@ApiOperation("批量推荐商品")
//	@GetMapping("/update/recommendStatus")
//	@ResponseBody
//	public CommonResult<?> updateRecommendStatus(@RequestParam("ids") List<Long> ids,
//												 @RequestParam("recommendStatus") Integer recommendStatus) {
//		int count = productCategoryService.updateRecommendStatus(ids, recommendStatus);
//		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
//	}
//	
//	@ApiOperation("批量设为新品")
//	@GetMapping("/update/newStatus")
//	@ResponseBody
//	public CommonResult<?> updateNewStatus(@RequestParam("ids") List<Long> ids,
//										   @RequestParam("newStatus") Integer newStatus) {
//		int count = productCategoryService.updateNewStatus(ids, newStatus);
//		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
//	}
//	
//	@ApiOperation("批量修改删除状态")
//	@GetMapping("/update/deleteStatus")
//	@ResponseBody
//	public CommonResult<?> updateDeleteStatus(@RequestParam("ids") List<Long> ids,
//											  @RequestParam("newStatus") Integer deleteStatus) {
//		int count = productCategoryService.updateDeleteStatus(ids, deleteStatus);
//		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
//	}
}

