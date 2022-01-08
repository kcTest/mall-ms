package com.zkc.mall.admin.controller;

import com.zkc.mall.admin.service.OmsCompanyAddressService;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.mbg.model.OmsCompanyAddress;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api("收货地址管理")
@RestController
@RequestMapping("/companyAddress")
public class OmsCompanyAddressController {
	
	@Resource
	private OmsCompanyAddressService orderService;
	
	@ApiOperation("获取所有收货地址")
	@GetMapping("/list")
	@ResponseBody
	public CommonResult<List<OmsCompanyAddress>> list() {
		List<OmsCompanyAddress> orderList = orderService.list();
		return CommonResult.success(orderList);
	}
}
