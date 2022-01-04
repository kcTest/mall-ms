package com.zkc.mall.admin.controller;

import com.zkc.mall.admin.dto.SmsFlashPromotionProduct;
import com.zkc.mall.admin.service.SmsFlashPromotionProductRelationService;
import com.zkc.mall.common.api.CommonPage;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.mbg.model.SmsFlashPromotionProductRelation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "限时购（秒杀）活动与场次（时间段）关系管理")
@RestController
@RequestMapping("/flashProductRelation")
public class SmsFlashPromotionProductRelationController {
	
	@Resource
	private SmsFlashPromotionProductRelationService relationService;
	
	@ApiOperation("批量选择商品添加关联")
	@PostMapping("/create")
	@ResponseBody
	public CommonResult<?> create(@RequestBody List<SmsFlashPromotionProductRelation> flashPromotionProductRelationList) {
		int count = relationService.create(flashPromotionProductRelationList);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("修改关联相关信息")
	@PostMapping("/update/{id}")
	@ResponseBody
	public CommonResult<?> update(@PathVariable Long id, @RequestBody SmsFlashPromotionProductRelation relation) {
		int count = relationService.update(id, relation);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("删除关联")
	@PostMapping("/delete/{id}")
	@ResponseBody
	public CommonResult<?> delete(@RequestParam("id") Long id) {
		int count = relationService.delete(id);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("获取关联详情")
	@GetMapping("/{id}")
	@ResponseBody
	public CommonResult<SmsFlashPromotionProductRelation> getItem(@PathVariable Long id) {
		SmsFlashPromotionProductRelation promotionProductRelation = relationService.getItem(id);
		return CommonResult.success(promotionProductRelation);
	}
	
	@ApiOperation("分页查询指定场次及时间段的关联商品信息")
	@GetMapping("/list")
	@ResponseBody
	public CommonResult<CommonPage<SmsFlashPromotionProduct>> list(
			@RequestParam(value = "flashPromotionId") Long flashPromotionId,
			@RequestParam(value = "flashPromotionSessionId") Long flashPromotionSessionId,
			@RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum
	) {
		List<SmsFlashPromotionProduct> flashPromotionProductList =
				relationService.list(flashPromotionId, flashPromotionSessionId, pageSize, pageNum);
		return CommonResult.success(CommonPage.restPage(flashPromotionProductList));
	}
}
