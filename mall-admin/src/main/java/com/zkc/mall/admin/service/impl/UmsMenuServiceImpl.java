package com.zkc.mall.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.zkc.mall.admin.dto.UmsMenuNode;
import com.zkc.mall.admin.service.UmsMenuService;
import com.zkc.mall.mbg.mapper.UmsMenuMapper;
import com.zkc.mall.mbg.model.UmsMenu;
import com.zkc.mall.mbg.model.UmsMenuExample;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UmsMenuServiceImpl implements UmsMenuService {
	
	@Resource
	private UmsMenuMapper menuMapper;
	
	@Override
	public int create(UmsMenu umsmenu) {
		umsmenu.setCreateTime(new Date());
		updateLevel(umsmenu);
		return menuMapper.insert(umsmenu);
	}
	
	@Override
	public int update(Long id, UmsMenu umsmenu) {
		umsmenu.setId(id);
		updateLevel(umsmenu);
		return menuMapper.updateByPrimaryKey(umsmenu);
	}
	
	@Override
	public UmsMenu getItem(Long id) {
		return menuMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public int delete(Long id) {
		return menuMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public List<UmsMenu> list(Long parentId, Integer pageSize, Integer pageNum) {
		PageHelper.startPage(pageNum, pageSize);
		UmsMenuExample umsMenuExample = new UmsMenuExample();
		umsMenuExample.setOrderByClause("sort desc");
		umsMenuExample.createCriteria().andParentIdEqualTo(parentId);
		return menuMapper.selectByExample(umsMenuExample);
	}
	
	@Override
	public List<UmsMenuNode> treeList() {
		List<UmsMenu> umsMenuList = menuMapper.selectByExample(new UmsMenuExample());
		return umsMenuList.stream()
				.filter(i -> i.getParentId().equals(0))
				.map(i -> convertMenuNode(i, umsMenuList)).collect(Collectors.toList());
	}
	
	@Override
	public int updateHidden(Long id, Integer hidden) {
		UmsMenu umsMenu = new UmsMenu();
		umsMenu.setId(id);
		umsMenu.setHidden(hidden);
		return menuMapper.updateByPrimaryKeySelective(umsMenu);
	}
	
	private UmsMenuNode convertMenuNode(UmsMenu umsMenu, List<UmsMenu> umsMenuList) {
		UmsMenuNode umsMenuNode = new UmsMenuNode();
		BeanUtils.copyProperties(umsMenu, umsMenuNode);
		umsMenuNode.setChildren(
				umsMenuList
						.stream()
						.filter(i -> i.getParentId().equals(umsMenu.getId()))
						.map(i -> convertMenuNode(i, umsMenuList)).collect(Collectors.toList())
		);
		
		return umsMenuNode;
	}
	
	/**
	 * 修改菜单层级
	 */
	private void updateLevel(UmsMenu umsmenu) {
		umsmenu.setLevel(0);
		if (umsmenu.getParentId() == null) {
			return;
		}
		UmsMenu parent = menuMapper.selectByPrimaryKey(umsmenu.getParentId());
		if (parent != null) {
			umsmenu.setLevel(parent.getLevel() + 1);
		}
	}
}
