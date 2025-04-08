package com.example.demo.config;

import com.example.demo.feign.DemoFeignClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(
    clients  = {
        DemoFeignClient.class,
    }
)
public class FeignConfig {
}
