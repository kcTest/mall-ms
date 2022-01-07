package com.zkc.mall.admin.controller;

import com.zkc.mall.admin.service.OmsOrderSettingService;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.mbg.model.OmsOrderSetting;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api("订单设置管理")
@RestController
@RequestMapping("/orderSetting")
public class OmsOrderSettingController {
	
	@Resource
	private OmsOrderSettingService orderSettingService;
	
	@ApiOperation("获取订单设置")
	@GetMapping("/{id}")
	@ResponseBody
	public CommonResult<OmsOrderSetting> getItem(@PathVariable Long id) {
		OmsOrderSetting orderSetting = orderSettingService.getItem(id);
		return CommonResult.success(orderSetting);
	}
	
	@ApiOperation("修改订单设置")
	@GetMapping("/update/{id}")
	@ResponseBody
	public CommonResult<?> update(@PathVariable Long id, @RequestBody OmsOrderSetting omsOrderSet) {
		int count = orderSettingService.update(id, omsOrderSet);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
}
