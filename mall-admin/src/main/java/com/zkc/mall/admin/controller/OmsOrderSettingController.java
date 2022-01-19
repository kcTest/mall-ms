package com.zkc.mall.admin.controller;

import com.zkc.mall.admin.service.OmsOrderSettingService;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.mbg.model.OmsOrderSetting;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

@Tag(name = "OmsOrderSettingController", description = "订单设置管理")

@RestController
@RequestMapping("/orderSetting")
public class OmsOrderSettingController {
	
	@Autowired
	private OmsOrderSettingService orderSettingService;
	
	@Operation(summary ="获取订单设置")
	@GetMapping("/{id}")
	@ResponseBody
	public CommonResult<OmsOrderSetting> getItem(@PathVariable Long id) {
		OmsOrderSetting orderSetting = orderSettingService.getItem(id);
		return CommonResult.success(orderSetting);
	}
	
	@Operation(summary ="修改订单设置")
	@GetMapping("/update/{id}")
	@ResponseBody
	public CommonResult<?> update(@PathVariable Long id, @RequestBody OmsOrderSetting omsOrderSet) {
		int count = orderSettingService.update(id, omsOrderSet);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
}
