package com.zkc.mall.portal.controller;

import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.portal.domain.ConfirmOrderResult;
import com.zkc.mall.portal.domain.OrderParam;
import com.zkc.mall.portal.service.OmsPortalOrderService;
import io.swagger.annotations.Api;
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
}
