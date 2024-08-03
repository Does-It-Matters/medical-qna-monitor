package com.example.monitoring.log;

import org.springframework.messaging.handler.annotation.MessageMapping;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class LogStreamController {

    // 비동기 로그 스트림을 전송하는 엔드포인트
    @MessageMapping("log-stream")
    public Flux<String> streamLogs() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(index -> "Log entry " + index);
    }
}
