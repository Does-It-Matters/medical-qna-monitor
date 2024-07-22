//package com.example.monitoring.webflux.exercise.functional.router;
//
//import com.example.monitoring.webflux.exercise.functional.handler.ProductHandler;
//import com.example.monitoring.webflux.exercise.functional.handler.UserHandler;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.reactive.function.server.RouterFunction;
//import org.springframework.web.reactive.function.server.RouterFunctions;
//import org.springframework.web.reactive.function.server.ServerResponse;
//
//import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
//import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
//import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
//import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
//
//@Configuration
//public class RouterConfiguration {
//
//    @Bean
//    public RouterFunction<ServerResponse> userRoutes(UserHandler userHandler) {
//        return RouterFunctions
//                .route(GET("/functional/user/{id}"), userHandler::getUser)
//                .andRoute(POST("/functional/user"), userHandler::createUser)
//                .andRoute(PUT("/functional/user/{id}"), userHandler::updateUser)
//                .andRoute(DELETE("/functional/user/{id}"), userHandler::deleteUser);
//    }
//
//    @Bean
//    public RouterFunction<ServerResponse> productRoutes(ProductHandler productHandler) {
//        return RouterFunctions
//                .route(GET("/functional/product/{id}"), productHandler::getProduct)
//                .andRoute(POST("/functional/product"), productHandler::createProduct)
//                .andRoute(PUT("/functional/product/{id}"), productHandler::updateProduct)
//                .andRoute(DELETE("/functional/product/{id}"), productHandler::deleteProduct);
//    }
//}
