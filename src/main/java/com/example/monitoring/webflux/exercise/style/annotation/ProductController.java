package com.example.monitoring.webflux.exercise.style.annotation;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/annotation/product")
public class ProductController {

    @GetMapping("/{id}")
    public Mono<String> getProduct(@PathVariable String id) {
        return Mono.just("Product ID: " + id);
    }

    @PostMapping
    public Mono<String> createProduct() {
        return Mono.just("Product created");
    }

    @PutMapping("/{id}")
    public Mono<String> updateProduct(@PathVariable String id) {
        return Mono.just("Product ID: " + id + " updated");
    }

    @DeleteMapping("/{id}")
    public Mono<String> deleteProduct(@PathVariable String id) {
        return Mono.just("Product ID: " + id + " deleted");
    }
}
