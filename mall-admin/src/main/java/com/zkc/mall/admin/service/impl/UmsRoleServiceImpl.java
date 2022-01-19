package com.zkc.mall.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.zkc.mall.admin.dao.UmsRoleDao;
import com.zkc.mall.admin.service.UmsResourceService;
import com.zkc.mall.admin.service.UmsRoleService;
import com.zkc.mall.mbg.mapper.UmsRoleMapper;
import com.zkc.mall.mbg.mapper.UmsRoleMenuRelationMapper;
import com.zkc.mall.mbg.mapper.UmsRoleResourceRelationMapper;
import com.zkc.mall.mbg.model.*;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.Date;
import java.util.List;

@Service
public class UmsRoleServiceImpl implements UmsRoleService {
	
	@Autowired
	private UmsRoleDao umsRoleDao;
	@Autowired
	private UmsRoleMapper roleMapper;
	@Autowired
	private UmsResourceService resourceService;
	@Autowired
	private UmsRoleMenuRelationMapper roleMenuRelationMapper;
	@Autowired
	private UmsRoleResourceRelationMapper roleResourceRelationMapper;
	
	@Override
	public List<UmsMenu> getMenuList(Long adminId) {
		return umsRoleDao.getMenuList(adminId);
	}
	
	@Override
	public int create(UmsRole role) {
		role.setCreateTime(new Date());
		role.setAdminCount(0);
		role.setSort(0);
		return roleMapper.insert(role);
	}
	
	@Override
	public int update(Long id, UmsRole role) {
		role.setId(id);
		return roleMapper.updateByPrimaryKeySelective(role);
	}
	
	@Override
	public int delete(List<Long> ids) {
		UmsRoleExample umsRoleExample = new UmsRoleExample();
		umsRoleExample.createCriteria().andIdIn(ids);
		int count = roleMapper.deleteByExample(umsRoleExample);
		resourceService.initResourceRolesMap();
		return count;
	}
	
	@Override
	public List<UmsRole> list() {
		return roleMapper.selectByExample(new UmsRoleExample());
	}
	
	@Override
	public List<UmsRole> list(String keyword, Integer pageSize, Integer pageNum) {
		PageHelper.startPage(pageNum, pageSize);
		UmsRoleExample umsRoleExample = new UmsRoleExample();
		if (!StrUtil.isEmpty(keyword)) {
			umsRoleExample.createCriteria().andNameLike("%" + keyword + "%");
		}
		return roleMapper.selectByExample(umsRoleExample);
	}
	
	@Override
	public List<UmsMenu> listMenu(Long roleId) {
		return umsRoleDao.getMenuListByRoleId(roleId);
	}
	
	@Override
	public List<UmsResource> listResource(Long roleId) {
		return umsRoleDao.getResourceListByRoleId(roleId);
	}
	
	@Override
	public int allocMenu(Long roleId, List<Long> menuIds) {
		
		UmsRoleMenuRelationExample umsRoleMenuRelationExample = new UmsRoleMenuRelationExample();
		umsRoleMenuRelationExample.createCriteria().andRoleIdEqualTo(roleId);
		roleMenuRelationMapper.deleteByExample(umsRoleMenuRelationExample);
		
		for (Long menuId : menuIds) {
			UmsRoleMenuRelation umsRoleMenuRelation = new UmsRoleMenuRelation();
			umsRoleMenuRelation.setRoleId(roleId);
			umsRoleMenuRelation.setMenuId(menuId);
			roleMenuRelationMapper.insert(umsRoleMenuRelation);
		}
		
		return menuIds.size();
	}
	
	@Override
	public int allocResource(Long roleId, List<Long> resourceIds) {
		
		UmsRoleResourceRelationExample umsRoleResourceRelationExample = new UmsRoleResourceRelationExample();
		umsRoleResourceRelationExample.createCriteria().andRoleIdEqualTo(roleId);
		roleResourceRelationMapper.deleteByExample(umsRoleResourceRelationExample);
		
		for (Long resourceId : resourceIds) {
			UmsRoleResourceRelation umsRoleResourceRelation = new UmsRoleResourceRelation();
			umsRoleResourceRelation.setRoleId(roleId);
			umsRoleResourceRelation.setResourceId(resourceId);
			roleResourceRelationMapper.insert(umsRoleResourceRelation);
		}
		
		resourceService.initResourceRolesMap();
		return resourceIds.size();
	}
}
