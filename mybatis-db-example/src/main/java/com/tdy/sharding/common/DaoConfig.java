package com.tdy.sharding.common;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

/**
 * Mybatis配置
 * 
 */
@Configuration
@MapperScan("com.tdy.sharding.mapper")
public class DaoConfig {


}
