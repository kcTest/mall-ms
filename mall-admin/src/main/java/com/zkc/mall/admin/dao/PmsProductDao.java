package com.zkc.mall.admin.dao;

import com.zkc.mall.admin.dto.PmsProductResult;
import org.apache.ibatis.annotations.Param;

public interface PmsProductDao {
	
	PmsProductResult getUpdateInfo(@Param("id") Long id);
}
