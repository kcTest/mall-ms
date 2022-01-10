package com.zkc.mall.portal.service;

import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.common.domain.UserDto;
import com.zkc.mall.mbg.model.UmsMember;
import org.springframework.transaction.annotation.Transactional;

public interface UmsMemberService {
	
	UmsMember getCurrentMember();
	
	@Transactional
	void register(String username, String password, String telephone, String authCode);
	
	CommonResult<?> login(String username, String password);
	
	String generateAuthCode(String telephone);
	
	@Transactional
	void updatePassword(String telephone, String password, String authCode);
	
	UserDto loadByUsername(String username);
}
