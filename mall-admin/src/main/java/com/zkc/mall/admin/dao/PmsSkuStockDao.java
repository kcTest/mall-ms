package com.zkc.mall.admin.dao;

import com.zkc.mall.mbg.model.PmsSkuStock;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PmsSkuStockDao {
	
	int replaceList(@Param("list") List<PmsSkuStock> skuStockList);
	
	int insertList(@Param("list") List<PmsSkuStock> skuStockList);
}
