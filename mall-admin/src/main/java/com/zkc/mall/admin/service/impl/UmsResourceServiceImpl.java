package com.zkc.mall.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.zkc.mall.admin.service.UmsResourceService;
import com.zkc.mall.common.constant.AuthConstant;
import com.zkc.mall.common.service.RedisService;
import com.zkc.mall.mbg.mapper.UmsResourceMapper;
import com.zkc.mall.mbg.mapper.UmsRoleMapper;
import com.zkc.mall.mbg.mapper.UmsRoleResourceRelationMapper;
import com.zkc.mall.mbg.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class UmsResourceServiceImpl implements UmsResourceService {
	
	@Autowired
	private UmsRoleMapper roleMapper;
	@Autowired
	private UmsResourceMapper resourceMapper;
	@Autowired
	private UmsRoleResourceRelationMapper roleResourceRelationMapper;
	@Value("${spring.application.name}")
	private String applicationName;
	@Autowired
	private RedisService redisService;
	
	@Override
	public Map<String, List<String>> initResourceRoleMap() {
		
		List<UmsRole> roles = roleMapper.selectByExample(new UmsRoleExample());
		Map<String, List<String>> resourceRoleMap = new TreeMap<>();
		List<UmsResource> resources = resourceMapper.selectByExample(new UmsResourceExample());
		List<UmsRoleResourceRelation> relations = roleResourceRelationMapper.selectByExample(new UmsRoleResourceRelationExample());
		for (UmsResource resource : resources) {
			List<Long> roleIds = relations.stream().filter(i -> i.getResourceId().equals(resource.getId())).map(i -> i.getRoleId()).collect(Collectors.toList());
			List<String> roleIdAndName = roles.stream().filter(i -> roleIds.contains(i.getId())).map(i -> i.getId() + "_" + i.getName()).collect(Collectors.toList());
			resourceRoleMap.put("/" + applicationName + resource.getUrl(), roleIdAndName);
		}
		
		redisService.del(AuthConstant.RESOURCE_ROLES_MAP_KEY);
		redisService.hSetAll(AuthConstant.RESOURCE_ROLES_MAP_KEY, resourceRoleMap);
		return resourceRoleMap;
	}
	
	@Override
	public int create(UmsResource umsResource) {
		
		umsResource.setCreateTime(new Date());
		int count = resourceMapper.insert(umsResource);
//		initResourceRoleMap();
		return count;
	}
	
	@Override
	public int update(Long id, UmsResource umsResource) {
		umsResource.setId(id);
		int count = resourceMapper.updateByPrimaryKeySelective(umsResource);
		initResourceRoleMap();
		return count;
	}
	
	@Override
	public UmsResource getItem(Long id) {
		return resourceMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public int delete(Long id) {
		int count = resourceMapper.deleteByPrimaryKey(id);
		initResourceRoleMap();
		return count;
	}
	
	@Override
	public List<UmsResource> list(Long categoryId, String nameKeyword, String urlKeyword, Integer pageSize, Integer pageNum) {
		PageHelper.startPage(pageNum, pageSize);
		UmsResourceExample umsResourceExample = new UmsResourceExample();
		UmsResourceExample.Criteria criteria = umsResourceExample.createCriteria();
		if (categoryId != null) {
			criteria.andCategoryIdEqualTo(categoryId);
		}
		if ((!StrUtil.isEmpty(nameKeyword))) {
			criteria.andNameLike("%" + nameKeyword + "%");
		}
		if (!StrUtil.isEmpty(urlKeyword)) {
			criteria.andUrlLike("%" + urlKeyword + "%");
		}
		
		return resourceMapper.selectByExample(umsResourceExample);
	}
	
	@Override
	public List<UmsResource> listAll() {
		return resourceMapper.selectByExample(new UmsResourceExample());
	}
	
}
