package com.example.monitoring.webflux.exercise.style.annotation;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/annotation/user")
public class UserController {

    @GetMapping("/{id}")
    public Mono<String> getUser(@PathVariable String id) {
        return Mono.just("User ID: " + id);
    }

    @PostMapping
    public Mono<String> createUser() {
        return Mono.just("User created");
    }

    @PutMapping("/{id}")
    public Mono<String> updateUser(@PathVariable String id) {
        return Mono.just("User ID: " + id + " updated");
    }

    @DeleteMapping("/{id}")
    public Mono<String> deleteUser(@PathVariable String id) {
        return Mono.just("User ID: " + id + " deleted");
    }
}
