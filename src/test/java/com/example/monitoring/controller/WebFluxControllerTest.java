package com.example.monitoring.controller;

import com.example.monitoring.webflux.exercise.style.annotation.ProductController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest(ProductController.class) // 테스트 하려는 클래스만 로드하여 테스트
public class WebFluxControllerTest {

    @Autowired
    private WebTestClient webTestClient; // webflux 클라이언트 : HTTP 요청 수행

    @Test
    public void testWebFluxSuccessEndpoint() {
        // given: 초기 설정

        // when: 테스트 수행
        webTestClient.get().uri("/annotation/product/1")
                .exchange() // 요청 후 응답 받음
                // then: 결과 검증
                .expectStatus().isOk() // 상태 코드 검증
                .expectBody(String.class).isEqualTo("Product ID: 1"); // 응답 바디 검증
    }

    @Test
    public void testWebFluxFailEndpoint() {
        webTestClient.get().uri("/annotation/product/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("Hello, WebFlux");
    }
}
