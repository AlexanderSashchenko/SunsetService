package com.datax.sunset.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@SpringBootTest
class SunriseSunsetApiCommunicationServiceImplTest {

    public static WebTestClient testClient;

    @BeforeAll
    static void setup() {
        testClient = WebTestClient.bindToServer()
                .baseUrl("https://api.sunrise-sunset.org")
                .build();
    }

    @Test
    void testSunsetSunriseApiAvailability() {
        RouterFunction<?> function = RouterFunctions.route(
                RequestPredicates.GET("/json"),
                request -> ServerResponse.ok().build()
        );

        WebTestClient
                .bindToRouterFunction(function)
                .build().get().uri("/json")
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();
    }
}