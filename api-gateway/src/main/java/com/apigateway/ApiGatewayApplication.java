package com.apigateway;

import org.springframework.beans.factory.annotation.Value;
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

    @Value("${RIDE_SERVICE_URL}")
    private String rideUrl;

    @Value("${DRIVER_SERVICE_URL}")
    private String driverUrl;

    @Value("${PASSENGER_SERVICE_URL}")
    private String passengerUrl;

    @Bean
    public RouterFunction<ServerResponse> customRoutes() {

        return
            route("ride_service")
            .route(path("/api/ride/**"), http())
            .before(uri(rideUrl))
            .build()

            .and(route("driver_service")
                    .route(path("/api/driver/**"), http())
                    .before(uri(driverUrl))
                    .build())

            .and(route("passenger_service")
                .route(path("/api/passenger/**"), http())
                .before(uri(passengerUrl))
                .build());
    }
}
