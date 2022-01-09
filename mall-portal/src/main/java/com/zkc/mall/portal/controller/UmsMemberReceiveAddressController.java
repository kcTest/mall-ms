package com.zkc.mall.portal.controller;

import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.mbg.model.UmsMemberReceiveAddress;
import com.zkc.mall.portal.service.UmsMemberReceiveAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
	
	@ApiOperation("删除收货地址")
	@PostMapping("/delete/{id}")
	@ResponseBody
	public CommonResult<?> delete(@PathVariable Long id) {
		int count = receiverAddressService.delete(id);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("修改收货地址")
	@PostMapping("/update/{id}")
	@ResponseBody
	public CommonResult<?> update(@PathVariable Long id, @RequestBody UmsMemberReceiveAddress receiveAddress) {
		int count = receiverAddressService.update(id, receiveAddress);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("显示所有收货地址")
	@GetMapping("/list")
	@ResponseBody
	public CommonResult<List<UmsMemberReceiveAddress>> list() {
		List<UmsMemberReceiveAddress> memberReceiveAddressList = receiverAddressService.list();
		return CommonResult.success(memberReceiveAddressList);
	}
	
	@ApiOperation("获取收货地址详情")
	@GetMapping("/{id}}")
	@ResponseBody
	public CommonResult<UmsMemberReceiveAddress> getItem(@PathVariable Long id) {
		UmsMemberReceiveAddress address = receiverAddressService.getItem(id);
		return CommonResult.success(address);
	}
}
