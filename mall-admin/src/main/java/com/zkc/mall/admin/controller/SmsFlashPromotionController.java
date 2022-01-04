package com.zkc.mall.admin.controller;

import com.zkc.mall.admin.service.SmsFlashPromotionService;
import com.zkc.mall.common.api.CommonPage;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.mbg.model.SmsFlashPromotion;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "限时购（秒杀）活动管理")
@RestController
@RequestMapping("/flash")
public class SmsFlashPromotionController {
	
	@Resource
	private SmsFlashPromotionService smsFlashPromotionService;
	
	@ApiOperation("添加活动")
	@PostMapping("/create")
	@ResponseBody
	public CommonResult<?> create(@RequestBody SmsFlashPromotion flashPromotion) {
		int count = smsFlashPromotionService.create(flashPromotion);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("编辑活动信息")
	@PostMapping("/update/{id}")
	@ResponseBody
	public CommonResult<?> update(@PathVariable Long id, @RequestBody SmsFlashPromotion flashPromotion) {
		int count = smsFlashPromotionService.update(id, flashPromotion);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("删除活动信息")
	@PostMapping("/delete/{id}")
	@ResponseBody
	public CommonResult<?> delete(@RequestParam("id") Long id) {
		int count = smsFlashPromotionService.delete(id);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("修改上下线状态")
	@PostMapping("/update/status/{id}")
	@ResponseBody
	public CommonResult<?> updateStatus(@PathVariable Long id, @RequestParam("status") Integer status) {
		int count = smsFlashPromotionService.updateStatus(id, status);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	
	@ApiOperation("获取活动详情")
	@PostMapping("/{id}")
	@ResponseBody
	public CommonResult<SmsFlashPromotion> getItem(@PathVariable Long id) {
		SmsFlashPromotion smsFlashPromotion = smsFlashPromotionService.getItem(id);
		return CommonResult.success(smsFlashPromotion);
	}
	
	@ApiOperation("分页查询活动")
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
