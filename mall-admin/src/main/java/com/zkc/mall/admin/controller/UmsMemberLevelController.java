package com.zkc.mall.admin.controller;

import com.zkc.mall.admin.service.UmsMemberLevelService;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.mbg.model.UmsMemberLevel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
@Tag(name = "UmsMemberLevelController", description = "会员等级管理")
@RequestMapping("/memberLevel")

@RestController
public class UmsMemberLevelController {
	
	@Autowired
	private UmsMemberLevelService memberLevelService;
	
	@Operation(summary ="查询所有会员等级")
	@GetMapping("/list")
	@ResponseBody
	public CommonResult<List<UmsMemberLevel>> list(@RequestParam("defaultStatus") Integer defaultStatus) {
		List<UmsMemberLevel> memberLevelList = memberLevelService.list(defaultStatus);
		return CommonResult.success(memberLevelList);
	}
}
