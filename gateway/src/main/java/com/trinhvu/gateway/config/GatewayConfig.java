//package com.trinhvu.gateway.config;
//
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class GatewayConfig {
//
//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route("websocket_route", r -> r.path("/ws-stock-updates")
//                        .uri("ws://localhost:8222")) // Kết nối đến WebSocket server của bạn
//                .build();
//    }
//}