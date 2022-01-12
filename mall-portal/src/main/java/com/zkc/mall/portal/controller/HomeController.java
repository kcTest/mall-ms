package com.zkc.mall.portal.controller;

import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.mbg.model.CmsSubject;
import com.zkc.mall.mbg.model.PmsProduct;
import com.zkc.mall.mbg.model.PmsProductCategory;
import com.zkc.mall.portal.domain.HomeContentResult;
import com.zkc.mall.portal.service.HomeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api("首页内容管理")
@RestController
@RequestMapping("/order")
public class HomeController {
	
	@Resource
	private HomeService homeService;
	
	@ApiOperation("首页内容信息展示")
	@GetMapping("/content")
	@ResponseBody
	public CommonResult<HomeContentResult> content() {
		HomeContentResult homeContentResult = homeService.content();
		return CommonResult.success(homeContentResult);
	}
	
	@ApiOperation("分页获取推荐商品")
	@GetMapping("/recommendProductList")
	@ResponseBody
	public CommonResult<List<PmsProduct>> recommendProductList(
			@RequestParam(value = "pageSize", defaultValue = "4", required = false) Integer pageSize,
			@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum) {
		List<PmsProduct> productList = homeService.recommendProductList(pageSize, pageNum);
		return CommonResult.success(productList);
	}
	
	@ApiOperation("获取首页商品分类")
	@GetMapping("/productCateList/{parentId}")
	@ResponseBody
	public CommonResult<List<PmsProductCategory>> getSubjectList(@PathVariable Long parentId) {
		List<PmsProductCategory> productCategoryList = homeService.getProductCategoryList(parentId);
		return CommonResult.success(productCategoryList);
	}
	
	@ApiOperation("根据分类获取专题")
	@GetMapping("/subjectList")
	@ResponseBody
	public CommonResult<List<CmsSubject>> getSubjectList(
			@RequestParam(value = "cateId", required = false) Long cateId,
			@RequestParam(value = "pageSize", defaultValue = "4", required = false) Integer pageSize,
			@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum) {
		List<CmsSubject> subjectList = homeService.getSubjectList(cateId, pageSize, pageNum);
		return CommonResult.success(subjectList);
	}
	
	@ApiOperation("分页获取人气专题")
	@GetMapping("/hotProductList")
	@ResponseBody
	public CommonResult<List<PmsProduct>> hotProductList(
			@RequestParam(value = "pageSize", defaultValue = "6", required = false) Integer pageSize,
			@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum) {
		List<PmsProduct> productList = homeService.hotProductList(pageSize, pageNum);
		return CommonResult.success(productList);
	}
	
	
	@ApiOperation("分页获取新品推荐商品")
	@GetMapping("/newProductList")
	@ResponseBody
	public CommonResult<List<PmsProduct>> newProductList(
			@RequestParam(value = "pageSize", defaultValue = "6", required = false) Integer pageSize,
			@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum) {
		List<PmsProduct> productList = homeService.newProductList(pageSize, pageNum);
		return CommonResult.success(productList);
	}
	
}
