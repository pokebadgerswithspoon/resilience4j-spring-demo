package com.example.demo.feign;

import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
    name = "demo",
    url = "http://localhost:8080",
//    url = "https://stackoverflow.com",
    path = "/"
//    primary = false
)
@Retry(name = "demo")
public interface DemoFeignClient {

    @GetMapping("/")
    String ping();
}
