package com.zkc.mall.admin.controller;

import com.zkc.mall.admin.dto.*;
import com.zkc.mall.admin.service.OmsOrderService;
import com.zkc.mall.common.api.CommonPage;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.mbg.model.OmsOrder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Tag(name = "OmsOrderController", description = "订单管理")

@RestController
@RequestMapping("/order")
public class OmsOrderController {
	
	@Autowired
	private OmsOrderService orderService;
	
	@Operation(summary = "分页查询订单")
	@GetMapping("/list")
	@ResponseBody
	public CommonResult<CommonPage<OmsOrder>> list(
			OmsOrderQueryParam orderQueryParam,
			@RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
		List<OmsOrder> orderList = orderService.list(orderQueryParam, pageSize, pageNum);
		return CommonResult.success(CommonPage.restPage(orderList));
	}
	
	@Operation(summary = "批量发货")
	@PostMapping("/update/delivery")
	@ResponseBody
	public CommonResult<?> delivery(@RequestBody List<OmsOrderDeliveryParam> deliveryParamList) {
		int count = orderService.delivery(deliveryParamList);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@Operation(summary = "批量关闭订单")
	@PostMapping("/update/close")
	@ResponseBody
	public CommonResult<?> close(@RequestParam("ids") List<Long> ids, @RequestParam("note") String note) {
		int count = orderService.close(ids, note);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@Operation(summary = "批量删除订单")
	@PostMapping("/update/delete")
	@ResponseBody
	public CommonResult<?> delete(@RequestParam("ids") List<Long> ids) {
		int count = orderService.delete(ids);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@Operation(summary = "获取单个订单详情：订单信息 商品信息 操作记录")
	@GetMapping("/{id}")
	@ResponseBody
	public CommonResult<OmsOrderDetail> detail(@PathVariable Long id) {
		OmsOrderDetail orderDetail = orderService.detail(id);
		return CommonResult.success(orderDetail);
	}
	
	@Operation(summary = "修改收货人信息")
	@PostMapping("/update/receiverInfo")
	@ResponseBody
	public CommonResult<?> updateReceiverInfo(@RequestBody OmsReceiverInfoParam receiverInfoParam) {
		int count = orderService.updateReceiverInfo(receiverInfoParam);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@Operation(summary = "修改订单费用信息")
	@PostMapping("/update/moneyInfo")
	@ResponseBody
	public CommonResult<?> updateMoneyInfo(@RequestBody OmsMoneyInfoParam moneyInfoParam) {
		int count = orderService.updateMoneyInfo(moneyInfoParam);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@Operation(summary = "备注订单")
	@PostMapping("/update/note")
	@ResponseBody
	public CommonResult<?> updateNode(@RequestParam("id") Long id,
									  @RequestParam("note") String note,
									  @RequestParam("status") Integer status) {
		int count = orderService.updateNode(id, note, status);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
}
