package com.zkc.mall.admin.dao;

import com.zkc.mall.mbg.model.PmsProductLadder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PmsProductLadderDao {
	
	int insertList(@Param("list") List<PmsProductLadder> productLadderList);
}
