package com.zkc.mall.admin.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OssConfig {
	
	@Value("${aliyun.oss.endpoint")
	private String ALIYUN_OSS_ENDPOINT;
	
	@Value("${aliyun.oss.accessKeyId}")
	private String ALIYUN_OSS_ACCESSKEYID;
	
	@Value("${aliyun.oss.accessSecrete}")
	private String ALIYUN_OSS_ACCESSSECRETE;
	
	@Bean
	public OSSClient ossClient() {
		OSS ossClient = new OSSClientBuilder().build(ALIYUN_OSS_ENDPOINT, ALIYUN_OSS_ACCESSKEYID, ALIYUN_OSS_ACCESSSECRETE);
		return (OSSClient) ossClient;
	}
}
