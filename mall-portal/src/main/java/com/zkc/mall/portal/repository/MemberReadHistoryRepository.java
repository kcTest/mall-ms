package com.zkc.mall.portal.repository;

import com.zkc.mall.portal.domain.MemberReadHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MemberReadHistoryRepository extends MongoRepository<MemberReadHistory, String> {
	
	void deleteAllByMemberId(Long memberId);
	
	List<MemberReadHistory> findByMemberIdOrderByCreateTimeDesc(Long id, Pageable pageable);
}
