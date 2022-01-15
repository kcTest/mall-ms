package com.zkc.mall.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.zkc.mall"})
public class MallAuthApp {
	public static void main(String[] args) {
		SpringApplication.run(MallAuthApp.class, args);
	}
}
