package com.zkc.mall.portal.service.impl;

import com.zkc.mall.mbg.model.UmsMember;
import com.zkc.mall.portal.domain.MemberProductCollection;
import com.zkc.mall.portal.repository.MemberProductCollectionRepositoty;
import com.zkc.mall.portal.service.MemberCollectionService;
import com.zkc.mall.portal.service.UmsMemberService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MemberCollectionServiceImpl implements MemberCollectionService {
	
	@Resource
	private UmsMemberService memberService;
	
	@Resource
	private MemberProductCollectionRepositoty memberProductCollectionRepositoty;
	
	@Override
	public int add(MemberProductCollection productCollection) {
		int count = 0;
		UmsMember member = memberService.getCurrentMember();
		productCollection.setMemberIcon(member.getIcon());
		productCollection.setMemberId(member.getId());
		productCollection.setMemberNickname(member.getNickname());
		MemberProductCollection collection = memberProductCollectionRepositoty
				.findByMemberIdAndProductId(productCollection.getMemberId(), productCollection.getProductId());
		
		if (collection == null) {
			memberProductCollectionRepositoty.save(productCollection);
		}
		
		return count;
	}
	
	@Override
	public int delete(Long productId) {
		UmsMember member = memberService.getCurrentMember();
		return memberProductCollectionRepositoty.deleteByMemberIdAndProductId(member.getId(), productId);
	}
	
	@Override
	public List<MemberProductCollection> list(Integer pageSize, Integer pageNum) {
		UmsMember member = memberService.getCurrentMember();
		Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
		return memberProductCollectionRepositoty.findByMemberId(member.getId(), pageable);
	}
	
	@Override
	public MemberProductCollection detail(Long productId) {
		UmsMember member = memberService.getCurrentMember();
		return memberProductCollectionRepositoty.findByMemberIdAndProductId(member.getId(), productId);
	}
}
