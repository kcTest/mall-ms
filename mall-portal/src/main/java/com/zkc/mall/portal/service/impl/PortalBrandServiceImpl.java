package com.zkc.mall.portal.service.impl;

import com.github.pagehelper.PageHelper;
import com.zkc.mall.mbg.mapper.PmsBrandMapper;
import com.zkc.mall.mbg.mapper.PmsProductMapper;
import com.zkc.mall.mbg.model.PmsBrand;
import com.zkc.mall.mbg.model.PmsProduct;
import com.zkc.mall.mbg.model.PmsProductExample;
import com.zkc.mall.portal.dao.HomeDao;
import com.zkc.mall.portal.service.PortalBrandService;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class PortalBrandServiceImpl implements PortalBrandService {
	
	@Autowired
	private PmsBrandMapper brandMapper;
	@Autowired
	private PmsProductMapper productMapper;
	@Autowired
	private HomeDao homeDao;
	
	
	@Override
	public List<PmsBrand> recommendList(Integer pageSize, Integer pageNum) {
		int offset = (pageNum - 1) * pageSize;
		return homeDao.getRecommendBrandList(offset, pageSize);
	}
	
	@Override
	public PmsBrand detail(Long brandId) {
		return brandMapper.selectByPrimaryKey(brandId);
	}
	
	@Override
	public List<PmsProduct> productList(Long brandId, Integer pageSize, Integer pageNum) {
		PageHelper.startPage(pageNum, pageSize);
		PmsProductExample productExample = new PmsProductExample();
		productExample.createCriteria().andBrandIdEqualTo(brandId).andDeleteStatusEqualTo(0);
		return productMapper.selectByExample(productExample);
	}
}
