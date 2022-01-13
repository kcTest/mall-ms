package com.zkc.mall.search.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan({"com.zkc.mall.mbg.mapper", "com.zkc.mall.search.dao"})
public class MybatisConfig {
}