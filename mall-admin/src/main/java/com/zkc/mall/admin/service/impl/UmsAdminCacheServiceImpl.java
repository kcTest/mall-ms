package com.zkc.mall.admin.service.impl;

import com.zkc.mall.admin.service.UmsAdminCacheService;
import com.zkc.mall.common.service.RedisService;
import com.zkc.mall.mbg.model.UmsAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UmsAdminCacheServiceImpl implements UmsAdminCacheService {
	
	@Resource
	private RedisService redisService;
	
	@Value("${redis.database}")
	private String REDIS_DATABASE;
	
	@Value("${redis.key.admin}")
	private String REDIS_KEY_ADMIN;
	
	@Value("${redis.expire.common}")
	private Long REDIS_EXPIRE;
	
	@Override
	public UmsAdmin getAdmin(Long adminId) {
		
		String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + adminId;
		UmsAdmin umsAdmin = (UmsAdmin) redisService.get(key);
		return umsAdmin;
	}
	
	@Override
	public void setAdmin(UmsAdmin admin) {
		
		String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + admin.getId();
		redisService.set(key, admin, REDIS_EXPIRE);
	}
}
