package com.zkc.mall.admin.controller;

import com.zkc.mall.admin.dto.SmsFlashPromotionSessionDetail;
import com.zkc.mall.admin.service.SmsFlashPromotionSessionService;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.mbg.model.SmsFlashPromotionSession;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Tag(name = "SmsFlashPromotionSessionController", description = "限时购（秒杀）活动场次（时间段）管理")
@CrossOrigin
@RestController
@RequestMapping("/flashSession")
public class SmsFlashPromotionSessionController {
	
	@Autowired
	private SmsFlashPromotionSessionService smsFlashPromotionSessionService;
	
	@Operation(summary = "添加场次")
	@PostMapping("/create")
	@ResponseBody
	public CommonResult<?> create(@RequestBody SmsFlashPromotionSession flashPromotionSession) {
		int count = smsFlashPromotionSessionService.create(flashPromotionSession);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@Operation(summary = "修改场次信息")
	@PostMapping("/update/{id}")
	@ResponseBody
	public CommonResult<?> update(@PathVariable Long id, @RequestBody SmsFlashPromotionSession flashPromotionSession) {
		int count = smsFlashPromotionSessionService.update(id, flashPromotionSession);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	
	@Operation(summary = "修改启用状态")
	@PostMapping("/update/status/{id}")
	@ResponseBody
	public CommonResult<?> updateStatus(@PathVariable Long id, @RequestParam("status") Integer status) {
		int count = smsFlashPromotionSessionService.updateStatus(id, status);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@Operation(summary = "删除场次")
	@PostMapping("/delete/{id}")
	@ResponseBody
	public CommonResult<?> delete(@PathVariable Long id) {
		int count = smsFlashPromotionSessionService.delete(id);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@Operation(summary = "获取场次详情")
	@GetMapping("/{id}")
	@ResponseBody
	public CommonResult<SmsFlashPromotionSession> getItem(@PathVariable Long id) {
		SmsFlashPromotionSession smsFlashPromotionSession = smsFlashPromotionSessionService.getItem(id);
		return CommonResult.success(smsFlashPromotionSession);
	}
	
	@Operation(summary = "获取全部场次")
	@GetMapping("/list")
	@ResponseBody
	public CommonResult<List<SmsFlashPromotionSession>> list() {
		List<SmsFlashPromotionSession> smsFlashPromotionSessionList = smsFlashPromotionSessionService.list();
		return CommonResult.success(smsFlashPromotionSessionList);
	}
	
	@Operation(summary = "获取指定活动下所有场次信息(包含几种商品）")
	@GetMapping("/selectList")
	@ResponseBody
	public CommonResult<List<SmsFlashPromotionSessionDetail>> selectList(Long flashPromotionId) {
		List<SmsFlashPromotionSessionDetail> smsFlashPromotionSessionDetailList = smsFlashPromotionSessionService.selectList(flashPromotionId);
		return CommonResult.success(smsFlashPromotionSessionDetailList);
	}
}
