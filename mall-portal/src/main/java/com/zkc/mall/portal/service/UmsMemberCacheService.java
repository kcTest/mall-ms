package com.zkc.mall.portal.service;

import com.zkc.mall.common.annotation.CacheException;
import com.zkc.mall.mbg.model.UmsMember;

public interface UmsMemberCacheService {
	
	UmsMember getMember(Long id);
	
	void setMember(UmsMember member);
	
	@CacheException
	String getAuthCode(String telephone);
	
	@CacheException
	void setAuthCode(String telephone, String authCode);
	
	/**
	 * 删除用户缓存
	 */
	void delMember(Long id);
}
