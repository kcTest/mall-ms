package com.zkc.mall.portal.controller;

import com.zkc.mall.common.api.CommonPage;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.portal.domain.ConfirmOrderResult;
import com.zkc.mall.portal.domain.OmsOrderDetail;
import com.zkc.mall.portal.domain.OrderParam;
import com.zkc.mall.portal.service.OmsPortalOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Map;
@Tag(name = "OmsPortalOrderController", description = "订单管理")
@CrossOrigin
@RestController
@RequestMapping("/order")
public class OmsPortalOrderController {
	
	@Autowired
	private OmsPortalOrderService orderService;
	
	@Operation(summary = "根据购物车信息生成确认订单信息")
	@PostMapping("/generateConfirmOrder")
	@ResponseBody
	public CommonResult<ConfirmOrderResult> generateConfirmOrder(@RequestParam("cartIds") List<Long> cartIds) {
		ConfirmOrderResult orderResult = orderService.generateConfirmOrder(cartIds);
		return CommonResult.success(orderResult);
	}
	
	@Operation(summary = "根据购物车信息生成订单")
	@PostMapping("/generateOrder")
	@ResponseBody
	public CommonResult<?> generateOrder(@RequestBody OrderParam orderParam) {
		Map<String, Object> result = orderService.generateOrder(orderParam);
		return CommonResult.success(result, "下单成功");
	}
	
	@Operation(summary = "用户支付成功的回调")
	@PostMapping("/paySuccess")
	@ResponseBody
	public CommonResult<?> paySuccess(@RequestParam("orderId") Long orderId, @RequestParam("payType") Integer payType) {
		int count = orderService.paySuccess(orderId, payType);
		return CommonResult.success(count, "支付成功");
	}
	
	@Operation(summary = "自动取消超时订单")
	@PostMapping("/cancelTimeOutOrder")
	@ResponseBody
	public CommonResult<?> cancelTimeOutOrder() {
		int count = orderService.cancelTimeOutOrder();
		return CommonResult.success(null);
	}
	
	@Operation(summary = "取消单个超时订单")
	@PostMapping("/cancelOrder")
	@ResponseBody
	public CommonResult<?> cancelOrder(@RequestParam("orderId") Long orderId) {
		orderService.sendDelayMessageCancelOrder(orderId);
		return CommonResult.success(null);
	}
	
	@Operation(summary = "按状态分页获取用户订单列表")
	@Parameter(name = "status", description = "订单状态:-1->全部 0->待付款 1->待发货 2->已发货 3->已完成 4->已关闭",
			in = ParameterIn.QUERY,
			content = @Content(schema = @Schema(defaultValue = "-1", allowableValues = "-1,0,1,2,3,4", type = "int")))
	@GetMapping("/list")
	@ResponseBody
	public CommonResult<CommonPage<OmsOrderDetail>> list(
			@RequestParam(value = "status", required = false) Integer status,
			@RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
			@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum) {
		List<OmsOrderDetail> orderDetailList = orderService.list(status, pageSize, pageNum);
		return CommonResult.success(CommonPage.restPage(orderDetailList));
	}
	
	@Operation(summary = "根据ID获取订单详情")
	@PostMapping("/detail/{orderId}")
	@ResponseBody
	public CommonResult<OmsOrderDetail> detail(@RequestParam("orderId") Long orderId) {
		OmsOrderDetail orderDetail = orderService.detail(orderId);
		return CommonResult.success(orderDetail);
	}
	
	@Operation(summary = "用户取消订单")
	@PostMapping("/cancelUserOrder")
	@ResponseBody
	public CommonResult<?> cancelUserOrder(@RequestParam("orderId") Long orderId) {
		orderService.cancelOrder(orderId);
		return CommonResult.success(null);
	}
	
	@Operation(summary = "用户确认收货")
	@PostMapping("/confirmReceiveOrder")
	@ResponseBody
	public CommonResult<?> confirmReceiveOrder(@RequestParam("orderId") Long orderId) {
		orderService.confirmReceiveOrder(orderId);
		return CommonResult.success(null);
	}
	
	@Operation(summary = "用户删除订单")
	@PostMapping("/deleteOrder")
	@ResponseBody
	public CommonResult<?> deleteOrder(@RequestParam("orderId") Long orderId) {
		orderService.deleteOrder(orderId);
		return CommonResult.success(null);
	}
}
