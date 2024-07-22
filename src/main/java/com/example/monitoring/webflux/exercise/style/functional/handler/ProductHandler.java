//package com.example.monitoring.webflux.exercise.functional.handler;
//
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.function.server.ServerRequest;
//import org.springframework.web.reactive.function.server.ServerResponse;
//import reactor.core.publisher.Mono;
//
//@Component
//public class ProductHandler {
//
//    public Mono<ServerResponse> getProduct(ServerRequest request) {
//        String productId = request.pathVariable("id");
//        return ServerResponse.ok().bodyValue("Product ID: " + productId);
//    }
//
//    public Mono<ServerResponse> createProduct(ServerRequest request) {
//        return ServerResponse.ok().bodyValue("Product created");
//    }
//
//    public Mono<ServerResponse> updateProduct(ServerRequest request) {
//        String productId = request.pathVariable("id");
//        return ServerResponse.ok().bodyValue("Product ID: " + productId + " updated");
//    }
//
//    public Mono<ServerResponse> deleteProduct(ServerRequest request) {
//        String productId = request.pathVariable("id");
//        return ServerResponse.ok().bodyValue("Product ID: " + productId + " deleted");
//    }
//}
