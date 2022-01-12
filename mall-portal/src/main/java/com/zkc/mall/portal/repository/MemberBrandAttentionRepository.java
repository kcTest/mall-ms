package com.zkc.mall.portal.repository;

import com.zkc.mall.portal.domain.MemberBrandAttention;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MemberBrandAttentionRepository extends MongoRepository<MemberBrandAttention, String> {
	
	MemberBrandAttention findByMemberIdAndBrandId(Long memberId, Long brandId);
	
	int deleteByMemberIdAndBrandId(Long id, Long brandId);
	
	List<MemberBrandAttention> findByMemberId(Long id);
	
	void deleteAllByMemberId(Long id);
}
