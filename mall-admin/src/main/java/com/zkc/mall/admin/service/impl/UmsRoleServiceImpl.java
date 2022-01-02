package com.zkc.mall.admin.service.impl;

import com.zkc.mall.admin.dao.UmsRoleDao;
import com.zkc.mall.admin.service.UmsRoleService;
import com.zkc.mall.mbg.model.UmsMenu;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UmsRoleServiceImpl implements UmsRoleService {
	
	@Resource
	private UmsRoleDao umsRoleDao;
	
	@Override
	public List<UmsMenu> getMenuList(Long adminId) {
		return umsRoleDao.getMenuList(adminId);
	}
}
