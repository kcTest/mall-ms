package com.zkc.mall.portal.controller;

import com.zkc.mall.common.api.CommonPage;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.portal.domain.MemberProductCollection;
import com.zkc.mall.portal.service.MemberCollectionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api("会员收藏管理")
@RestController
@RequestMapping("/member/productCollection")
public class MemberProductCollectionController {
	
	@Resource
	private MemberCollectionService memberCollectionService;
	
	@ApiOperation("添加商品收藏")
	@PostMapping("/add")
	@ResponseBody
	public CommonResult<?> add(@RequestBody MemberProductCollection productCollection) {
		int count = memberCollectionService.add(productCollection);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("删除商品收藏")
	@PostMapping("/delete")
	@ResponseBody
	public CommonResult<?> delete(@RequestParam("productId") Long productId) {
		int count = memberCollectionService.delete(productId);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	
	@ApiOperation("显示收藏列表")
	@GetMapping("/list")
	@ResponseBody
	public CommonResult<CommonPage<MemberProductCollection>> list(
			@RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
			@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum) {
		List<MemberProductCollection> productCollectionList = memberCollectionService.list(pageSize, pageNum);
		return CommonResult.success(CommonPage.restPage(productCollectionList));
	}
	
	@ApiOperation("显示收藏商品详情")
	@GetMapping("/detail")
	@ResponseBody
	public CommonResult<MemberProductCollection> detail(@RequestParam("productId") Long productId) {
		MemberProductCollection productCollection = memberCollectionService.detail(productId);
		return CommonResult.success(productCollection);
	}
	
	
}
