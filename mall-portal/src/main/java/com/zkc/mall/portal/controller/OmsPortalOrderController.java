package com.zkc.mall.portal.controller;

import com.zkc.mall.common.api.CommonPage;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.portal.domain.ConfirmOrderResult;
import com.zkc.mall.portal.domain.OmsOrderDetail;
import com.zkc.mall.portal.domain.OrderParam;
import com.zkc.mall.portal.service.OmsPortalOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Api("订单管理")
@RestController
@RequestMapping("/order")
public class OmsPortalOrderController {
	
	@Resource
	private OmsPortalOrderService orderService;
	
	@ApiOperation("根据购物车信息生成确认订单信息")
	@PostMapping("/generateConfirmOrder")
	@ResponseBody
	public CommonResult<ConfirmOrderResult> generateConfirmOrder(@RequestParam("cartIds") List<Long> cartIds) {
		ConfirmOrderResult orderResult = orderService.generateConfirmOrder(cartIds);
		return CommonResult.success(orderResult);
	}
	
	@ApiOperation("根据购物车信息生成订单")
	@PostMapping("/generateOrder")
	@ResponseBody
	public CommonResult<?> generateOrder(@RequestBody OrderParam orderParam) {
		Map<String, Object> result = orderService.generateOrder(orderParam);
		return CommonResult.success(result, "下单成功");
	}
	
	@ApiOperation("用户支付成功的回调")
	@PostMapping("/paySuccess")
	@ResponseBody
	public CommonResult<?> paySuccess(@RequestParam("orderId") Long orderId, @RequestParam("payType") Integer payType) {
		int count = orderService.paySuccess(orderId, payType);
		return CommonResult.success(count, "支付成功");
	}
	
	@ApiOperation("自动取消超时订单")
	@PostMapping("/cancelTimeOutOrder")
	@ResponseBody
	public CommonResult<?> cancelTimeOutOrder() {
		int count = orderService.cancelTimeOutOrder();
		return CommonResult.success(null);
	}
	
	@ApiOperation("取消单个超时订单")
	@PostMapping("/cancelOrder")
	@ResponseBody
	public CommonResult<?> cancelOrder(@RequestParam("orderId") Long orderId) {
		orderService.sendDelayMessageCancelOrder(orderId);
		return CommonResult.success(null);
	}
	
	@ApiOperation("按状态分页获取用户订单列表")
	@ApiImplicitParam(name = "status", value = "订单状态:-1->全部 0->待付款 1->待发货 2->已发货 3->已完成 4->已关闭",
			defaultValue = "-1", allowableValues = "-1,0,1,2,3,4", paramType = "query", dataType = "int")
	@GetMapping("/list")
	@ResponseBody
	public CommonResult<CommonPage<OmsOrderDetail>> list(
			@RequestParam(value = "status", required = false) Integer status,
			@RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
			@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum) {
		List<OmsOrderDetail> orderDetailList = orderService.list(status, pageSize, pageNum);
		return CommonResult.success(CommonPage.restPage(orderDetailList));
	}
	
	@ApiOperation("根据ID获取订单详情")
	@PostMapping("/detail/{orderId}")
	@ResponseBody
	public CommonResult<OmsOrderDetail> detail(@RequestParam("orderId") Long orderId) {
		OmsOrderDetail orderDetail = orderService.detail(orderId);
		return CommonResult.success(orderDetail);
	}
	
	@ApiOperation("用户取消订单")
	@PostMapping("/cancelUserOrder")
	@ResponseBody
	public CommonResult<?> cancelUserOrder(@RequestParam("orderId") Long orderId) {
		orderService.cancelOrder(orderId);
		return CommonResult.success(null);
	}
	
	@ApiOperation("用户确认收货")
	@PostMapping("/confirmReceiveOrder")
	@ResponseBody
	public CommonResult<?> confirmReceiveOrder(@RequestParam("orderId") Long orderId) {
		orderService.confirmReceiveOrder(orderId);
		return CommonResult.success(null);
	}
	
	@ApiOperation("用户删除订单")
	@PostMapping("/deleteOrder")
	@ResponseBody
	public CommonResult<?> deleteOrder(@RequestParam("orderId") Long orderId) {
		orderService.deleteOrder(orderId);
		return CommonResult.success(null);
	}
}
