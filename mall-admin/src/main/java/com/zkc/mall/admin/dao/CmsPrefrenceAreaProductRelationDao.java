package com.zkc.mall.admin.dao;

import com.zkc.mall.mbg.model.CmsPrefrenceAreaProductRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CmsPrefrenceAreaProductRelationDao {
	
	int insertList(@Param("list") List<CmsPrefrenceAreaProductRelation> prefrenceAreaProductRelationList);
	
}
