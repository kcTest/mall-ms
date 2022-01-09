package com.zkc.mall.portal.service;

import com.zkc.mall.mbg.model.UmsMemberReceiveAddress;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UmsMemberReceiveAddressService {
	
	int add(UmsMemberReceiveAddress receiverAddress);
	
	int delete(Long id);
	
	@Transactional
	int update(Long id, UmsMemberReceiveAddress receiveAddress);
	
	List<UmsMemberReceiveAddress> list();
	
	UmsMemberReceiveAddress getItem(Long id);
}
