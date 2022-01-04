package com.zkc.mall.admin.controller;

import com.zkc.mall.admin.service.SmsHomeBrandService;
import com.zkc.mall.common.api.CommonPage;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.mbg.model.SmsHomeBrand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@Api(tags = "首页品牌推荐")
@RequestMapping(value = "/home/brand")
@RestController
public class SmsHomeBrandController {
	
	@Resource
	private SmsHomeBrandService homeBrandService;
	
	@ApiOperation("添加首页品牌推荐")
	@PostMapping("/create")
	@ResponseBody
	public CommonResult<?> create(@RequestBody List<SmsHomeBrand> homeBrandList) {
		int count = homeBrandService.create(homeBrandList);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("修改推荐品牌排序")
	@PostMapping("/update/sort/{id}")
	@ResponseBody
	public CommonResult<?> updateSort(@PathVariable Long id, @RequestParam("sort") Integer sort) {
		int count = homeBrandService.updateSort(id, sort);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("批量删除推荐品牌排序")
	@PostMapping("/delete")
	@ResponseBody
	public CommonResult<?> delete(@RequestParam("ids") List<Long> ids) {
		int count = homeBrandService.delete(ids);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("批量修改推荐品牌状态")
	@PostMapping("/update/recommendStatus")
	@ResponseBody
	public CommonResult<?> updateRecommendStatus(@RequestParam("ids") List<Long> ids, @RequestParam("status") Integer recommendStatus) {
		int count = homeBrandService.updateRecommendStatus(ids, recommendStatus);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("分页查询品牌推荐")
	@GetMapping("/list")
	@ResponseBody
	public CommonResult<CommonPage<SmsHomeBrand>> list(
			@RequestParam(value = "productName", required = false) String productName,
			@RequestParam(value = "recommendStatus", required = false) Integer recommendStatus,
			@RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
		List<SmsHomeBrand> homeBrandList = homeBrandService.list(productName, recommendStatus, pageSize, pageNum);
		return CommonResult.success(CommonPage.restPage(homeBrandList));
	}
	
}
	
	
	