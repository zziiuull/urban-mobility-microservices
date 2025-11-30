package com.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.path;

@SpringBootApplication
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    //TODO: verify services ports
    @Bean
    public RouterFunction<ServerResponse> customRoutes() {
        return
            route("ride_service")
            .route(path("/api/ride/**"), http())
            .before(uri("http://localhost:8081"))
            .build()

            .and(route("driver_service")
                    .route(path("/api/driver/**"), http())
                    .before(uri("http://localhost:8082"))
                    .build())

            .and(route("passenger_service")
                .route(path("/api/passenger/**"), http())
                .before(uri("http://localhost:8083"))
                .build());
    }
}
