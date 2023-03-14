package com.maersk.bookingvalidate.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.maersk.bookingvalidate.service.CheckAvailabilityService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

@TestConfiguration
public class TestConfig {
    @Bean
    public WireMockServer webServer() {
        WireMockServer wireMockServer = new WireMockServer(options().dynamicPort());
        // required so we can use `baseUrl()` in the construction of `webClient` below
        wireMockServer.start();
        return wireMockServer;
    }

    @Bean
    public WebClient webClient(WireMockServer server) {
        return WebClient.builder().baseUrl(server.baseUrl()).build();
    }

    @Bean
    public CheckAvailabilityService client(WebClient webClient) {
        return new CheckAvailabilityService(webClient);
    }
}
