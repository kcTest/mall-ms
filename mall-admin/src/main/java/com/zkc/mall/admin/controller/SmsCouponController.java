package com.zkc.mall.admin.controller;

import com.zkc.mall.admin.dto.SmsCouponParam;
import com.zkc.mall.admin.service.SmsCouponService;
import com.zkc.mall.common.api.CommonPage;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.mbg.model.SmsCoupon;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
@Tag(name = "SmsCouponController", description = "优惠券管理")
@CrossOrigin
@RestController
@RequestMapping("/coupon")
public class SmsCouponController {
	
	@Autowired
	private SmsCouponService couponService;
	
	@Operation(summary ="添加优惠券")
	@PostMapping("/create")
	@ResponseBody
	public CommonResult<?> create(@RequestBody SmsCouponParam flashPromotion) {
		int count = couponService.create(flashPromotion);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@Operation(summary ="删除优惠券")
	@PostMapping("/delete/{id}")
	@ResponseBody
	public CommonResult<?> delete(@PathVariable Long id) {
		int count = couponService.delete(id);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@Operation(summary ="修改优惠券")
	@PostMapping("/update/{id}")
	@ResponseBody
	public CommonResult<?> update(@PathVariable Long id, @RequestBody SmsCouponParam couponParam) {
		int count = couponService.update(id, couponParam);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	
	@Operation(summary ="获取单个优惠券详情")
	@PostMapping("/{id}")
	@ResponseBody
	public CommonResult<SmsCouponParam> getItem(@PathVariable Long id) {
		SmsCouponParam couponParam = couponService.getItem(id);
		return CommonResult.success(couponParam);
	}
	
	
	@Operation(summary ="根据名称和类型 分页查询优惠券列表")
	@GetMapping("/list")
	@ResponseBody
	public CommonResult<CommonPage<SmsCoupon>> list(
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "type", required = false) Integer type,
			@RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
		List<SmsCoupon> couponList = couponService.list(name, type, pageSize, pageNum);
		return CommonResult.success(CommonPage.restPage(couponList));
	}
}
