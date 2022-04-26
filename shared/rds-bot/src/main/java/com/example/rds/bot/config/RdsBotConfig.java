package com.example.rds.bot.config;

import com.example.CoreApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@EnableJdbcRepositories(basePackageClasses = CoreApplication.class)
@MapperScan(basePackageClasses = CoreApplication.class)
@Configuration
public class RdsBotConfig {
}
