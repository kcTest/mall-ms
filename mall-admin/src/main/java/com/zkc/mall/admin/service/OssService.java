package com.zkc.mall.admin.service;

import com.zkc.mall.admin.dto.OssCallbackResult;
import com.zkc.mall.admin.dto.OssPolicyResult;

import javax.servlet.http.HttpServletRequest;

public interface OssService {
	
	OssPolicyResult policy();
	
	OssCallbackResult callback(HttpServletRequest request);
}
