package com.zkc.mall.admin.controller;

import com.zkc.mall.admin.service.CmsPreferenceAreaService;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.mbg.model.CmsPrefrenceArea;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api("商品优选管理")
@RestController
@RequestMapping("/preferenceArea")
public class CmsPreferenceAreaController {
	
	@Resource
	private CmsPreferenceAreaService preferenceAreaService;
	
	@ApiOperation("获取全部商品优选")
	@GetMapping("/listAll")
	@ResponseBody
	public CommonResult<List<CmsPrefrenceArea>> listAll() {
		List<CmsPrefrenceArea> preferenceAreaList = preferenceAreaService.listAll();
		return CommonResult.success(preferenceAreaList);
	}
}
