package com.rnd4impact.apigateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableHystrix
public class Config {

    @Autowired
    private AuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("identity", r -> r.path("/identity/**")
                .filters(f -> f.filter(filter))
                .uri("lb://identity"))
            .route("book", r -> r.path("/book/**")
                .filters(f -> f.filter(filter))
                .uri("lb://book"))
            .route("authentication-service", r -> r.path("/auth/**")
                .filters(f -> f.filter(filter))
                .uri("lb://authentication-service"))
            .route("notification-service", r -> r.path("/notification/**")
                .filters(f -> f.filter(filter))
                .uri("lb://notification-service"))
            .build();
    }

    /*@Bean
    public HttpClient httpClient() {
        return HttpClient.create().resolver(DefaultAddressResolverGroup.INSTANCE);
    }*/
}
