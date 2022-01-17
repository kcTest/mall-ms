package com.zkc.mall.portal.controller;

import com.zkc.mall.common.api.CommonPage;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.portal.domain.MemberReadHistory;
import com.zkc.mall.portal.service.MemberReadHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "MemberReadHistoryController", description = "会员商品浏览记录管理")
@CrossOrigin
@RestController
@RequestMapping("/member/readHistory")
public class MemberReadHistoryController {
	
	@Autowired
	private MemberReadHistoryService readHistoryService;
	
	@Operation(summary ="创建浏览记录")
	@PostMapping("/create")
	@ResponseBody
	public CommonResult<?> create(@RequestBody MemberReadHistory memberReadHistory) {
		int count = readHistoryService.create(memberReadHistory);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@Operation(summary ="删除浏览记录")
	@PostMapping("/delete")
	@ResponseBody
	public CommonResult<?> delete(@RequestParam("ids") List<String> ids) {
		int count = readHistoryService.delete(ids);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@Operation(summary ="清空浏览记录")
	@PostMapping("/clear")
	@ResponseBody
	public CommonResult<?> clear() {
		readHistoryService.clear();
		return CommonResult.success(null);
	}
	
	@Operation(summary ="分页获取用户浏览记录")
	@GetMapping("/list")
	@ResponseBody
	public CommonResult<CommonPage<MemberReadHistory>> list(
			@RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
			@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum) {
		List<MemberReadHistory> readHistoryList = readHistoryService.list(pageSize, pageNum);
		return CommonResult.success(CommonPage.restPage(readHistoryList));
	}
	
}
