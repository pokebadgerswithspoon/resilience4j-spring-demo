package com.example.demo.feign;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResilientDemoClient implements DemoFeignClient {

    private final DemoFeignClient feignClient;

    @Retry(name = "demo")
    @Override
    public String ping() {
        return feignClient.ping();
    }
}
