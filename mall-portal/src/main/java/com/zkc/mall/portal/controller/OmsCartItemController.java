package com.zkc.mall.portal.controller;

import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.mbg.model.OmsCartItem;
import com.zkc.mall.portal.domain.CartProduct;
import com.zkc.mall.portal.domain.CartPromotionItem;
import com.zkc.mall.portal.service.OmsCartItemService;
import com.zkc.mall.portal.service.UmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api("购物车管理")
@RestController
@RequestMapping("/cart")
public class OmsCartItemController {
	
	@Resource
	private OmsCartItemService cartItemService;
	@Resource
	private UmsMemberService memberService;
	
	@ApiOperation("添加商品到购物车")
	@PostMapping("/add")
	@ResponseBody
	public CommonResult<?> add(@RequestBody OmsCartItem cartItem) {
		int count = cartItemService.add(cartItem);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("查询某个会员的购物车列表")
	@GetMapping("/list")
	@ResponseBody
	public CommonResult<List<OmsCartItem>> list() {
		List<OmsCartItem> cartItemList = cartItemService.list(memberService.getCurrentMember().getId());
		return CommonResult.success(cartItemList);
	}
	
	@ApiOperation("查询某个会员的购物车列表 包括促销信息")
	@GetMapping("/list/promotion")
	@ResponseBody
	public CommonResult<List<CartPromotionItem>> listPromotion(@RequestParam List<Long> cartIdList) {
		List<CartPromotionItem> cartPromotionItemList = cartItemService.listPromotion(cartIdList);
		return CommonResult.success(cartPromotionItemList);
	}
	
	
	@ApiOperation("修改购物车中某个商品数量")
	@PostMapping("/update/quantity")
	@ResponseBody
	public CommonResult<?> updateQuantity(@RequestParam("id") Long id, @RequestParam("quantity") Integer quantity) {
		int count = cartItemService.updateQuantity(id, memberService.getCurrentMember().getId(), quantity);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("获取购物车中某个商品的规格，用于重选规格")
	@GetMapping("/getProduct/{productId}")
	@ResponseBody
	public CommonResult<CartProduct> getCartProduct(@PathVariable("productId") Long productId) {
		CartProduct cartProduct = cartItemService.getCartProduct(productId);
		return CommonResult.success(cartProduct);
	}
	
	@ApiOperation("修改购物车中某个商品的规格")
	@PostMapping("/update/attr")
	@ResponseBody
	public CommonResult<?> updateAttr(@RequestBody OmsCartItem cartItem) {
		int count = cartItemService.updateAttr(cartItem);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("删除购物车中某个商品")
	@PostMapping("/delete")
	@ResponseBody
	public CommonResult<?> delete(@RequestParam("ids") List<Long> ids) {
		int count = cartItemService.delete(memberService.getCurrentMember().getId(), ids);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("清空购物车")
	@PostMapping("/clear")
	@ResponseBody
	public CommonResult<?> clear() {
		int count = cartItemService.clear(memberService.getCurrentMember().getId());
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
}
