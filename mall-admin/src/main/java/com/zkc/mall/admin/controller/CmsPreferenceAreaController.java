package com.zkc.mall.admin.controller;

import com.zkc.mall.admin.service.CmsPreferenceAreaService;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.mbg.model.CmsPrefrenceArea;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "CmsPreferenceAreaController", description = "商品优选管理")
@CrossOrigin
@RestController
@RequestMapping("/preferenceArea")
public class CmsPreferenceAreaController {
	
	@Autowired
	private CmsPreferenceAreaService preferenceAreaService;
	
	@Operation(summary = "获取全部商品优选")
	@GetMapping("/listAll")
	@ResponseBody
	public CommonResult<List<CmsPrefrenceArea>> listAll() {
		List<CmsPrefrenceArea> preferenceAreaList = preferenceAreaService.listAll();
		return CommonResult.success(preferenceAreaList);
	}
}
