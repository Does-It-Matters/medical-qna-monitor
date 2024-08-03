package com.example.monitoring.rsocket;

import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;

@Controller
public class RSocketController {

    private final Sinks.Many<String> alertSink = Sinks.many().multicast().onBackpressureBuffer();

    @MessageExceptionHandler
    public Mono<String> handleException(Exception e) {
        System.out.println("Exception: " + e.getMessage());
        return Mono.just("Exception: " + e.getMessage());
    }

    // == 기초 예제 ==
    // request-response 예제
    @MessageMapping("request-response")
    public Mono<String> requestResponse(String message) {
        return Mono.just("Response to: " + message);
    }

    // fire-and-forget 예제
    @MessageMapping("fire-and-forget")
    public Mono<Void> fireAndForget(String message) {
        System.out.println("Received: " + message);
        return Mono.empty();
    }

    // stream 예제
    @MessageMapping("stream")
    public Flux<String> stream(String message) {
        return Flux.interval(Duration.ofSeconds(1))
                .map(index -> "Stream response " + index + " to " + message)
                .take(10);
    }

    // channel 예제
    @MessageMapping("channel")
    public Flux<String> channel(Flux<String> messages) {
        return messages.map(message -> "Response to: " + message);
    }

// == 심화 예제 ==
// request-response 예제
//    @MessageMapping("request-response-with-metadata")
//    public Mono<String> requestResponseWithMetadata(String message, @Header("custom-header") String header) {
//        return Mono.just("Response to: " + message + " with header: " + header);
//    }
//
//    @MessageMapping("request-response-delayed")
//    public Mono<String> requestResponseDelayed(String message) {
//        return Mono.delay(Duration.ofSeconds(5))
//                .then(Mono.just("Delayed response to: " + message));
//    }
//

// fire-and-forget 예제
//    @MessageMapping("fire-and-forget-with-metadata")
//    public Mono<Void> fireAndForgetWithMetadata(String message, @Header("custom-header") String header) {
//        System.out.println("Received: " + message + " with header: " + header);
//        return Mono.empty();
//    }
//
//    @MessageMapping("fire-and-forget-delayed")
//    public Mono<Void> fireAndForgetDelayed(String message) {
//        return Mono.delay(Duration.ofSeconds(3))
//                .then(Mono.fromRunnable(() -> System.out.println("Received after delay: " + message)));
//    }
//

// stream 예제
//    @MessageMapping("stream-with-filter")
//    public Flux<String> streamWithFilter(String message) {
//        return Flux.interval(Duration.ofSeconds(1))
//                .zipWith(Flux.interval(Duration.ofSeconds(1))
//                        .map(index -> "Filtered response " + index + " to " + message))
//                .filter(tuple -> tuple.getT1() % 2 == 0)  // index가 짝수인 경우만 반환
//                .map(tuple -> tuple.getT2())
//                .take(10);
//    }
//
//    @MessageMapping("stream-with-combine")
//    public Flux<String> streamWithCombine(String message) {
//        Flux<String> stream1 = Flux.interval(Duration.ofSeconds(1))
//                .map(index -> "Stream1 response " + index + " to " + message)
//                .take(5);
//
//        Flux<String> stream2 = Flux.interval(Duration.ofSeconds(2))
//                .map(index -> "Stream2 response " + index + " to " + message)
//                .take(5);
//
//        return Flux.merge(stream1, stream2);
//    }
//

// channel 예제
//    @MessageMapping("channel-with-filter")
//    public Flux<String> channelWithFilter(Flux<String> messages) {
//        return messages
//                .filter(message -> message.length() > 5)
//                .map(message -> "Filtered response to: " + message);
//    }
//
//    @MessageMapping("channel-with-delay")
//    public Flux<String> channelWithDelay(Flux<String> messages) {
//        return messages
//                .delayElements(Duration.ofSeconds(1))
//                .map(message -> "Delayed response to: " + message);
//    }
}
