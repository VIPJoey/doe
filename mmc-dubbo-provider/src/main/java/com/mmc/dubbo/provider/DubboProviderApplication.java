package com.mmc.dubbo.provider;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication
public class DubboProviderApplication {

    public static void main(String[] args) {

        SpringApplication springApplication = new SpringApplication(DubboProviderApplication.class);
        springApplication.run(args);
    }
}

