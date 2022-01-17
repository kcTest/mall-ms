package com.zkc.mall.portal.controller;

import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.mbg.model.CmsSubject;
import com.zkc.mall.mbg.model.PmsProduct;
import com.zkc.mall.mbg.model.PmsProductCategory;
import com.zkc.mall.portal.domain.HomeContentResult;
import com.zkc.mall.portal.service.HomeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "HomeController", description = "首页内容管理")
@CrossOrigin
@RestController
@RequestMapping("/order")
public class HomeController {
	
	@Autowired
	private HomeService homeService;
	
	@Operation(summary ="首页内容信息展示")
	@GetMapping("/content")
	@ResponseBody
	public CommonResult<HomeContentResult> content() {
		HomeContentResult homeContentResult = homeService.content();
		return CommonResult.success(homeContentResult);
	}
	
	@Operation(summary ="分页获取推荐商品")
	@GetMapping("/recommendProductList")
	@ResponseBody
	public CommonResult<List<PmsProduct>> recommendProductList(
			@RequestParam(value = "pageSize", defaultValue = "4", required = false) Integer pageSize,
			@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum) {
		List<PmsProduct> productList = homeService.recommendProductList(pageSize, pageNum);
		return CommonResult.success(productList);
	}
	
	@Operation(summary ="获取首页商品分类")
	@GetMapping("/productCateList/{parentId}")
	@ResponseBody
	public CommonResult<List<PmsProductCategory>> getSubjectList(@PathVariable Long parentId) {
		List<PmsProductCategory> productCategoryList = homeService.getProductCategoryList(parentId);
		return CommonResult.success(productCategoryList);
	}
	
	@Operation(summary ="根据分类获取专题")
	@GetMapping("/subjectList")
	@ResponseBody
	public CommonResult<List<CmsSubject>> getSubjectList(
			@RequestParam(value = "cateId", required = false) Long cateId,
			@RequestParam(value = "pageSize", defaultValue = "4", required = false) Integer pageSize,
			@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum) {
		List<CmsSubject> subjectList = homeService.getSubjectList(cateId, pageSize, pageNum);
		return CommonResult.success(subjectList);
	}
	
	@Operation(summary ="分页获取人气专题")
	@GetMapping("/hotProductList")
	@ResponseBody
	public CommonResult<List<PmsProduct>> hotProductList(
			@RequestParam(value = "pageSize", defaultValue = "6", required = false) Integer pageSize,
			@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum) {
		List<PmsProduct> productList = homeService.hotProductList(pageSize, pageNum);
		return CommonResult.success(productList);
	}
	
	
	@Operation(summary ="分页获取新品推荐商品")
	@GetMapping("/newProductList")
	@ResponseBody
	public CommonResult<List<PmsProduct>> newProductList(
			@RequestParam(value = "pageSize", defaultValue = "6", required = false) Integer pageSize,
			@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum) {
		List<PmsProduct> productList = homeService.newProductList(pageSize, pageNum);
		return CommonResult.success(productList);
	}
	
}
