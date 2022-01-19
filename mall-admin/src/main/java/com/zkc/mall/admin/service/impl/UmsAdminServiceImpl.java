package com.zkc.mall.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageHelper;
import com.zkc.mall.admin.dao.UmsAdminRoleRelationDao;
import com.zkc.mall.admin.dto.UpdatePasswordParam;
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
import com.zkc.mall.mbg.mapper.UmsAdminRoleRelationMapper;
import com.zkc.mall.mbg.model.*;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UmsAdminServiceImpl implements UmsAdminService {
	
	@Autowired
	private UmsAdminMapper adminMapper;
	@Autowired
	private UmsAdminRoleRelationDao adminRoleRelationDao;
	@Autowired
	private AuthService authService;
	@Autowired
	private UmsAdminLoginLogMapper adminLoginLogMapper;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private UmsAdminCacheService adminCacheService;
	@Autowired
	private UmsAdminRoleRelationMapper adminRoleRelationMapper;
	
	@Override
	public CommonResult login(String username, String password) {
		if (StrUtil.isEmpty(username) || StrUtil.isEmpty(password)) {
			Asserts.fail("用户名或密码不能为空！");
		}
		Map<String, String> params = new HashMap<>(5);
		params.put(AuthConstant.AUTH_CLIENT_ID, AuthConstant.ADMIN_CLIENT_ID);
		params.put(AuthConstant.AUTH_CLIENT_SECRET, AuthConstant.AUTH_CLIENT_SECRET_DEFAULT);
		params.put(AuthConstant.AUTH_GRANT_TYPE, AuthConstant.AUTH_GRANT_TYPE_DEFAULT);
		params.put(AuthConstant.AUTH_USERNAME, username);
		params.put(AuthConstant.AUTH_PASSWORD, password);
		CommonResult restResult = CommonResult.failed("登录异常");
		try {
			restResult = authService.getAccessToken(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	
	@Override
	public List<UmsAdmin> list(String keyword, Integer pageSize, Integer pageNum) {
		PageHelper.startPage(pageNum, pageSize);
		UmsAdminExample example = new UmsAdminExample();
		if (!StrUtil.isEmpty(keyword)) {
			example.createCriteria().andUsernameLike("%" + keyword + "%");
			example.or(example.createCriteria().andNickNameLike("%" + keyword + "%"));
		}
		
		return adminMapper.selectByExample(example);
	}
	
	@Override
	public UmsAdmin getItem(Long id) {
		return adminMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public int update(Long id, UmsAdmin admin) {
		admin.setId(id);
		UmsAdmin rawAdmin = adminMapper.selectByPrimaryKey(id);
		if (rawAdmin.getPassword().equals(admin.getPassword())) {
			admin.setPassword(null);
		} else {
			if (StrUtil.isEmpty(admin.getPassword())) {
				admin.setPassword(null);
			} else {
				admin.setPassword(BCrypt.hashpw(admin.getPassword()));
			}
		}
		int count = adminMapper.updateByPrimaryKeySelective(admin);
		adminCacheService.delAdmin(id);
		return count;
	}
	
	@Override
	public int updatePassword(UpdatePasswordParam param) {
		Long id = param.getId();
		if (id == null || id < 0) {
			return -1;
		}
		UmsAdmin umsAdmin = adminMapper.selectByPrimaryKey(id);
		if (umsAdmin == null) {
			return -2;
		}
		if (!BCrypt.checkpw(param.getOldPassword(), umsAdmin.getPassword())) {
			return -3;
		}
		umsAdmin.setPassword(BCrypt.hashpw(param.getNewPassword()));
		adminMapper.updateByPrimaryKeySelective(umsAdmin);
		adminCacheService.delAdmin(id);
		return 1;
	}
	
	@Override
	public int delete(Long id) {
		int count = adminMapper.deleteByPrimaryKey(id);
		adminCacheService.delAdmin(id);
		return count;
	}
	
	@Override
	public int updateRole(Long adminId, List<Long> roleIds) {
		UmsAdmin admin = adminMapper.selectByPrimaryKey(adminId);
		int count = admin == null || roleIds == null ? 0 : roleIds.size();
		
		UmsAdminRoleRelationExample example = new UmsAdminRoleRelationExample();
		example.createCriteria().andAdminIdEqualTo(adminId);
		adminRoleRelationMapper.deleteByExample(example);
		
		if (!CollUtil.isEmpty(roleIds)) {
			List<UmsAdminRoleRelation> relationList = new ArrayList<>();
			for (Long roleId : roleIds) {
				UmsAdminRoleRelation relation = new UmsAdminRoleRelation();
				relation.setAdminId(adminId);
				relation.setRoleId(roleId);
				relationList.add(relation);
			}
			adminRoleRelationDao.insertList(relationList);
		}
		return count;
	}
	
}
