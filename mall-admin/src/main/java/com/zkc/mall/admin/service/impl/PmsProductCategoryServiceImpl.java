package com.zkc.mall.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.zkc.mall.admin.dao.PmsProductCategoryAttributeRelationDao;
import com.zkc.mall.admin.dto.PmsProductCategoryParam;
import com.zkc.mall.admin.dto.PmsProductParam;
import com.zkc.mall.admin.service.PmsProductCategoryService;
import com.zkc.mall.mbg.mapper.PmsProductCategoryAttributeRelationMapper;
import com.zkc.mall.mbg.mapper.PmsProductCategoryMapper;
import com.zkc.mall.mbg.model.PmsProductAttributeCategory;
import com.zkc.mall.mbg.model.PmsProductCategory;
import com.zkc.mall.mbg.model.PmsProductCategoryAttributeRelation;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class PmsProductCategoryServiceImpl implements PmsProductCategoryService {
	
	@Resource
	private PmsProductCategoryMapper productCategoryMapper;
	@Resource
	private PmsProductCategoryAttributeRelationDao productCategoryAttributeRelationDao;
	
	@Override
	public int create(PmsProductCategoryParam productCategoryParam) {
		
		PmsProductCategory productCategory = new PmsProductCategory();
		productCategory.setProductCount(0);
		BeanUtil.copyProperties(productCategoryParam, productCategory);
		setCategory(productCategory);
		int count = productCategoryMapper.insertSelective(productCategory);
		
		//分类与属性关系
		List<Long> attributeIdList = productCategoryParam.getProductAttributeIdList();
		if (!CollectionUtil.isEmpty(attributeIdList)) {
			insertRelationList(productCategory.getId(), attributeIdList);
		}
		
		return count;
	}
	
	@Override
	public int update(Long id, PmsProductCategoryParam productCategoryParam) {
		
		
		return 0;
	}
	
	private void setCategory(PmsProductCategory productCategory) {
		productCategory.setLevel(0);
		if (productCategory.getParentId() == 0) {
			return;
		}
		PmsProductCategory parentCategory = productCategoryMapper.selectByPrimaryKey(productCategory.getParentId());
		if (parentCategory != null) {
			productCategory.setLevel(productCategory.getLevel() + 1);
		}
	}
	
	private void insertRelationList(Long id, List<Long> attributeIdList) {
		
		List<PmsProductCategoryAttributeRelation> list = new ArrayList<>();
		for (Long attrId : attributeIdList) {
			PmsProductCategoryAttributeRelation relation = new PmsProductCategoryAttributeRelation();
			relation.setProductCategoryId(id);
			relation.setProductAttributeId(attrId);
			list.add(relation);
		}
		productCategoryAttributeRelationDao.insertList(list);
	}
}
