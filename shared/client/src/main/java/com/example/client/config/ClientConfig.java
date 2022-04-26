package com.example.client.config;

import com.example.CoreApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackageClasses = CoreApplication.class)
@Configuration
public class ClientConfig {

}
