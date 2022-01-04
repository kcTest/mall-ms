package com.zkc.mall.admin.controller;

import com.zkc.mall.admin.service.SmsHomeRecommendProductService;
import com.zkc.mall.common.api.CommonPage;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.mbg.model.SmsHomeRecommendProduct;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@Api(tags = "首页人气推荐管理")
@RequestMapping(value = "/home/recommendProduct")
@RestController
public class SmsHomeRecommendProductController {
	
	@Resource
	private SmsHomeRecommendProductService homeRecommendProductService;
	
	@ApiOperation("添加首页人气推荐")
	@PostMapping("/create")
	@ResponseBody
	public CommonResult<?> create(@RequestBody List<SmsHomeRecommendProduct> homeRecommendProducts) {
		int count = homeRecommendProductService.create(homeRecommendProducts);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("修改人气推荐排序")
	@PostMapping("/update/sort/{id}")
	@ResponseBody
	public CommonResult<?> updateSort(@PathVariable Long id, @RequestParam("sort") Integer sort) {
		int count = homeRecommendProductService.updateSort(id, sort);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("批量删除人气推荐排序")
	@PostMapping("/delete")
	@ResponseBody
	public CommonResult<?> delete(@RequestParam("ids") List<Long> ids) {
		int count = homeRecommendProductService.delete(ids);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("批量修改人气推荐状态")
	@PostMapping("/update/recommendStatus")
	@ResponseBody
	public CommonResult<?> updateRecommendStatus(@RequestParam("ids") List<Long> ids, @RequestParam("status") Integer recommendStatus) {
		int count = homeRecommendProductService.updateRecommendStatus(ids, recommendStatus);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("分页查询人气推荐")
	@GetMapping("/list")
	@ResponseBody
	public CommonResult<CommonPage<SmsHomeRecommendProduct>> list(
			@RequestParam(value = "productName", required = false) String productName,
			@RequestParam(value = "recommendStatus", required = false) Integer recommendStatus,
			@RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
		List<SmsHomeRecommendProduct> homeRecommendProductList = homeRecommendProductService.list(productName, recommendStatus, pageSize, pageNum);
		return CommonResult.success(CommonPage.restPage(homeRecommendProductList));
	}
	
}
	
	
	