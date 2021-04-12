package com.videoSite.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 **/
@Configuration
@EnableTransactionManagement
@MapperScan("com.videoSite.mapper")
public class MybatisXConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor(){
          PaginationInterceptor paginationInterceptor=new PaginationInterceptor();
          return paginationInterceptor;
      }
}
