package com.zkc.mall.admin.service;

import com.zkc.mall.mbg.model.UmsResource;

import java.util.List;
import java.util.Map;

/**
 * 后台可访问资源管理
 */
public interface UmsResourceService {
	
	
	Map<String, List<String>> initResourceRolesMap();
	
	int create(UmsResource umsResource);
	
	int update(Long id, UmsResource umsResource);
	
	UmsResource getItem(Long id);
	
	int delete(Long id);
	
	List<UmsResource> list(Long categoryId, String nameKeyword, String urlKeyword, Integer pageSize, Integer pageNum);
	
	List<UmsResource> listAll();
	
}
