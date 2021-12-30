package com.zkc.mall.auth.service.impl;

import com.zkc.mall.common.constant.AuthConstant;
import com.zkc.mall.common.domain.UserDto;
import com.zkc.mall.auth.constant.MessageConstant;
import com.zkc.mall.auth.domain.CustomUserDetails;
import com.zkc.mall.auth.service.UmsAdminService;
import com.zkc.mall.auth.service.UmsMemberService;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
	
	@Resource
	private HttpServletRequest request;
	@Resource
	private UmsAdminService adminService;
	@Resource
	private UmsMemberService memberService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		String client_id = request.getParameter("client_id");
		
		UserDto userDto;
		if (AuthConstant.ADMIN_CLIENT_ID.equals(client_id)) {
			userDto = adminService.loadUserByUsername(username);
		} else {
			userDto = memberService.loadUserByUsername(username);
		}
		if (userDto == null) {
			throw new UsernameNotFoundException(MessageConstant.USERNAME_PASSWORD_ERROR);
		}
		userDto.setClientId(client_id);
		
		CustomUserDetails customUserDetails = new CustomUserDetails(userDto);
		if (!customUserDetails.isEnabled()) {
			throw new DisabledException(MessageConstant.ACCOUNT_DISABLE);
		} else if (!customUserDetails.isAccountNonLocked()) {
			throw new LockedException(MessageConstant.ACCOUNT_LOCKED);
		} else if (!customUserDetails.isAccountNonExpired()) {
			throw new AccountExpiredException(MessageConstant.ACCOUNT_EXPIRED);
		} else if (!customUserDetails.isCredentialsNonExpired()) {
			throw new CredentialsExpiredException(MessageConstant.CREDENTIAL_EXPIRED);
		}
		
		return customUserDetails;
	}
	
}
