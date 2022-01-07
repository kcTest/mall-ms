package com.zkc.mall.admin.controller;

import com.zkc.mall.admin.dto.PmsProductParam;
import com.zkc.mall.admin.dto.PmsProductQueryParam;
import com.zkc.mall.admin.dto.PmsProductResult;
import com.zkc.mall.admin.service.PmsProductService;
import com.zkc.mall.admin.service.PmsSkuStockService;
import com.zkc.mall.common.api.CommonPage;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.mbg.model.PmsProduct;
import com.zkc.mall.mbg.model.PmsSkuStock;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api("商品管理")
@RestController
@RequestMapping(value = "/product")
public class PmsProductController {
	
	@Resource
	private PmsProductService productService;
	
	@ApiOperation("创建商品")
	@PostMapping("/create")
	@ResponseBody
	public CommonResult<?> create(@RequestBody PmsProductParam product) {
		int count = productService.create(product);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("根据商品ID获取商品编辑信息")
	@GetMapping("/updateInfo/{id}")
	@ResponseBody
	public CommonResult<PmsProductResult> getUpdateInfo(@PathVariable Long pid) {
		PmsProductResult productResult = productService.getUpdateInfo(pid);
		return CommonResult.success(productResult);
	}
	
	@ApiOperation("更新商品")
	@PostMapping("/update/{id}")
	@ResponseBody
	public CommonResult<?> update(@PathVariable Long id, @RequestBody PmsProductParam productParam) {
		int count = productService.update(id, productParam);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("分页查询商品")
	@GetMapping("/list")
	@ResponseBody
	public CommonResult<CommonPage<PmsProduct>> list(@RequestBody PmsProductQueryParam productQueryParam,
													 @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
													 @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
		List<PmsProduct> productList = productService.list(productQueryParam, pageSize, pageNum);
		return CommonResult.success(CommonPage.restPage(productList));
	}
	
	@ApiOperation("根据商品名称或货号模糊查询")
	@GetMapping("/simpleList")
	@ResponseBody
	public CommonResult<List<PmsProduct>> getList(@RequestParam("keyword") String keyword) {
		List<PmsProduct> productList = productService.getList(keyword);
		return CommonResult.success(productList);
	}
	
	@ApiOperation("批量修改审核状态")
	@GetMapping("/update/verifyStatus")
	@ResponseBody
	public CommonResult<?> updateVerifyStatus(@RequestParam("ids") List<Long> ids,
											  @RequestParam("verifyStatus") Integer verifyStatus,
											  @RequestParam("detail") String detail) {
		int count = productService.updateVerifyStatus(ids, verifyStatus, detail);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("批量修改上下架状态")
	@GetMapping("/update/publishStatus")
	@ResponseBody
	public CommonResult<?> updatePublishStatus(@RequestParam("ids") List<Long> ids,
											   @RequestParam("publishStatus") Integer publishStatus) {
		int count = productService.updatePublishStatus(ids, publishStatus);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("批量推荐商品")
	@GetMapping("/update/recommendStatus")
	@ResponseBody
	public CommonResult<?> updateRecommendStatus(@RequestParam("ids") List<Long> ids,
												 @RequestParam("recommendStatus") Integer recommendStatus) {
		int count = productService.updateRecommendStatus(ids, recommendStatus);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("批量设为新品")
	@GetMapping("/update/newStatus")
	@ResponseBody
	public CommonResult<?> updateNewStatus(@RequestParam("ids") List<Long> ids,
										   @RequestParam("newStatus") Integer newStatus) {
		int count = productService.updateNewStatus(ids, newStatus);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("批量修改删除状态")
	@GetMapping("/update/deleteStatus")
	@ResponseBody
	public CommonResult<?> updateDeleteStatus(@RequestParam("ids") List<Long> ids,
											  @RequestParam("newStatus") Integer deleteStatus) {
		int count = productService.updateDeleteStatus(ids, deleteStatus);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
}