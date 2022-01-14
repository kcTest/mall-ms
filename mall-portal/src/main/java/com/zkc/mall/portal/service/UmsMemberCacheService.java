package com.zkc.mall.portal.service;

import com.zkc.mall.mbg.model.UmsMember;

public interface UmsMemberCacheService {
	
	UmsMember getMember(Long id);
	
	void setMember(UmsMember member);
	
	String getAuthCode(String telephone);
	
	void setAuthCode(String telephone, String authCode);
	
	/**
	 * 删除用户缓存
	 */
	void delMember(Long id);
}
