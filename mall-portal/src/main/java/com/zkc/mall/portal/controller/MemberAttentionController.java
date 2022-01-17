package com.zkc.mall.portal.controller;

import com.zkc.mall.common.api.CommonPage;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.portal.domain.MemberBrandAttention;
import com.zkc.mall.portal.service.MemberAttentionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
@Tag(name = "MemberAttentionController", description = "会员关注品牌管理")
@CrossOrigin
@RestController
@RequestMapping("/member/attention")
public class MemberAttentionController {
	
	@Autowired
	private MemberAttentionService memberAttentionService;
	
	@Operation(summary ="添加品牌关注")
	@PostMapping("/add")
	@ResponseBody
	public CommonResult<?> add(@RequestBody MemberBrandAttention brandAttention) {
		int count = memberAttentionService.add(brandAttention);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@Operation(summary ="取消品牌关注")
	@PostMapping("/delete")
	@ResponseBody
	public CommonResult<?> delete(@RequestParam("brandId") Long brandId) {
		int count = memberAttentionService.delete(brandId);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	
	@Operation(summary ="显示关注列表")
	@GetMapping("/list")
	@ResponseBody
	public CommonResult<CommonPage<MemberBrandAttention>> list(
			@RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
			@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum) {
		List<MemberBrandAttention> brandAttentionList = memberAttentionService.list(pageSize, pageNum);
		return CommonResult.success(CommonPage.restPage(brandAttentionList));
	}
	
	@Operation(summary ="显示品牌关注详情")
	@GetMapping("/detail")
	@ResponseBody
	public CommonResult<MemberBrandAttention> detail(@RequestParam("brandId") Long brandId) {
		MemberBrandAttention brandAttention = memberAttentionService.detail(brandId);
		return CommonResult.success(brandAttention);
	}
	
	@Operation(summary ="清空品牌关注列表")
	@PostMapping("/clear")
	@ResponseBody
	public CommonResult<MemberBrandAttention> clear() {
		memberAttentionService.clear();
		return CommonResult.success(null);
	}
}
