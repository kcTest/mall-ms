package com.zkc.mall.portal.dao;

import com.zkc.mall.mbg.model.PmsBrand;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HomeDao {
	
	List<PmsBrand> getRecommendBrandList(@Param("offset") int offset, @Param("pageSize") Integer pageSize);
}
