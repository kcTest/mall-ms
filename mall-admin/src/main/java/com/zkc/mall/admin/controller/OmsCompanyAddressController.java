package com.zkc.mall.admin.controller;

import com.zkc.mall.admin.service.OmsCompanyAddressService;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.mbg.model.OmsCompanyAddress;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "OmsCompanyAddressController", description = "收货地址管理")

@RestController
@RequestMapping("/companyAddress")
public class OmsCompanyAddressController {
	
	@Autowired
	private OmsCompanyAddressService orderService;
	
	@Operation(summary ="获取所有收货地址")
	@GetMapping("/list")
	@ResponseBody
	public CommonResult<List<OmsCompanyAddress>> list() {
		List<OmsCompanyAddress> orderList = orderService.list();
		return CommonResult.success(orderList);
	}
}
