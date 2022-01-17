package com.zkc.mall.admin.controller;

import com.zkc.mall.admin.service.SmsFlashPromotionService;
import com.zkc.mall.common.api.CommonPage;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.mbg.model.SmsFlashPromotion;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Tag(name = "SmsFlashPromotionController", description = "限时购（秒杀）活动管理")
@CrossOrigin
@RestController
@RequestMapping("/flash")
public class SmsFlashPromotionController {
	
	@Autowired
	private SmsFlashPromotionService smsFlashPromotionService;
	
	@Operation(summary = "添加活动")
	@PostMapping("/create")
	@ResponseBody
	public CommonResult<?> create(@RequestBody SmsFlashPromotion flashPromotion) {
		int count = smsFlashPromotionService.create(flashPromotion);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@Operation(summary = "编辑活动信息")
	@PostMapping("/update/{id}")
	@ResponseBody
	public CommonResult<?> update(@PathVariable Long id, @RequestBody SmsFlashPromotion flashPromotion) {
		int count = smsFlashPromotionService.update(id, flashPromotion);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@Operation(summary = "删除活动信息")
	@PostMapping("/delete/{id}")
	@ResponseBody
	public CommonResult<?> delete(@RequestParam("id") Long id) {
		int count = smsFlashPromotionService.delete(id);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@Operation(summary = "修改上下线状态")
	@PostMapping("/update/status/{id}")
	@ResponseBody
	public CommonResult<?> updateStatus(@PathVariable Long id, @RequestParam("status") Integer status) {
		int count = smsFlashPromotionService.updateStatus(id, status);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	
	@Operation(summary = "获取活动详情")
	@PostMapping("/{id}")
	@ResponseBody
	public CommonResult<SmsFlashPromotion> getItem(@PathVariable Long id) {
		SmsFlashPromotion smsFlashPromotion = smsFlashPromotionService.getItem(id);
		return CommonResult.success(smsFlashPromotion);
	}
	
	@Operation(summary = "分页查询活动")
	@GetMapping("/list")
	@ResponseBody
	public CommonResult<CommonPage<SmsFlashPromotion>> list(
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
		List<SmsFlashPromotion> smsFlashPromotionList = smsFlashPromotionService.list(title, pageSize, pageNum);
		return CommonResult.success(CommonPage.restPage(smsFlashPromotionList));
	}
}
