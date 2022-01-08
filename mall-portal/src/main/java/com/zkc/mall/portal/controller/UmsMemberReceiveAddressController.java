package com.zkc.mall.portal.controller;

import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.mbg.model.UmsMemberReceiveAddress;
import com.zkc.mall.portal.service.UmsMemberReceiveAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api("会员收货地址管理")
@RestController
@RequestMapping("/member/address")
public class UmsMemberReceiveAddressController {
	
	@Resource
	private UmsMemberReceiveAddressService receiverAddressService;
	
	@ApiOperation("添加收货地址")
	@PostMapping("/add")
	@ResponseBody
	public CommonResult<?> add(@RequestBody UmsMemberReceiveAddress receiverAddress) {
		int count = receiverAddressService.add(receiverAddress);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	
}
