package com.zkc.mall.admin.controller;

import com.zkc.mall.admin.service.OmsOrderReturnReasonService;
import com.zkc.mall.common.api.CommonPage;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.mbg.model.OmsOrderReturnReason;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Tag(name = "OmsOrderReturnReasonController", description = "退货原因管理")
@CrossOrigin
@RestController
@RequestMapping("/returnReason")
public class OmsOrderReturnReasonController {
	
	@Autowired
	private OmsOrderReturnReasonService orderReturnReasonService;
	
	@Operation(summary ="添加退货原因")
	@PostMapping("/create")
	@ResponseBody
	public CommonResult<?> create(@RequestBody OmsOrderReturnReason returnReason) {
		int count = orderReturnReasonService.create(returnReason);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@Operation(summary ="修改退货原因")
	@PostMapping("/update/{id}")
	@ResponseBody
	public CommonResult<?> update(@PathVariable Long id, @RequestBody OmsOrderReturnReason returnReason) {
		int count = orderReturnReasonService.update(id, returnReason);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	
	@Operation(summary ="批量删除退货原因")
	@PostMapping("/delete")
	@ResponseBody
	public CommonResult<?> delete(@RequestParam("ids") List<Long> ids) {
		int count = orderReturnReasonService.delete(ids);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@Operation(summary ="分页查询退货原因")
	@GetMapping("/list")
	@ResponseBody
	public CommonResult<CommonPage<OmsOrderReturnReason>> list(@RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
															   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
		List<OmsOrderReturnReason> returnReasonList = orderReturnReasonService.list(pageSize, pageNum);
		return CommonResult.success(CommonPage.restPage(returnReasonList));
	}
	
	@Operation(summary ="获取单个退货原因详情")
	@GetMapping("/{id}")
	@ResponseBody
	public CommonResult<OmsOrderReturnReason> getItem(@PathVariable Long id) {
		OmsOrderReturnReason returnReason = orderReturnReasonService.getItem(id);
		return CommonResult.success(returnReason);
	}
	
	@Operation(summary ="批量修改退货原因启用状态")
	@PostMapping("/update/status")
	@ResponseBody
	public CommonResult<?> updateStatus(@RequestParam("ids") List<Long> ids, @RequestParam("status") Integer status) {
		int count = orderReturnReasonService.updateStatus(ids, status);
		return CommonResult.success(count);
	}
}
