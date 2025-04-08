package com.example.demo.listeners;

import com.example.demo.feign.DemoFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

//@Component
@RequiredArgsConstructor
public class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    private final DemoFeignClient demoFeignClient;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        demoFeignClient.ping();
    }
}
