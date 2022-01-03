package com.zkc.mall.admin.service.impl;

import com.zkc.mall.admin.service.UmsResourceCategoryService;
import com.zkc.mall.mbg.mapper.UmsResourceCategoryMapper;
import com.zkc.mall.mbg.model.UmsResourceCategory;
import com.zkc.mall.mbg.model.UmsResourceCategoryExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class UmsResourceCategoryServiceImpl implements UmsResourceCategoryService {
	
	@Resource
	private UmsResourceCategoryMapper resourceCategoryMapper;
	
	@Override
	public List<UmsResourceCategory> listAll() {
		return resourceCategoryMapper.selectByExample(new UmsResourceCategoryExample());
	}
	
	@Override
	public int create(UmsResourceCategory umsResourceCategory) {
		umsResourceCategory.setCreateTime(new Date());
		return resourceCategoryMapper.insert(umsResourceCategory);
	}
	
	@Override
	public int update(Long id, UmsResourceCategory umsResourceCategory) {
		umsResourceCategory.setId(id);
		return resourceCategoryMapper.updateByPrimaryKeySelective(umsResourceCategory);
	}
	
	@Override
	public int delete(Long id) {
		return resourceCategoryMapper.deleteByPrimaryKey(id);
	}
}
