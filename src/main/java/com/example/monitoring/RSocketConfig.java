package com.example.monitoring;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler;
import org.springframework.web.util.pattern.PathPatternRouteMatcher;

@Configuration
public class RSocketConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        // 필요한 ObjectMapper 설정을 추가
        return mapper;
    }

    @Bean
    public RSocketStrategies rSocketStrategies(ObjectMapper objectMapper) {
        return RSocketStrategies.builder()
                .decoder(new Jackson2JsonDecoder(objectMapper))
                .encoder(new Jackson2JsonEncoder(objectMapper))
                .build();
    }

    @Bean
    public RSocketMessageHandler rsocketMessageHandler(RSocketStrategies rSocketStrategies) {
        RSocketMessageHandler handler = new RSocketMessageHandler();
        handler.setRSocketStrategies(rSocketStrategies);
        handler.setRouteMatcher(new PathPatternRouteMatcher());
        return handler;
    }
}