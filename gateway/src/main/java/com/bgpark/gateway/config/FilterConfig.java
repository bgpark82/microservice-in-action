package com.bgpark.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class FilterConfig {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/item-service/**")
                    .filters(f -> f.addRequestHeader("item-request","item-request-header")
                                    .addResponseHeader("item-response","item-response-header"))
                    .uri("http://localhost:8081")
                ).build();
    }

}
