package com.zkc.mall.portal.controller;

import com.zkc.mall.common.api.CommonPage;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.mbg.model.PmsBrand;
import com.zkc.mall.mbg.model.PmsProduct;
import com.zkc.mall.portal.service.PortalBrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api("前台品牌管理")
@RestController
@RequestMapping("/brand")
public class PortalBrandController {
	
	@Resource
	private PortalBrandService portalBrandService;
	
	@ApiOperation("分页获取推荐品牌")
	@GetMapping("/recommendList")
	@ResponseBody
	public CommonResult<List<PmsBrand>> add(@RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize,
											@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
		List<PmsBrand> brandList = portalBrandService.recommendList(pageSize, pageNum);
		return CommonResult.success(brandList);
	}
	
	@ApiOperation("获取品牌详情")
	@GetMapping("/detail/{brandId}")
	@ResponseBody
	public CommonResult<PmsBrand> detail(@PathVariable Long brandId) {
		PmsBrand brand = portalBrandService.detail(brandId);
		return CommonResult.success(brand);
	}
	
	@ApiOperation("分页获取品牌相关商品")
	@GetMapping("/productList")
	@ResponseBody
	public CommonResult<CommonPage<PmsProduct>> productList(
			@RequestParam(value = "brandId") Long brandId,
			@RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
		List<PmsProduct> brandList = portalBrandService.productList(brandId, pageSize, pageNum);
		return CommonResult.success(CommonPage.restPage(brandList));
	}
}
