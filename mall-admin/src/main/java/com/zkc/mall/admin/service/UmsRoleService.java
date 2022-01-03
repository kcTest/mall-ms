package com.zkc.mall.admin.service;

import com.zkc.mall.mbg.model.UmsMenu;
import com.zkc.mall.mbg.model.UmsResource;
import com.zkc.mall.mbg.model.UmsRole;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UmsRoleService {
	
	/**
	 * 根据管理员ID获取对应菜单
	 */
	List<UmsMenu> getMenuList(Long adminId);
	
	int create(UmsRole role);
	
	int update(Long id, UmsRole role);
	
	int delete(List<Long> ids);
	
	List<UmsRole> list();
	
	List<UmsRole> list(String keyword, Integer pageSize, Integer pageNum);
	
	List<UmsMenu> listMenu(Long roleId);
	
	List<UmsResource> listResource(Long roleId);
	
	@Transactional
	int allocMenu(Long roleId, List<Long> menuIds);
	
	@Transactional
	int allocResource(Long roleId, List<Long> resourceIds);
}
