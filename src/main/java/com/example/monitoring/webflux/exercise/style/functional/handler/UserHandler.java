package com.example.monitoring.webflux.exercise.style.functional.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class UserHandler {

    public Mono<ServerResponse> getUser(ServerRequest request) {
        String userId = request.pathVariable("id");
        return ServerResponse.ok().bodyValue("User ID: " + userId);
    }

    public Mono<ServerResponse> createUser(ServerRequest request) {
        return ServerResponse.ok().bodyValue("User created");
    }

    public Mono<ServerResponse> updateUser(ServerRequest request) {
        String userId = request.pathVariable("id");
        return ServerResponse.ok().bodyValue("User ID: " + userId + " updated");
    }

    public Mono<ServerResponse> deleteUser(ServerRequest request) {
        String userId = request.pathVariable("id");
        return ServerResponse.ok().bodyValue("User ID: " + userId + " deleted");
    }
}
