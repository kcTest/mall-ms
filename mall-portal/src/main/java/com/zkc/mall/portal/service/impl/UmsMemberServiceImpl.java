package com.zkc.mall.portal.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.zkc.mall.common.api.ResultCode;
import com.zkc.mall.common.constant.AuthConstant;
import com.zkc.mall.common.domain.UserDto;
import com.zkc.mall.common.exception.Asserts;
import com.zkc.mall.mbg.mapper.UmsMemberMapper;
import com.zkc.mall.mbg.model.UmsMember;
import com.zkc.mall.portal.service.UmsMemberCacheService;
import com.zkc.mall.portal.service.UmsMemberService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class UmsMemberServiceImpl implements UmsMemberService {
	
	@Resource
	private HttpServletRequest request;
	@Resource
	private UmsMemberCacheService memberCacheService;
	@Resource
	private UmsMemberMapper memberMapper;
	
	
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
		
		
	}
	
	private UmsMember getById(Long id) {
		return memberMapper.selectByPrimaryKey(id);
	}
}
