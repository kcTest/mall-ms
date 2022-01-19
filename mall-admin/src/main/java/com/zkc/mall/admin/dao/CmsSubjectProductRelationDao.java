package com.zkc.mall.admin.dao;

import com.zkc.mall.mbg.model.CmsSubjectProductRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CmsSubjectProductRelationDao {
	
	int insertList(@Param("list") List<CmsSubjectProductRelation> subjectProductRelationList);
}
