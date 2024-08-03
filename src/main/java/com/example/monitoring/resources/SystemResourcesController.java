package com.example.monitoring.resources;

import org.springframework.messaging.handler.annotation.MessageMapping;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class SystemResourcesController {

    // 주기적으로 시스템 리소스를 전송하는 엔드포인트
    @MessageMapping("system-resources")
    public Flux<String> streamSystemResources() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(index -> "System resource usage at interval " + index + ": CPU: 50%, Memory: 70%");
    }
}
