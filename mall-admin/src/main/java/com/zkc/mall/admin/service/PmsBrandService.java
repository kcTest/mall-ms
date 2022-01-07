package com.zkc.mall.admin.service;

import com.zkc.mall.admin.dto.PmsBrandParam;
import com.zkc.mall.mbg.model.PmsBrand;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PmsBrandService {
	
	List<PmsBrand> getList();
	
	int create(PmsBrandParam brand);
	
	@Transactional
	int update(Long id, PmsBrandParam brandParam);
	
	int delete(Long id);
	
	List<PmsBrand> getList(String name, Integer pageSize, Integer pageNum);
	
	PmsBrand getItem(Long id);
	
	int deleteBatch(List<Long> ids);
	
	int updateShowStatus(List<Long> ids, Integer showStatus);
	
	int updateFactoryStatus(List<Long> ids, Integer factoryStatus);
}
