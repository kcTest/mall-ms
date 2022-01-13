package com.zkc.mall.search.controller;

import com.zkc.mall.common.api.CommonPage;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.search.domain.EsProduct;
import com.zkc.mall.search.domain.EsProductRelatedInfo;
import com.zkc.mall.search.service.EsProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api("搜索商品管理")
@RestController
@RequestMapping("/esProduct")
public class EsProductController {
	
	@Resource
	private EsProductService esProductService;
	
	@ApiOperation("导入所有商品到ES")
	@PostMapping("/importAll")
	@ResponseBody
	public CommonResult<Integer> importAll() {
		int count = esProductService.importAll();
		return CommonResult.success(count);
	}
	
	@ApiOperation("根据id删除商品")
	@GetMapping("/delete/{id}")
	@ResponseBody
	public CommonResult<?> delete(@PathVariable Long id) {
		esProductService.delete(id);
		return CommonResult.success(null);
	}
	
	@ApiOperation("根据id删除商品")
	@PostMapping("/delete/batch")
	@ResponseBody
	public CommonResult<?> delete(@RequestParam("ids") List<Long> ids) {
		esProductService.delete(ids);
		return CommonResult.success(null);
	}
	
	@ApiOperation("根据id创建商品")
	@PostMapping("/create/{id}")
	@ResponseBody
	public CommonResult<EsProduct> create(@PathVariable Long id) {
		EsProduct esProduct = esProductService.create(id);
		return esProduct != null ? CommonResult.success(esProduct) : CommonResult.failed();
	}
	
	@ApiOperation("简单搜索")
	@GetMapping("/search/simple")
	@ResponseBody
	public CommonResult<CommonPage<EsProduct>> search(
			@RequestParam(required = false) String keyword,
			@RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
			@RequestParam(value = "pageNum", defaultValue = "0", required = false) Integer pageNum) {
		List<EsProduct> esProductList = esProductService.search(keyword, pageSize, pageNum);
		return CommonResult.success(CommonPage.restPage(esProductList));
	}
	
	@ApiOperation("综合搜索、筛选、排序")
	@ApiImplicitParam(name = "sort", value = "排序: 0->按相关度 1->按新品 2->按销量 3->按价格从低到高 4->按价格从高到低",
			defaultValue = "0", allowableValues = ",0,1,2,3,4", paramType = "query", dataType = "integer")
	@GetMapping("/list")
	@ResponseBody
	public CommonResult<CommonPage<EsProduct>> search(
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "brandId", required = false) Long brandId,
			@RequestParam(value = "productCategoryId", required = false) Long productCategoryId,
			@RequestParam(value = "sort", defaultValue = "0", required = false) Integer sort,
			@RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
			@RequestParam(value = "pageNum", defaultValue = "0", required = false) Integer pageNum) {
		List<EsProduct> esProductList = esProductService.search(keyword, brandId, productCategoryId, sort, pageSize, pageNum);
		return CommonResult.success(CommonPage.restPage(esProductList));
	}
	
	@ApiOperation("根据ID推荐商品")
	@GetMapping("/recommend/{id}")
	@ResponseBody
	public CommonResult<CommonPage<EsProduct>> recommend(
			@PathVariable Long id,
			@RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
			@RequestParam(value = "pageNum", defaultValue = "0", required = false) Integer pageNum) {
		List<EsProduct> esProductList = esProductService.recommend(id, pageSize, pageNum);
		return CommonResult.success(CommonPage.restPage(esProductList));
	}
	
	
	@ApiOperation("获取搜索的相关品牌、分类及筛选属性")
	@GetMapping("/search/relate")
	@ResponseBody
	public CommonResult<EsProductRelatedInfo> searchRelatedInfo(@RequestParam(value = "keyword", required = false) String keyword) {
		EsProductRelatedInfo relatedInfo = esProductService.searchRelatedInfo(keyword);
		return CommonResult.success(relatedInfo);
	}
	
}
