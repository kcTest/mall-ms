package com.zkc.mall.portal.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.json.JSONUtil;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.common.api.ResultCode;
import com.zkc.mall.common.constant.AuthConstant;
import com.zkc.mall.common.domain.UserDto;
import com.zkc.mall.common.exception.Asserts;
import com.zkc.mall.mbg.mapper.UmsMemberLevelMapper;
import com.zkc.mall.mbg.mapper.UmsMemberMapper;
import com.zkc.mall.mbg.model.UmsMember;
import com.zkc.mall.mbg.model.UmsMemberExample;
import com.zkc.mall.mbg.model.UmsMemberLevel;
import com.zkc.mall.mbg.model.UmsMemberLevelExample;
import com.zkc.mall.portal.service.AuthService;
import com.zkc.mall.portal.service.UmsMemberCacheService;
import com.zkc.mall.portal.service.UmsMemberService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UmsMemberServiceImpl implements UmsMemberService {
	
	@Resource
	private HttpServletRequest request;
	@Resource
	private UmsMemberCacheService memberCacheService;
	@Resource
	private UmsMemberMapper memberMapper;
	@Resource
	private UmsMemberLevelMapper memberLevelMapper;
	@Resource
	private AuthService authService;
	
	@Override
	public UmsMember getCurrentMember() {
		String userStr = request.getHeader(AuthConstant.USER_TOKEN_HEADER);
		if (StrUtil.isEmpty(userStr)) {
			Asserts.fail(ResultCode.UNAUTHORIZED);
		}
		
		UserDto userDto = JSONUtil.toBean(userStr, UserDto.class);
		UmsMember member = memberCacheService.getMember(userDto.getId());
		if (member == null) {
			member = getById(userDto.getId());
			memberCacheService.setMember(member);
		}
		
		return member;
	}
	
	@Override
	public void register(String username, String password, String telephone, String authCode) {
		//验证验证码
		if (!verifyAuthCode(authCode, telephone)) {
			Asserts.fail("验证码错误");
		}
		
		//判断用户是否存在
		UmsMemberExample example = new UmsMemberExample();
		example.createCriteria().andUsernameEqualTo(username);
		example.or(example.createCriteria().andPhoneEqualTo(telephone));
		List<UmsMember> members = memberMapper.selectByExample(example);
		if (CollUtil.isNotEmpty(members)) {
			Asserts.fail("该用户已存在");
		}
		
		UmsMember member = new UmsMember();
		member.setCreateTime(new Date());
		member.setPhone(telephone);
		member.setPassword(BCrypt.hashpw(password));
		member.setStatus(1);
		
		//设置默认会员等级
		UmsMemberLevelExample levelExample = new UmsMemberLevelExample();
		levelExample.createCriteria().andDefaultStatusEqualTo(1);
		List<UmsMemberLevel> memberLevels = memberLevelMapper.selectByExample(levelExample);
		if (CollUtil.isNotEmpty(memberLevels)) {
			member.setMemberLevelId(memberLevels.get(0).getId());
		}
		memberMapper.insert(member);
		
		member.setPassword(null);
	}
	
	@Override
	public CommonResult<?> login(String username, String password) {
		if (StrUtil.isEmpty(username) || StrUtil.isEmpty(password)) {
			Asserts.fail("用户名或密码不能为空！");
		}
		Map<String, String> params = new HashMap<>();
		params.put(AuthConstant.AUTH_CLIENT_ID, AuthConstant.PORTAL_CLIENT_ID);
		params.put(AuthConstant.AUTH_CLIENT_SECRET, AuthConstant.AUTH_CLIENT_SECRET_DEFAULT);
		params.put(AuthConstant.AUTH_GRANT_TYPE, AuthConstant.AUTH_GRANT_TYPE_DEFAULT);
		params.put(AuthConstant.AUTH_USERNAME, username);
		params.put(AuthConstant.AUTH_PASSWORD, password);
		
		return authService.getAccessToken(params);
	}
	
	@Override
	public String generateAuthCode(String telephone) {
		String authCode = RandomUtil.randomNumbers(6);
		memberCacheService.setAuthCode(telephone, authCode);
		return authCode;
	}
	
	@Override
	public void updatePassword(String telephone, String password, String authCode) {
		
		UmsMemberExample example = new UmsMemberExample();
		example.createCriteria().andPhoneEqualTo(telephone);
		List<UmsMember> umsMembers = memberMapper.selectByExample(example);
		if (CollUtil.isEmpty(umsMembers)) {
			Asserts.fail("账号不存在");
		}
		
		if (!verifyAuthCode(authCode, telephone)) {
			Asserts.fail("验证码错误");
		}
		
		UmsMember member = umsMembers.get(0);
		member.setPassword(BCrypt.hashpw(password));
		memberMapper.updateByPrimaryKeySelective(member);
		memberCacheService.delMember(member.getId());
	}
	
	@Override
	public UserDto loadByUsername(String username) {
		UmsMember member = getByUsername(username);
		if (member != null) {
			UserDto userDto = new UserDto();
			BeanUtil.copyProperties(member, userDto);
			userDto.setRoles(CollUtil.toList("前台会员"));
			return userDto;
		}
		
		return null;
	}
	
	@Override
	public void updateIntegration(Long id, int integration) {
		UmsMember member = new UmsMember();
		member.setId(id);
		member.setIntegration(integration);
		memberMapper.updateByPrimaryKeySelective(member);
		memberCacheService.delMember(id);
	}
	
	private UmsMember getByUsername(String username) {
		UmsMemberExample example = new UmsMemberExample();
		example.createCriteria().andUsernameEqualTo(username);
		List<UmsMember> members = memberMapper.selectByExample(example);
		if (CollUtil.isNotEmpty(members)) {
			return members.get(0);
		}
		return null;
	}
	
	private boolean verifyAuthCode(String authCode, String telephone) {
		if (StrUtil.isEmpty(authCode)) {
			return false;
		}
		String realAuthCode = memberCacheService.getAuthCode(telephone);
		return authCode.equals(realAuthCode);
	}
	
	@Override
	public UmsMember getById(Long memberId) {
		return memberMapper.selectByPrimaryKey(memberId);
	}
}
