package com.zkc.mall.portal.controller;

import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.mbg.model.SmsCoupon;
import com.zkc.mall.mbg.model.SmsCouponHistory;
import com.zkc.mall.portal.domain.CartPromotionItem;
import com.zkc.mall.portal.domain.SmsCouponHistoryDetail;
import com.zkc.mall.portal.service.OmsCartItemService;
import com.zkc.mall.portal.service.UmsMemberCouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api("用户优惠券管理")
@RestController
@RequestMapping("/member/coupon")
public class UmsMemberCouponController {
	
	@Resource
	private UmsMemberCouponService couponService;
	
	@Resource
	private OmsCartItemService cartItemService;
	
	@ApiOperation("领取指定优惠券")
	@PostMapping("/add/{couponId}")
	@ResponseBody
	public CommonResult<?> add(@PathVariable Long couponId) {
		couponService.add(couponId);
		return CommonResult.success("领取成功");
	}
	
	@ApiOperation("获取用户优惠券历史列表")
	@ApiImplicitParam(name = "useStatus", value = "优惠券筛选类型:0->未使用 1->已使用 2->已过期",
			allowableValues = "0,1,2", paramType = "query", dataType = "integer")
	@GetMapping("/listHistory")
	@ResponseBody
	public CommonResult<List<SmsCouponHistory>> listHistory(@RequestParam(value = "useStatus", required = false) Integer useStatus) {
		List<SmsCouponHistory> couponHistoryList = couponService.listHistory(useStatus);
		return CommonResult.success(couponHistoryList);
	}
	
	
	@ApiOperation("获取用户优惠券列表")
	@ApiImplicitParam(name = "useStatus", value = "优惠券筛选类型:0->未使用 1->已使用 2->已过期",
			allowableValues = "0,1,2", paramType = "query", dataType = "integer")
	@GetMapping("/list")
	@ResponseBody
	public CommonResult<List<SmsCoupon>> list(@RequestParam(value = "useStatus", required = false) Integer useStatus) {
		List<SmsCoupon> couponHistoryList = couponService.list(useStatus);
		return CommonResult.success(couponHistoryList);
	}
	
	@ApiOperation("获取登录会员购物车的相关优惠券")
	@ApiImplicitParam(name = "type", value = "0->不可用 1->可用",
			defaultValue = "1", allowableValues = "0,1", paramType = "query", dataType = "integer")
	@GetMapping("/list/cart/{type}")
	@ResponseBody
	public CommonResult<List<SmsCouponHistoryDetail>> listCart(@PathVariable Integer type) {
		List<CartPromotionItem> cartPromotionItemList = cartItemService.listPromotion(null);
		List<SmsCouponHistoryDetail> historyDetailList = couponService.listCart(cartPromotionItemList, type);
		return CommonResult.success(historyDetailList);
	}
	
	@ApiOperation("获取当前商品相关优惠券")
	@GetMapping("/listByProduct/{productId}")
	@ResponseBody
	public CommonResult<List<SmsCoupon>> listByProduct(@PathVariable Long productId) {
		List<SmsCoupon> couponList = couponService.listByProduct(productId);
		return CommonResult.success(couponList);
	}
}
