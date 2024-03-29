package com.zkc.mall.portal.service.impl;

import com.zkc.mall.common.annotation.CacheException;
import com.zkc.mall.common.service.RedisService;
import com.zkc.mall.mbg.model.UmsMember;
import com.zkc.mall.portal.service.UmsMemberCacheService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UmsMemberCacheServiceImpl implements UmsMemberCacheService {
	
	@Value("${redis.database}")
	private String REDIS_DATABASE;
	
	@Value("${redis.expire.common}")
	private Long REDIS_EXPIRE;
	
	@Value("${redis.expire.authCode}")
	private Long REDIS_EXPIRE_AUTH_CODE;
	
	@Value("${redis.key.member}")
	private String REDIS_KEY_MEMBER;
	
	@Value("${redis.key.authCode}")
	private String REDIS_KEY_AUTH_CODE;
	
	@Autowired
	private RedisService redisService;
	
	@Override
	public UmsMember getMember(Long id) {
		String key = REDIS_DATABASE + ":" + REDIS_KEY_MEMBER + ":" + id;
		return (UmsMember) redisService.get(key);
	}
	
	@Override
	public void setMember(UmsMember member) {
		String key = REDIS_DATABASE + ":" + REDIS_KEY_MEMBER + ":" + member.getId();
		redisService.set(key, member, REDIS_EXPIRE);
	}
	
	@CacheException
	@Override
	public String getAuthCode(String telephone) {
		String key = REDIS_DATABASE + ":" + REDIS_KEY_AUTH_CODE + ":" + telephone;
		return (String) redisService.get(key);
		
	}
	
	@CacheException
	@Override
	public void setAuthCode(String telephone, String authCode) {
		String key = REDIS_DATABASE + ":" + REDIS_KEY_AUTH_CODE + ":" + telephone;
		redisService.set(key, authCode, REDIS_EXPIRE_AUTH_CODE);
	}
	
	@Override
	public void delMember(Long id) {
		String key = REDIS_DATABASE + ":" + REDIS_KEY_MEMBER + ":" + id;
		redisService.del(key);
	}
}
