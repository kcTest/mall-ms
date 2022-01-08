package com.zkc.mall.admin.controller;

import com.zkc.mall.admin.service.CmsSubjectService;
import com.zkc.mall.common.api.CommonPage;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.mbg.model.CmsSubject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api("商品专题管理")
@RestController
@RequestMapping("/subject")
public class CmsSubjectController {
	
	@Resource
	private CmsSubjectService subjectService;
	
	@ApiOperation("获取全部商品专题")
	@GetMapping("/listAll")
	@ResponseBody
	public CommonResult<List<CmsSubject>> listAll() {
		List<CmsSubject> subjectList = subjectService.listAll();
		return CommonResult.success(subjectList);
	}
	
	@ApiOperation("根据专题名称分页获取专题")
	@PostMapping("/list")
	@ResponseBody
	public CommonResult<CommonPage<CmsSubject>> list(@RequestParam("keyword") String keyword,
													 @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
													 @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
		List<CmsSubject> subjectList = subjectService.list(keyword, pageSize, pageNum);
		return CommonResult.success(CommonPage.restPage(subjectList));
	}
}
