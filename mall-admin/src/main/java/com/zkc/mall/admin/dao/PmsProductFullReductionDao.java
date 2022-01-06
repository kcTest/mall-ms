package com.zkc.mall.admin.dao;

import com.zkc.mall.mbg.model.PmsProductFullReduction;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PmsProductFullReductionDao {
	
	int insertList(@Param("list") List<PmsProductFullReduction> productFullReductionList);
}
