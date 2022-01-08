package com.zkc.mall.portal.service.impl;

import com.zkc.mall.mbg.mapper.UmsMemberReceiveAddressMapper;
import com.zkc.mall.mbg.model.UmsMember;
import com.zkc.mall.mbg.model.UmsMemberReceiveAddress;
import com.zkc.mall.portal.service.UmsMemberReceiveAddressService;
import com.zkc.mall.portal.service.UmsMemberService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UmsMemberReceiveAddressServiceImpl implements UmsMemberReceiveAddressService {
	
	@Resource
	private UmsMemberService memberService;
	@Resource
	private UmsMemberReceiveAddressMapper memberReceiveAddressMapper;
	
	@Override
	public int add(UmsMemberReceiveAddress receiverAddress) {
		UmsMember member = memberService.getCurrentMember();
		receiverAddress.setMemberId(member.getId());
		return memberReceiveAddressMapper.insert(receiverAddress);
	}
}
