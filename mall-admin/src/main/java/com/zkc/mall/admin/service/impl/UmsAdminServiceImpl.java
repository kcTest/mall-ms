package com.zkc.mall.admin.service.impl;

import com.zkc.mall.admin.service.UmsAdminService;
import com.zkc.mall.common.domain.UserDto;
import com.zkc.mall.mbg.mapper.UmsAdminMapper;
import com.zkc.mall.mbg.model.UmsAdmin;
import com.zkc.mall.mbg.model.UmsAdminExample;
import com.zkc.mall.mbg.model.UmsRole;

import javax.annotation.Resource;
import java.util.List;

public class UmsAdminServiceImpl implements UmsAdminService {
	
	@Resource
	private UmsAdminMapper adminMapper;
	
	@Override
	public UmsAdmin getAdminByUsername(String username) {
		
		UmsAdminExample example = new UmsAdminExample();
		example.createCriteria().andUsernameEqualTo(username);
		List<UmsAdmin> adminList = adminMapper.selectByExample(example);
		if (adminList != null && adminList.size() > 0) {
			return adminList.get(0);
		}
		
		return null;
	}
	
	@Override
	public List<UmsRole> getRoleList(long adminId) {
		return null;
	}
	
	@Override
	public UserDto loadUserByUsername(String username) {
		UmsAdmin admin = getAdminByUsername(username);
		if (admin!=null){
			
		}
		return null;
	}
}
