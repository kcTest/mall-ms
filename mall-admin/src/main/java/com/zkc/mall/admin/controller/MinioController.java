package com.zkc.mall.admin.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.zkc.mall.admin.dto.BucketPolicyConfigDto;
import com.zkc.mall.admin.dto.MinioUploadDto;
import com.zkc.mall.common.api.CommonResult;
import io.minio.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;

@Tag(name = "MinioController", description = "minio对象存储管理")
@CrossOrigin
@RestController
@RequestMapping("/minio")
public class MinioController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MinioController.class);
	
	@Value("${minio.endpoint}")
	private String ENDPOINT;
	@Value("${minio.accessKey}")
	private String ACCESS_KEY;
	@Value("${minio.secretKey}")
	private String SECRET_KEY;
	@Value("${minio.bucketName}")
	private String BUCKET_NAME;
	
	@Operation(summary ="文件上传")
	@PostMapping("/upload")
	@ResponseBody
	public CommonResult<?> upload(@RequestParam("file") MultipartFile file) {
		
		try {
			
			MinioClient client = MinioClient.builder()
					.endpoint(ENDPOINT)
					.credentials(ACCESS_KEY, SECRET_KEY)
					.build();
			
			boolean exists = client.bucketExists(BucketExistsArgs.builder().bucket(BUCKET_NAME).build());
			
			if (exists) {
				LOGGER.info("存储桶已存在！");
			} else {
				
				client.makeBucket(MakeBucketArgs.builder().bucket(BUCKET_NAME).build());
				BucketPolicyConfigDto bucketPolicyConfigDto = createBucketConfiguration(BUCKET_NAME);
				SetBucketPolicyArgs policyArgs = SetBucketPolicyArgs.builder()
						.bucket(BUCKET_NAME)
						.config(JSONUtil.toJsonStr(bucketPolicyConfigDto))
						.build();
				client.setBucketPolicy(policyArgs);
				
				//存储对象名称
				String filename = file.getOriginalFilename();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String objectName = sdf.format(new Date()) + "/" + filename;
				
				//上传
				PutObjectArgs putObjectArgs = PutObjectArgs.builder()
						.bucket(BUCKET_NAME)
						.object(objectName)
						.contentType(file.getContentType())
						.stream(file.getInputStream(), file.getSize(), ObjectWriteArgs.MIN_MULTIPART_SIZE)
						.build();
				
				client.putObject(putObjectArgs);
				LOGGER.info("上传文件成功!");
				
				MinioUploadDto uploadDto = new MinioUploadDto();
				uploadDto.setName(filename);
				uploadDto.setUrl(ENDPOINT + "/" + BUCKET_NAME + "/" + objectName);
				
				return CommonResult.success(uploadDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("上传文件发生错误:{}!", e.getMessage());
		}
		
		return CommonResult.failed();
	}
	
	private BucketPolicyConfigDto createBucketConfiguration(String bucketName) {
		BucketPolicyConfigDto.Statement statement = BucketPolicyConfigDto.Statement.builder()
				.Effect("Allow")
				.Principal("*")
				.Action("s3:GetObject")
				.Resource("arn:aws:s3::" + bucketName + "/*.**")
				.build();
		return BucketPolicyConfigDto.builder()
				.Version("2022-01-07")
				.Statement(CollUtil.toList(statement))
				.build();
	}
	
	
	@Operation(summary ="文件删除")
	@PostMapping("/delete")
	@ResponseBody
	public CommonResult<?> delete(@RequestParam("objectName") String objectName) {
		
		try {
			MinioClient client = MinioClient.builder()
					.endpoint(ENDPOINT)
					.credentials(ACCESS_KEY, SECRET_KEY)
					.build();
			client.removeObject(RemoveObjectArgs.builder().bucket(BUCKET_NAME).object(objectName).build());
			return CommonResult.success(null);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("删除文件发生错误:{}!", e.getMessage());
		}
		
		return CommonResult.failed();
	}
	
}
