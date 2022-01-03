package com.zkc.mall.admin.service;


import com.zkc.mall.admin.dto.UpdatePasswordParam;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.common.domain.UserDto;
import com.zkc.mall.mbg.model.UmsAdmin;
import com.zkc.mall.mbg.model.UmsRole;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UmsAdminService {
	
	CommonResult login(String username, String password);
	
	UmsAdmin getAdminByUsername(String username);
	
	List<UmsRole> getRoleList(long adminId);
	
	UserDto loadUserByUsername(String username);
	
	UmsAdmin registry(String username, String password);
	
	UmsAdmin getCurrentAdmin();
	
	List<UmsAdmin> list(String keyword, Integer pageSize, Integer pageNum);
	
	UmsAdmin getItem(Long id);
	
	int update(Long id, UmsAdmin admin);
	
	int updatePassword(UpdatePasswordParam param);
	
	int delete(Long id);
	
	@Transactional
	int updateRole(Long adminId, List<Long> roleIds);
}
