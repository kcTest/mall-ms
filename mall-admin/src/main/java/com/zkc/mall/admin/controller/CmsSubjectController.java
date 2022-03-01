package com.zkc.mall.admin.controller;

import com.zkc.mall.admin.service.CmsSubjectService;
import com.zkc.mall.common.api.CommonPage;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.mbg.model.CmsSubject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Tag(name = "CmsSubjectController", description = "商品专题管理")

@RestController
@RequestMapping("/subject")
public class CmsSubjectController {
	
	@Autowired
	private CmsSubjectService subjectService;
	
	@Operation(summary = "获取全部商品专题")
	@GetMapping("/listAll")
	@ResponseBody
	public CommonResult<List<CmsSubject>> listAll() {
		List<CmsSubject> subjectList = subjectService.listAll();
		return CommonResult.success(subjectList);
	}
	
	@Operation(summary = "根据专题名称分页获取专题")
	@GetMapping("/list")
	@ResponseBody
	public CommonResult<CommonPage<CmsSubject>> list(@RequestParam(value = "keyword", required = false) String keyword,
													 @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
													 @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
		List<CmsSubject> subjectList = subjectService.list(keyword, pageSize, pageNum);
		return CommonResult.success(CommonPage.restPage(subjectList));
	}
}
