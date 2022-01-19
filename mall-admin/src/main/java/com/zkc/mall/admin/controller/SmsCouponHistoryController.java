package com.zkc.mall.admin.controller;

import com.zkc.mall.admin.service.SmsCouponHistoryService;
import com.zkc.mall.common.api.CommonPage;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.mbg.model.SmsCouponHistory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Tag(name = "SmsCouponHistoryController", description = "优惠券领取记录管理")

@RestController
@RequestMapping("/couponHistory")
public class SmsCouponHistoryController {
	
	@Autowired
	private SmsCouponHistoryService couponHistoryService;
	
	@Operation(summary ="根据优惠券id、使用状态、订单编号分页查询优惠券领取记录")
	@GetMapping("/list")
	@ResponseBody
	public CommonResult<CommonPage<SmsCouponHistory>> list(
			@RequestParam(value = "couponId", required = false) Long couponId,
			@RequestParam(value = "useStatus", required = false) Integer useStatus,
			@RequestParam(value = "orderSn", required = false) String orderSn,
			@RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
		List<SmsCouponHistory> couponList = couponHistoryService.list(couponId, useStatus, orderSn, pageSize, pageNum);
		return CommonResult.success(CommonPage.restPage(couponList));
	}
}
