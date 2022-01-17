package com.zkc.mall.portal.controller;

import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.mbg.model.SmsCoupon;
import com.zkc.mall.mbg.model.SmsCouponHistory;
import com.zkc.mall.portal.domain.CartPromotionItem;
import com.zkc.mall.portal.domain.SmsCouponHistoryDetail;
import com.zkc.mall.portal.service.OmsCartItemService;
import com.zkc.mall.portal.service.UmsMemberCouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
@Tag(name = "UmsMemberCouponController", description = "用户优惠券管理")
@CrossOrigin
@RestController
@RequestMapping("/member/coupon")
public class UmsMemberCouponController {
	
	@Autowired
	private UmsMemberCouponService couponService;
	
	@Autowired
	private OmsCartItemService cartItemService;
	
	@Operation(summary = "领取指定优惠券")
	@PostMapping("/add/{couponId}")
	@ResponseBody
	public CommonResult<?> add(@PathVariable Long couponId) {
		couponService.add(couponId);
		return CommonResult.success("领取成功");
	}
	
	@Operation(summary = "获取用户优惠券历史列表")
	@Parameter(name = "useStatus", description = "优惠券筛选类型:0->未使用 1->已使用 2->已过期",
			in = ParameterIn.QUERY,
			content = @Content(schema = @Schema(allowableValues = "0,1,2", type = "integer")))
	@GetMapping("/listHistory")
	@ResponseBody
	public CommonResult<List<SmsCouponHistory>> listHistory(@RequestParam(value = "useStatus", required = false) Integer useStatus) {
		List<SmsCouponHistory> couponHistoryList = couponService.listHistory(useStatus);
		return CommonResult.success(couponHistoryList);
	}
	
	
	@Operation(summary = "获取用户优惠券列表")
	@Parameter(name = "useStatus", description = "优惠券筛选类型:0->未使用 1->已使用 2->已过期",
			in = ParameterIn.QUERY,
			content = @Content(schema = @Schema(allowableValues = "0,1,2", type = "integer")))
	@GetMapping("/list")
	@ResponseBody
	public CommonResult<List<SmsCoupon>> list(@RequestParam(value = "useStatus", required = false) Integer useStatus) {
		List<SmsCoupon> couponHistoryList = couponService.list(useStatus);
		return CommonResult.success(couponHistoryList);
	}
	
	@Operation(summary = "获取登录会员购物车的相关优惠券")
	@Parameter(name = "type", description = "0->不可用 1->可用",
			in = ParameterIn.QUERY,
			content = @Content(schema = @Schema(defaultValue = "1", allowableValues = "0,1", type = "integer")))
	@GetMapping("/list/cart/{type}")
	@ResponseBody
	public CommonResult<List<SmsCouponHistoryDetail>> listCart(@PathVariable Integer type) {
		List<CartPromotionItem> cartPromotionItemList = cartItemService.listPromotion(null);
		List<SmsCouponHistoryDetail> historyDetailList = couponService.listCart(cartPromotionItemList, type);
		return CommonResult.success(historyDetailList);
	}
	
	@Operation(summary = "获取当前商品相关优惠券")
	@GetMapping("/listByProduct/{productId}")
	@ResponseBody
	public CommonResult<List<SmsCoupon>> listByProduct(@PathVariable Long productId) {
		List<SmsCoupon> couponList = couponService.listByProduct(productId);
		return CommonResult.success(couponList);
	}
}
