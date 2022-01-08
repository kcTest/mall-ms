package com.zkc.mall.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.zkc.mall.portal"})
public class MallPortalApp {
	public static void main(String[] args) {
		SpringApplication.run(MallPortalApp.class, args);
	}
}
