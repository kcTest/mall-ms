package com.zkc.mall.portal.controller;

import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.mbg.model.UmsMemberReceiveAddress;
import com.zkc.mall.portal.service.UmsMemberReceiveAddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "UmsMemberReceiveAddressController", description = "会员收货地址管理")

@RestController
@RequestMapping("/member/address")
public class UmsMemberReceiveAddressController {
	
	@Autowired
	private UmsMemberReceiveAddressService receiverAddressService;
	
	@Operation(summary = "添加收货地址")
	@PostMapping("/add")
	@ResponseBody
	public CommonResult<?> add(@RequestBody UmsMemberReceiveAddress receiverAddress) {
		int count = receiverAddressService.add(receiverAddress);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@Operation(summary = "删除收货地址")
	@PostMapping("/delete/{id}")
	@ResponseBody
	public CommonResult<?> delete(@PathVariable Long id) {
		int count = receiverAddressService.delete(id);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@Operation(summary = "修改收货地址")
	@PostMapping("/update/{id}")
	@ResponseBody
	public CommonResult<?> update(@PathVariable Long id, @RequestBody UmsMemberReceiveAddress receiveAddress) {
		int count = receiverAddressService.update(id, receiveAddress);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@Operation(summary = "显示所有收货地址")
	@GetMapping("/list")
	@ResponseBody
	public CommonResult<List<UmsMemberReceiveAddress>> list() {
		List<UmsMemberReceiveAddress> memberReceiveAddressList = receiverAddressService.list();
		return CommonResult.success(memberReceiveAddressList);
	}
	
	@Operation(summary = "获取收货地址详情")
	@GetMapping("/{id}")
	@ResponseBody
	public CommonResult<UmsMemberReceiveAddress> getItem(@PathVariable Long id) {
		UmsMemberReceiveAddress address = receiverAddressService.getItem(id);
		return CommonResult.success(address);
	}
}
