package com.zkc.mall.admin.dao;

import com.zkc.mall.mbg.model.UmsAdminRoleRelation;
import com.zkc.mall.mbg.model.UmsRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UmsAdminRoleRelationDao {
	
	List<UmsRole> getRoleList(@Param("adminId") Long adminId);
	
	int insertList(@Param("list") List<UmsAdminRoleRelation> relationList);
}
