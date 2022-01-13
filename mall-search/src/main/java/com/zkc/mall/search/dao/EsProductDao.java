package com.zkc.mall.search.dao;

import com.zkc.mall.search.domain.EsProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EsProductDao {
	
	List<EsProduct> getAllEsProductList(@Param("id") Long id);
	
}
