package com.zkc.mall.portal.repository;

import com.zkc.mall.portal.domain.MemberProductCollection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MemberProductCollectionRepositoty extends MongoRepository<MemberProductCollection, String> {
	
	MemberProductCollection findByMemberIdAndProductId(Long memberId, Long productId);
	
	int deleteByMemberIdAndProductId(Long id, Long productId);
	
	List<MemberProductCollection> findByMemberId(Long id, Pageable pageable);
}
