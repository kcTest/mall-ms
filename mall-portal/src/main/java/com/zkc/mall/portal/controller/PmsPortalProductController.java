package com.zkc.mall.portal.controller;

import com.zkc.mall.common.api.CommonPage;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.mbg.model.PmsProduct;
import com.zkc.mall.portal.domain.PmsPortalProductDetail;
import com.zkc.mall.portal.domain.PmsProductCategoryNode;
import com.zkc.mall.portal.service.PmsPortalProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api("前台商品管理")
@RestController
@RequestMapping("/product")
public class PmsPortalProductController {
	
	@Resource
	private PmsPortalProductService portalProductService;
	
	@ApiImplicitParam(name = "sort", value = "排序字段:0->按相关度 1->按新品 2->按销量 3->价格从低到高 4->价格从高到低",
			defaultValue = "0", allowableValues = "0,1,2,3,4", paramType = "query", dataType = "integer")
	@ApiOperation("综合搜索、筛选、排序")
	@GetMapping("/search")
	@ResponseBody
	public CommonResult<CommonPage<PmsProduct>> search(
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "brandId", required = false) Long brandId,
			@RequestParam(value = "productCategoryId", required = false) Long productCategoryId,
			@RequestParam(value = "sort", defaultValue = "0") Integer sort,
			@RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
			@RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum) {
		List<PmsProduct> productList = portalProductService.search(keyword, brandId, productCategoryId, sort, pageSize, pageNum);
		return CommonResult.success(CommonPage.restPage(productList));
	}
	
	@ApiOperation("以树形结构获取所有商品分类")
	@GetMapping("/categoryTreeList")
	@ResponseBody
	public CommonResult<List<PmsProductCategoryNode>> categoryTreeList() {
		List<PmsProductCategoryNode> categoryNodes = portalProductService.categoryTreeList();
		return CommonResult.success(categoryNodes);
	}
	
	@ApiOperation("获取前台商品详情")
	@PostMapping("/detail/{productId}")
	@ResponseBody
	public CommonResult<PmsPortalProductDetail> detail(@PathVariable Long productId) {
		PmsPortalProductDetail productDetail = portalProductService.detail(productId);
		return CommonResult.success(productDetail);
	}
}
