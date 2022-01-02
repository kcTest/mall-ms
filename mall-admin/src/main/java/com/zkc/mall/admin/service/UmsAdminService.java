package com.zkc.mall.admin.service;


import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.common.domain.UserDto;
import com.zkc.mall.mbg.model.UmsAdmin;
import com.zkc.mall.mbg.model.UmsRole;

import java.util.List;

public interface UmsAdminService {
	
	CommonResult login(String username, String password);
	
	UmsAdmin getAdminByUsername(String username);
	
	List<UmsRole> getRoleList(long adminId);
	
	UserDto loadUserByUsername(String username);
	
	UmsAdmin registry(String username, String password);
	
	UmsAdmin getCurrentAdmin();
}
