package com.zkc.mall.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.zkc.mall.admin.dao.PmsProductAttributeCategoryDao;
import com.zkc.mall.admin.dto.PmsProductAttributeCategoryItem;
import com.zkc.mall.admin.service.PmsProductAttributeCategoryService;
import com.zkc.mall.mbg.mapper.PmsProductAttributeCategoryMapper;
import com.zkc.mall.mbg.model.PmsProductAttributeCategory;
import com.zkc.mall.mbg.model.PmsProductAttributeCategoryExample;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class PmsProductAttributeCategoryServiceImpl implements PmsProductAttributeCategoryService {
	
	@Autowired
	private PmsProductAttributeCategoryMapper productAttributeCategoryMapper;
	@Autowired
	private PmsProductAttributeCategoryDao productAttributeCategoryDao;
	
	@Override
	public int create(String name) {
		PmsProductAttributeCategory category = new PmsProductAttributeCategory();
		category.setName(name);
		return productAttributeCategoryMapper.insertSelective(category);
	}
	
	@Override
	public int update(Long id, String name) {
		PmsProductAttributeCategory category = new PmsProductAttributeCategory();
		category.setId(id);
		category.setName(name);
		return productAttributeCategoryMapper.updateByPrimaryKeySelective(category);
	}
	
	@Override
	public int delete(Long id) {
		return productAttributeCategoryMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public PmsProductAttributeCategory getItem(Long id) {
		return productAttributeCategoryMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public List<PmsProductAttributeCategory> getList(Integer pageSize, Integer pageNum) {
		PageHelper.startPage(pageNum, pageSize);
		return productAttributeCategoryMapper.selectByExample(new PmsProductAttributeCategoryExample());
	}
	
	@Override
	public List<PmsProductAttributeCategoryItem> getListWithAttr() {
		return productAttributeCategoryDao.getListWithAttr();
	}
}
