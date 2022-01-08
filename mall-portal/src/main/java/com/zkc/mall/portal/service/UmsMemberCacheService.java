package com.zkc.mall.portal.service;

import com.zkc.mall.mbg.model.UmsMember;

public interface UmsMemberCacheService {
	
	UmsMember getMember(Long id);
	
	void setMember(UmsMember member);
}
