package com.zkc.mall.admin.component;

import com.zkc.mall.admin.service.UmsResourceService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
public class ResourceRoleRuleHolder {
	
	@Resource
	private UmsResourceService resourceService;
	
	@PostConstruct
	public void initResourceRoleMap() {
		resourceService.initResourceRoleMap();
	}
}
