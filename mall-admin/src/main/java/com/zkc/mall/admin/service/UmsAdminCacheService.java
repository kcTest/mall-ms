package com.zkc.mall.admin.service;

import com.zkc.mall.mbg.model.UmsAdmin;

public interface UmsAdminCacheService {
	
	/**
	 * 获取缓存的后台用户信息
	 */
	UmsAdmin getAdmin(Long adminId);
	
	/**
	 * 缓存后台用户信息
	 */
	void setAdmin(UmsAdmin admin);
	
	void delAdmin(Long adminId);
}
