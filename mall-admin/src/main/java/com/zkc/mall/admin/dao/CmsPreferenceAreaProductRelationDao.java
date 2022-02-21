package com.zkc.mall.admin.dao;

import com.zkc.mall.mbg.model.CmsPreferenceAreaProductRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CmsPreferenceAreaProductRelationDao {
	
	int insertList(@Param("list") List<CmsPreferenceAreaProductRelation> preferenceAreaProductRelationList);
	
}
