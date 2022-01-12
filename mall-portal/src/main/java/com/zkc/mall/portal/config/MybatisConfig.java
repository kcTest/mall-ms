package com.zkc.mall.portal.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan({"com.zkc.mall.mbg.mapper", "com.zkc.mall.portal.dao"})
public class MybatisConfig {
}
