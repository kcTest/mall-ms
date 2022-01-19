package com.zkc.mall.admin.component;

import com.zkc.mall.admin.service.UmsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ResourceRoleRulesHolder {
	
	@Autowired
	private UmsResourceService resourceService;
	
	@PostConstruct
	public void initResourceRolesMap() {
		resourceService.initResourceRolesMap();
	}
}
