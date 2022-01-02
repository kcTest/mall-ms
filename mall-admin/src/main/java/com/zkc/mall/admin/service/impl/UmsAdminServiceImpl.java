package com.zkc.mall.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.json.JSONUtil;
import com.zkc.mall.admin.dao.UmsAdminRoleRelationDao;
import com.zkc.mall.admin.service.AuthService;
import com.zkc.mall.admin.service.UmsAdminCacheService;
import com.zkc.mall.admin.service.UmsAdminService;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.common.api.ResultCode;
import com.zkc.mall.common.constant.AuthConstant;
import com.zkc.mall.common.domain.UserDto;
import com.zkc.mall.common.exception.Asserts;
import com.zkc.mall.mbg.mapper.UmsAdminLoginLogMapper;
import com.zkc.mall.mbg.mapper.UmsAdminMapper;
import com.zkc.mall.mbg.model.UmsAdmin;
import com.zkc.mall.mbg.model.UmsAdminExample;
import com.zkc.mall.mbg.model.UmsAdminLoginLog;
import com.zkc.mall.mbg.model.UmsRole;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UmsAdminServiceImpl implements UmsAdminService {
	
	@Resource
	private UmsAdminMapper adminMapper;
	@Resource
	private UmsAdminRoleRelationDao adminRoleRelationDao;
	@Resource
	private AuthService authService;
	@Resource
	private UmsAdminLoginLogMapper adminLoginLogMapper;
	@Resource
	private HttpServletRequest request;
	@Resource
	private UmsAdminCacheService adminCacheService;
	
	@Override
	public CommonResult login(String username, String password) {
		if (StrUtil.isEmpty(username) || StrUtil.isEmpty(password)) {
			Asserts.fail("用户名或密码不能为空！");
		}
		Map<String, String> params = new HashMap<>(5);
		params.put(AuthConstant.AUTH_CLIENT_ID, AuthConstant.ADMIN_CLIENT_ID);
		params.put(AuthConstant.AUTH_CLIENT_SECRETE, AuthConstant.AUTH_CLIENT_SECRETE_DEFAULT);
		params.put(AuthConstant.AUTH_GRANT_TYPE, AuthConstant.AUTH_GRANT_TYPE_DEFAULT);
		params.put(AuthConstant.AUTH_USERNAME, username);
		params.put(AuthConstant.AUTH_PASSWORD, password);
		CommonResult restResult = authService.getAccessToken(params);
		if (restResult.getCode() == ResultCode.SUCCESS.getCode() && restResult.getData() != null) {
			insertLoginLog(username);
		}
		
		return restResult;
	}
	
	private void insertLoginLog(String username) {
		UmsAdmin admin = getAdminByUsername(username);
		if (admin == null) {
			return;
		}
		UmsAdminLoginLog loginLog = new UmsAdminLoginLog();
		loginLog.setAdminId(admin.getId());
		loginLog.setCreateTime(new Date());
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		loginLog.setIp(request.getRemoteAddr());
		
		adminLoginLogMapper.insert(loginLog);
	}
	
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
		return adminRoleRelationDao.getRoleList(adminId);
	}
	
	@Override
	public UserDto loadUserByUsername(String username) {
		
		UmsAdmin admin = getAdminByUsername(username);
		if (admin != null) {
			List<UmsRole> roleList = getRoleList(admin.getId());
			UserDto userDto = new UserDto();
			BeanUtil.copyProperties(admin, userDto);
			if (CollUtil.isNotEmpty(roleList)) {
				List<String> roleStrList = roleList.stream().map(role -> role.getId() + "_" + role.getName()).collect(Collectors.toList());
				userDto.setRoles(roleStrList);
			}
			return userDto;
		}
		return null;
	}
	
	@Override
	public UmsAdmin registry(String username, String password) {
		
		UmsAdmin umsAdmin = new UmsAdmin();
		umsAdmin.setUsername(username);
		umsAdmin.setPassword(password);
		umsAdmin.setCreateTime(new Date());
		umsAdmin.setStatus(1);
		//查询是否有相同用户名的用户
		UmsAdminExample example = new UmsAdminExample();
		example.createCriteria().andUsernameEqualTo(username);
		List<UmsAdmin> umsAdminList = adminMapper.selectByExample(example);
		if (umsAdminList.size() > 0) {
			Asserts.fail("用户名已存在！");
		}
		//将密码进行加密操作
		String encodePassword = BCrypt.hashpw(umsAdmin.getPassword());
		umsAdmin.setPassword(encodePassword);
		adminMapper.insert(umsAdmin);
		return umsAdmin;
	}
	
	@Override
	public UmsAdmin getCurrentAdmin() {
		
		String userStr = request.getHeader(AuthConstant.USER_TOKEN_HEADER);
		if (StrUtil.isEmpty(userStr)) {
			Asserts.fail(ResultCode.UNAUTHORIZED);
		}
		UserDto userDto = JSONUtil.toBean(userStr, UserDto.class);
		UmsAdmin admin = adminCacheService.getAdmin(userDto.getId());
		if (admin != null) {
			return admin;
		} else {
			admin = adminMapper.selectByPrimaryKey(userDto.getId());
			adminCacheService.setAdmin(admin);
			return admin;
		}
	}
	
}
