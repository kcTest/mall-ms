package com.zkc.mall.portal.service.impl;

import com.zkc.mall.mbg.model.UmsMember;
import com.zkc.mall.portal.domain.MemberBrandAttention;
import com.zkc.mall.portal.repository.MemberBrandAttentionRepository;
import com.zkc.mall.portal.service.MemberAttentionService;
import com.zkc.mall.portal.service.UmsMemberService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class MemberAttentionServiceImpl implements MemberAttentionService {
	
	@Resource
	private UmsMemberService memberService;
	
	@Resource
	private MemberBrandAttentionRepository memberBrandAttentionRepository;
	
	@Override
	public int add(MemberBrandAttention brandAttention) {
		int count = 0;
		UmsMember member = memberService.getCurrentMember();
		brandAttention.setMemberId(member.getId());
		brandAttention.setMemberNickname(member.getNickname());
		brandAttention.setMemberIcon(member.getIcon());
		brandAttention.setCreateTime(new Date());
		MemberBrandAttention findbrandAttention = memberBrandAttentionRepository.findByMemberIdAndBrandId(brandAttention.getMemberId(), brandAttention.getBrandId());
		if (findbrandAttention == null) {
			memberBrandAttentionRepository.save(brandAttention);
			count = 1;
		}
		return count;
	}
	
	@Override
	public int delete(Long brandId) {
		UmsMember member = memberService.getCurrentMember();
		return memberBrandAttentionRepository.deleteByMemberIdAndBrandId(member.getId(), brandId);
	}
	
	@Override
	public List<MemberBrandAttention> list(Integer pageSize, Integer pageNum) {
		UmsMember member = memberService.getCurrentMember();
		return memberBrandAttentionRepository.findByMemberId(member.getId());
	}
	
	@Override
	public MemberBrandAttention detail(Long brandId) {
		UmsMember member = memberService.getCurrentMember();
		return memberBrandAttentionRepository.findByMemberIdAndBrandId(member.getId(), brandId);
	}
	
	@Override
	public void clear() {
		UmsMember member = memberService.getCurrentMember();
		memberBrandAttentionRepository.deleteAllByMemberId(member.getId());
	}
}
