package com.maersk.bookingvalidate.service;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.maersk.bookingvalidate.config.TestConfig;
import com.maersk.bookingvalidate.dto.AvailableDto;
import com.maersk.bookingvalidate.dto.AvailableSpaceDto;
import com.maersk.bookingvalidate.dto.CheckAvailabilityDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.maersk.bookingvalidate.dto.ContainerType.DRY;
import static com.maersk.bookingvalidate.dto.ContainerType.REEFER;

@AutoConfigureJsonTesters
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@Import(TestConfig.class)

class CheckAvailabilityServiceTest {

    private static final String CHECK_AVAILABLE_END_POINT = "/api/bookings-mock/checkAvailable";

    @Autowired
    private WireMockServer server;

    @Autowired
    private CheckAvailabilityService checkAvailabilityService;

    @Autowired
    private JacksonTester<AvailableSpaceDto> jsonAvailableSpace;

    @Test
    void testCheckWhenExternalApiReturnsNotAvailable() throws IOException {
        server.stubFor(
                post(urlEqualTo(CHECK_AVAILABLE_END_POINT))
                        .willReturn(
                                aResponse()
                                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                        .withBody(jsonAvailableSpace.write(new AvailableSpaceDto(0)).getJson())));

        CheckAvailabilityDto checkAvailabilityDto = new CheckAvailabilityDto(REEFER, 20, "Southampton", "Singapore", 5);

        Mono<AvailableDto> response = checkAvailabilityService.check(Mono.just(checkAvailabilityDto));

        StepVerifier.create(response)
                .expectSubscription()
                .expectNext(new AvailableDto(false))
                .verifyComplete();
    }

    @Test
    void testCheckWhenExternalApiReturnsAvailable() throws IOException {
        server.stubFor(
                post(urlEqualTo(CHECK_AVAILABLE_END_POINT))
                        .willReturn(
                                aResponse()
                                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                        .withBody(jsonAvailableSpace.write(new AvailableSpaceDto(6)).getJson())));

        CheckAvailabilityDto checkAvailabilityDto = new CheckAvailabilityDto(DRY, 20, "Southampton", "Singapore", 5);

        Mono<AvailableDto> response = checkAvailabilityService.check(Mono.just(checkAvailabilityDto));

        StepVerifier.create(response)
                .expectSubscription()
                .expectNext(new AvailableDto(true))
                .verifyComplete();
    }
}