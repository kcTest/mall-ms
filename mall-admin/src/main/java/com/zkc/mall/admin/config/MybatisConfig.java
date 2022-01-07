package com.zkc.mall.admin.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan({"com.zkc.mall.mbg.mapper", "com.zkc.mall.admin.dao"})
public class MybatisConfig {
}
