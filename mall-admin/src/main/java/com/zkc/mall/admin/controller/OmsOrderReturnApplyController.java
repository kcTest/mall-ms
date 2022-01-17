package com.zkc.mall.admin.controller;

import com.zkc.mall.admin.dto.OmsOrderReturnApplyQueryParam;
import com.zkc.mall.admin.dto.OmsUpdateStatusParam;
import com.zkc.mall.admin.service.OmsOrderReturnApplyService;
import com.zkc.mall.common.api.CommonPage;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.mbg.model.OmsOrderReturnApply;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Tag(name = "OmsOrderReturnApplyController", description = "退货申请管理")
@CrossOrigin
@RestController
@RequestMapping("/returnApply")
public class OmsOrderReturnApplyController {
	
	@Autowired
	private OmsOrderReturnApplyService returnApplyService;
	
	@Operation(summary ="分页查询退货申请")
	@GetMapping("/list")
	@ResponseBody
	public CommonResult<CommonPage<OmsOrderReturnApply>> list(
			@RequestParam OmsOrderReturnApplyQueryParam returnApplyParam,
			@RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
		List<OmsOrderReturnApply> returnReasonApplyList = returnApplyService.list(returnApplyParam, pageSize, pageNum);
		return CommonResult.success(CommonPage.restPage(returnReasonApplyList));
	}
	
	@Operation(summary ="批量删除退货申请")
	@PostMapping("/delete")
	@ResponseBody
	public CommonResult<?> delete(@RequestParam("ids") List<Long> ids) {
		int count = returnApplyService.delete(ids);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	
	@Operation(summary ="获取单个退货申请详情")
	@GetMapping("/{id}")
	@ResponseBody
	public CommonResult<OmsOrderReturnApply> getItem(@PathVariable Long id) {
		OmsOrderReturnApply returnApply = returnApplyService.getItem(id);
		return CommonResult.success(returnApply);
	}
	
	
	@Operation(summary ="修改退货申请状态")
	@PostMapping("/update/status/{id}")
	@ResponseBody
	public CommonResult<?> updateStatus(@PathVariable Long id, @RequestBody OmsUpdateStatusParam statusParam) {
		int count = returnApplyService.updateStatus(id, statusParam);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
}
