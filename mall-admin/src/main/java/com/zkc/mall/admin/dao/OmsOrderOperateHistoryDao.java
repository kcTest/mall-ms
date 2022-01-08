package com.zkc.mall.admin.dao;

import com.zkc.mall.mbg.model.OmsOrderOperateHistory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OmsOrderOperateHistoryDao {
	
	int insertList(@Param("list") List<OmsOrderOperateHistory> historyList);
}
