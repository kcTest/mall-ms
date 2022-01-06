package com.zkc.mall.admin.controller;

import com.zkc.mall.admin.dto.SmsCouponParam;
import com.zkc.mall.admin.service.PmsSkuStockService;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.mbg.model.PmsSkuStock;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api("sku商品库存管理")
@RestController
@RequestMapping(value = "/sku")
public class PmsSkuStockController {
	
	@Resource
	private PmsSkuStockService skuStockService;
	
	@ApiOperation("根据商品编号和货号查询sku库存")
	@GetMapping("/{pid}")
	@ResponseBody
	public CommonResult<List<PmsSkuStock>> getList(@PathVariable Long pid, @RequestParam(value = "skuCode", required = false) String skuCode) {
		List<PmsSkuStock> skuStockList = skuStockService.getList(pid, skuCode);
		return CommonResult.success(skuStockList);
	}
	
	@ApiOperation("批量更新库存信息")
	@PostMapping("/update")
	@ResponseBody
	public CommonResult<?> update(@RequestBody List<PmsSkuStock> skuStockList) {
		int count = skuStockService.update(skuStockList);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
}
