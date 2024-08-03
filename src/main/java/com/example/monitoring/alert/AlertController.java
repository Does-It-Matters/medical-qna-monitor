package com.example.monitoring.alert;

import org.springframework.messaging.handler.annotation.MessageMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public class AlertController {
    private final Sinks.Many<String> ALERT_SINK = Sinks.many().multicast().onBackpressureBuffer();

    // 서버가 클라이언트에게 알림을 보내는 엔드포인트
    @MessageMapping("alert-stream")
    public Flux<String> alertStream() {
        return ALERT_SINK.asFlux();
    }

    // 특정 상황에서 클라이언트에게 알림을 보내는 메서드
    public void triggerAlert(String alertMessage) {
        ALERT_SINK.tryEmitNext(alertMessage);
    }
}
