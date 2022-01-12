package com.zkc.mall.portal.service.impl;

import com.zkc.mall.mbg.model.UmsMember;
import com.zkc.mall.portal.domain.MemberReadHistory;
import com.zkc.mall.portal.repository.MemberReadHistoryRepository;
import com.zkc.mall.portal.service.MemberReadHistoryService;
import com.zkc.mall.portal.service.UmsMemberService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MemberReadHistoryServiceImpl implements MemberReadHistoryService {
	
	@Resource
	private UmsMemberService memberService;
	@Resource
	private MemberReadHistoryRepository memberReadHistoryRepository;
	
	@Override
	public int create(MemberReadHistory memberReadHistory) {
		UmsMember currentMember = memberService.getCurrentMember();
		memberReadHistory.setMemberId(currentMember.getId());
		memberReadHistory.setMemberNickname(currentMember.getNickname());
		memberReadHistory.setMemberIcon(currentMember.getIcon());
		memberReadHistory.setId(null);
		memberReadHistory.setCreateTime(new Date());
		memberReadHistoryRepository.save(memberReadHistory);
		return 1;
	}
	
	@Override
	public int delete(List<String> ids) {
		List<MemberReadHistory> historyList = new ArrayList<>();
		for (String id : ids) {
			MemberReadHistory memberReadHistory = new MemberReadHistory();
			memberReadHistory.setId(id);
			historyList.add(memberReadHistory);
		}
		memberReadHistoryRepository.deleteAll(historyList);
		return ids.size();
	}
	
	@Override
	public void clear() {
		UmsMember currentMember = memberService.getCurrentMember();
		memberReadHistoryRepository.deleteAllByMemberId(currentMember.getId());
	}
	
	@Override
	public List<MemberReadHistory> list(Integer pageSize, Integer pageNum) {
		UmsMember currentMember = memberService.getCurrentMember();
		Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
		return memberReadHistoryRepository.findByMemberIdOrderByCreateTimeDesc(currentMember.getId(), pageable);
	}
}
