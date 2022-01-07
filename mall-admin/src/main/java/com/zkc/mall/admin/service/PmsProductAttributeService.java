package com.zkc.mall.admin.service;

import com.zkc.mall.admin.dto.PmsProductAttributeParam;
import com.zkc.mall.admin.dto.ProductAttrInfo;
import com.zkc.mall.mbg.model.PmsProductAttribute;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PmsProductAttributeService {
	
	List<PmsProductAttribute> getList(Long cid, Integer type, Integer pageSize, Integer pageNum);
	
	@Transactional
	int create(PmsProductAttributeParam productAttributeParam);
	
	int update(Long id, PmsProductAttributeParam productAttributeParam);
	
	PmsProductAttribute getItem(Long id);
	
	@Transactional
	int delete(List<Long> ids);
	
	List<ProductAttrInfo> getAttrInfo(Long productCategoryId);
}
