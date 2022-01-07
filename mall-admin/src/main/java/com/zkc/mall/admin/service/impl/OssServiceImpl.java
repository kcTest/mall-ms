package com.zkc.mall.admin.service.impl;

import cn.hutool.json.JSONUtil;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.zkc.mall.admin.dto.OssCallbackParam;
import com.zkc.mall.admin.dto.OssCallbackResult;
import com.zkc.mall.admin.dto.OssPolicyResult;
import com.zkc.mall.admin.service.OssService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class OssServiceImpl implements OssService {
	
	@Value("${aliyun.oss.dir.prefix}")
	private String ALIYUN_OSS_DIR_PREFIX;
	
	@Value("${aliyun.oss.policy.expire}")
	private int ALIYUN_OSS_POLICY_EXPIRE;
	
	@Value("${aliyun.oss.maxSize}")
	private int ALIYUN_OSS_MAX_SIZE;
	
	@Value("${aliyun.oss.callback}")
	private String ALIYUN_OSS_CALLBACK;
	
	@Value("${aliyun.oss.bucketName}")
	private String ALIYUN_OSS_BUCKET_NAME;
	
	@Value("${aliyun.oss.endpoint}")
	private String ALIYUN_OSS_ENDPOINT;
	
	@Resource
	private OSSClient ossClient;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OssServiceImpl.class);
	
	@Override
	public OssPolicyResult policy() {
		
		OssPolicyResult result = new OssPolicyResult();
		
		//存储目录
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dir = ALIYUN_OSS_DIR_PREFIX + sdf.format(new Date());
		
		//签名有效期
		long expireEndTime = System.currentTimeMillis() + ALIYUN_OSS_POLICY_EXPIRE * 1000;
		Date expireDate = new Date(expireEndTime);
		
		//文件大小
		long maxSize = ALIYUN_OSS_MAX_SIZE * 1024 * 1024;
		
		//回调
		OssCallbackParam callback = new OssCallbackParam();
		callback.setCallbackUrl(ALIYUN_OSS_CALLBACK);
		callback.setCallbackBody("filename=${object}&size=${size}&mimeType=${mimeType}&height=" +
				"${imageInfo.height}&width=${imageInfo.width}");
		callback.setCallbackBodyType("application/x-www.form-urlencoded");
		
		try {
			PolicyConditions pcs = new PolicyConditions();
			pcs.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, maxSize);
			pcs.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
			String postPolicy = ossClient.generatePostPolicy(expireDate, pcs);
			byte[] policyBytes = postPolicy.getBytes(StandardCharsets.UTF_8);
			String policy = BinaryUtil.toBase64String(policyBytes);
			String signature = ossClient.calculatePostSignature(policy);
			String callbackData = BinaryUtil.toBase64String(JSONUtil.parse(callback).toString().getBytes(StandardCharsets.UTF_8));
			
			result.setAccessKeyId(ossClient.getCredentialsProvider().getCredentials().getAccessKeyId());
			result.setPolicy(policy);
			result.setSignature(signature);
			result.setDir(dir);
			result.setCallback(callbackData);
			String host = "http://" + ALIYUN_OSS_BUCKET_NAME + "." + ALIYUN_OSS_ENDPOINT;
			result.setHost(host);
		} catch (Exception e) {
			LOGGER.error("生成签名失败", e);
		}
		
		return result;
	}
	
	@Override
	public OssCallbackResult callback(HttpServletRequest request) {
		OssCallbackResult result = new OssCallbackResult();
		String filename = request.getParameter("filename");
		filename = "http://".concat(ALIYUN_OSS_BUCKET_NAME).concat(".").concat(ALIYUN_OSS_ENDPOINT).concat("/").concat(filename);
		
		result.setFilename(filename);
		result.setSize(request.getParameter("size"));
		result.setMimeType(request.getParameter("mimeType"));
		result.setWidth(request.getParameter("width"));
		result.setHeight(request.getParameter("height"));
		return result;
	}
}
