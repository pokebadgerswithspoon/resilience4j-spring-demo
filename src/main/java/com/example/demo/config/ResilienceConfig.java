package com.example.demo.config;

import io.github.resilience4j.common.retry.configuration.RetryConfigCustomizer;
import io.github.resilience4j.retry.RetryRegistry;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.function.Predicate;

@Configuration
@Slf4j
public class ResilienceConfig implements ApplicationListener<ContextRefreshedEvent> {

    @Bean
    public RetryConfigCustomizer retryCustomizer() {
        Predicate<Throwable> retryExPredicate = this::considerRetryEx;
        Predicate<Object> retryResultPredicate = this::considerRetry;
        return RetryConfigCustomizer
            .of("demo",
                builder-> builder
                    .retryOnResult(retryResultPredicate)
                    .retryOnException(ex -> {
                        log.info("asdfasdf");
                        return true;
                    })
//                    .retryExceptions(Throwable.class)
            );
    }

    private boolean considerRetryEx(Throwable throwable) {
        log.info("A problem: {}", throwable.getMessage());
       return true;
    }
    private boolean considerRetry(Object o) {
        log.info("Yey: {}", o);
        return true;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent ctxEvent) {
        RetryRegistry retryRegistry = ctxEvent.getApplicationContext()
            .getBean(RetryRegistry.class);
        retryRegistry.getAllRetries()
            .forEach(retry -> retry
                .getEventPublisher()
                .onRetry(event -> log.info("{}", event))
            );
    }
}
