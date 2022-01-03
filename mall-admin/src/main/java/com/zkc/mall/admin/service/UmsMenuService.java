package com.zkc.mall.admin.service;

import com.zkc.mall.admin.dto.UmsMenuNode;
import com.zkc.mall.mbg.model.UmsMenu;

import java.util.List;

public interface UmsMenuService {
	
	int create(UmsMenu umsmenu);
	
	int update(Long id, UmsMenu umsmenu);
	
	UmsMenu getItem(Long id);
	
	int delete(Long id);
	
	List<UmsMenu> list(Long parentId, Integer pageSize, Integer pageNum);
	
	List<UmsMenuNode> treeList();
	
	int updateHidden(Long id, Integer hidden);
}
