package com.zkc.mall.portal.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.zkc.mall.mbg.mapper.UmsMemberReceiveAddressMapper;
import com.zkc.mall.mbg.model.UmsMember;
import com.zkc.mall.mbg.model.UmsMemberReceiveAddress;
import com.zkc.mall.mbg.model.UmsMemberReceiveAddressExample;
import com.zkc.mall.portal.service.UmsMemberReceiveAddressService;
import com.zkc.mall.portal.service.UmsMemberService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
	
	@Override
	public int delete(Long id) {
		UmsMember member = memberService.getCurrentMember();
		UmsMemberReceiveAddressExample example = new UmsMemberReceiveAddressExample();
		example.createCriteria().andIdEqualTo(id).andMemberIdEqualTo(member.getId());
		return memberReceiveAddressMapper.deleteByExample(example);
	}
	
	@Override
	public int update(Long id, UmsMemberReceiveAddress receiveAddress) {
		receiveAddress.setId(null);
		UmsMember member = memberService.getCurrentMember();
		UmsMemberReceiveAddressExample example = new UmsMemberReceiveAddressExample();
		example.createCriteria().andMemberIdEqualTo(member.getId()).andIdEqualTo(id);
		if (receiveAddress.getDefaultStatus() == 1) {
			//当前为新的默认地址 修改原有默认地址状态
			UmsMemberReceiveAddress oriDefaultAddress = new UmsMemberReceiveAddress();
			oriDefaultAddress.setDefaultStatus(0);
			UmsMemberReceiveAddressExample oriExample = new UmsMemberReceiveAddressExample();
			oriExample.createCriteria().andMemberIdEqualTo(member.getId());
			memberReceiveAddressMapper.updateByExampleSelective(oriDefaultAddress, oriExample);
		}
		
		return memberReceiveAddressMapper.updateByExampleSelective(receiveAddress, example);
	}
	
	@Override
	public List<UmsMemberReceiveAddress> list() {
		UmsMember member = memberService.getCurrentMember();
		UmsMemberReceiveAddressExample example = new UmsMemberReceiveAddressExample();
		example.createCriteria().andMemberIdEqualTo(member.getId());
		return memberReceiveAddressMapper.selectByExample(example);
	}
	
	@Override
	public UmsMemberReceiveAddress getItem(Long id) {
		UmsMember member = memberService.getCurrentMember();
		UmsMemberReceiveAddressExample example = new UmsMemberReceiveAddressExample();
		example.createCriteria().andMemberIdEqualTo(member.getId()).andIdEqualTo(id);
		List<UmsMemberReceiveAddress> addresses = memberReceiveAddressMapper.selectByExample(example);
		return !CollectionUtil.isEmpty(addresses) ? addresses.get(0) : null;
	}
}
