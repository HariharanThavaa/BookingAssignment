package com.maersk.bookingvalidate.service;

import com.maersk.bookingvalidate.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.validation.constraints.AssertTrue;


@Slf4j
@Service
public class CheckAvailabilityService {

    private static final String BASE_URI = "http://localhost:9092";
    private static final String CHECK_AVAILABLE_END_POINT = "/api/bookings-mock/checkAvailable";

    private final WebClient webClient;

    // Default usages
    public CheckAvailabilityService() {
        webClient = WebClient.create(BASE_URI);
    }

    // For integration Test
    public CheckAvailabilityService(WebClient webClient) {
        this.webClient = webClient;
    }

    @AssertTrue
    public Mono<AvailableDto> check(Mono<CheckAvailabilityDto> checkAvailabilityDtoMono){
        return checkAvailabilityDtoMono.flatMap(this::checkAvailability);
    }

    public Mono<AvailableDto> checkAvailability(CheckAvailabilityDto checkAvailabilityDto) {
        return webClient.post()
                .uri(CHECK_AVAILABLE_END_POINT)
                .body(Mono.just(checkAvailabilityDto), CheckAvailabilityDto.class)
                .exchangeToMono(response -> response.bodyToMono(AvailableSpaceDto.class)
                        .map(res -> new AvailableDto(res.getSpace() > 0)));
    }
}
