package com.zkc.mall.admin.component;

import com.zkc.mall.admin.service.UmsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ResourceRoleRuleHolder {
	
	@Autowired
	private UmsResourceService resourceService;
	
	@PostConstruct
	public void initResourceRoleMap() {
		resourceService.initResourceRoleMap();
	}
}
