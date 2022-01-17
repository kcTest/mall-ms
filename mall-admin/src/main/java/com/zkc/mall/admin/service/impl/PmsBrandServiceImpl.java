package com.zkc.mall.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.zkc.mall.admin.dto.PmsBrandParam;
import com.zkc.mall.admin.service.PmsBrandService;
import com.zkc.mall.mbg.mapper.PmsBrandMapper;
import com.zkc.mall.mbg.mapper.PmsProductMapper;
import com.zkc.mall.mbg.model.PmsBrand;
import com.zkc.mall.mbg.model.PmsBrandExample;
import com.zkc.mall.mbg.model.PmsProduct;
import com.zkc.mall.mbg.model.PmsProductExample;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class PmsBrandServiceImpl implements PmsBrandService {
	
	@Autowired
	private PmsBrandMapper brandMapper;
	@Autowired
	private PmsProductMapper productMapper;
	
	@Override
	public List<PmsBrand> getList() {
		return brandMapper.selectByExample(new PmsBrandExample());
	}
	
	@Override
	public int create(PmsBrandParam brandParam) {
		PmsBrand brand = new PmsBrand();
		BeanUtils.copyProperties(brandParam, brand);
		//如果创建时首字母为空 取名称的第一个作为首字母
		if (StrUtil.isEmpty(brand.getFirstLetter())) {
			brand.setFirstLetter(brand.getName().substring(0, 1));
		}
		return brandMapper.insertSelective(brand);
	}
	
	@Override
	public int update(Long id, PmsBrandParam brandParam) {
		PmsBrand brand = new PmsBrand();
		BeanUtils.copyProperties(brandParam, brand);
		brand.setId(id);
		//如果创建时首字母为空 取名称的第一个作为首字母
		if (StrUtil.isEmpty(brand.getFirstLetter())) {
			brand.setFirstLetter(brand.getName().substring(0, 1));
		}
		
		//更新商品中的品牌名称
		PmsProductExample productExample = new PmsProductExample();
		productExample.createCriteria().andBrandIdEqualTo(id);
		PmsProduct product = new PmsProduct();
		product.setBrandName(brand.getName());
		productMapper.updateByExampleSelective(product, productExample);
		
		return brandMapper.updateByPrimaryKeySelective(brand);
		
	}
	
	@Override
	public int delete(Long id) {
		return brandMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public List<PmsBrand> getList(String name, Integer pageSize, Integer pageNum) {
		PageHelper.startPage(pageNum, pageSize);
		PmsBrandExample example = new PmsBrandExample();
		example.setOrderByClause("sort desc");
		if (StrUtil.isNotEmpty(name)) {
			example.createCriteria().andNameLike("%" + name + "%");
		}
		return brandMapper.selectByExample(example);
	}
	
	@Override
	public PmsBrand getItem(Long id) {
		return brandMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public int deleteBatch(List<Long> ids) {
		PmsBrandExample example = new PmsBrandExample();
		example.createCriteria().andIdIn(ids);
		return brandMapper.deleteByExample(example);
	}
	
	@Override
	public int updateShowStatus(List<Long> ids, Integer showStatus) {
		PmsBrand brand = new PmsBrand();
		brand.setShowStatus(showStatus);
		PmsBrandExample example = new PmsBrandExample();
		example.createCriteria().andIdIn(ids);
		return brandMapper.updateByExampleSelective(brand, example);
		
	}
	
	@Override
	public int updateFactoryStatus(List<Long> ids, Integer factoryStatus) {
		PmsBrand brand = new PmsBrand();
		brand.setFactoryStatus(factoryStatus);
		PmsBrandExample example = new PmsBrandExample();
		example.createCriteria().andIdIn(ids);
		return brandMapper.updateByExampleSelective(brand, example);
	}
}
