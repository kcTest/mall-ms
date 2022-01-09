package com.zkc.mall.portal.service;

import com.zkc.mall.mbg.model.UmsMember;
import org.springframework.transaction.annotation.Transactional;

public interface UmsMemberService {
	
	UmsMember getCurrentMember();
	
	@Transactional
	void register(String username, String password, String telephone, String authCode);
}
