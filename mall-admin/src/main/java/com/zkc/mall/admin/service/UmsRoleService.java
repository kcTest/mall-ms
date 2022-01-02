package com.zkc.mall.admin.service;

import com.zkc.mall.mbg.model.UmsMenu;

import java.util.List;

public interface UmsRoleService {
	
	/**
	 * 根据管理员ID获取对应菜单
	 */
	List<UmsMenu> getMenuList(Long adminId);
}
