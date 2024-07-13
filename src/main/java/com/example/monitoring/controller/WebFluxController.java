package com.example.monitoring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class WebFluxController {

    @GetMapping("/webflux")
    public Mono<String> webflux() {
        return Mono.just("Hello, WebFlux!");
    }
}
