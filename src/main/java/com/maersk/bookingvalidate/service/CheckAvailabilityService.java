package com.maersk.bookingvalidate.service;

import com.maersk.bookingvalidate.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.validation.constraints.AssertTrue;
import org.springframework.beans.factory.annotation.Value;

@Service
@Slf4j
public class CheckAvailabilityService {

    @Value( "${external.service.base.uri}" )
    private String baseUri;
    @Value( "${external.service.checkAvailable.endpoint}" )
    private String checkAvailableEndpoint;

    @AssertTrue
    public Mono<AvailableDto> check(Mono<CheckAvailabilityDto> checkAvailabilityDtoMono){
        return checkAvailabilityDtoMono.doOnNext(req -> log.info(req.toString()))
                .flatMap(this::checkAvailability);
    }


    public Mono<AvailableDto> checkAvailability(CheckAvailabilityDto checkAvailabilityDto) {
        return WebClient.create(baseUri).post()
                .uri(checkAvailableEndpoint)
                .body(Mono.just(checkAvailabilityDto), CheckAvailabilityDto.class)
                .exchangeToMono(response -> response.bodyToMono(AvailableSpaceDto.class)
                        .doOnNext(res -> log.info(res.toString()))
                                .map(res -> new AvailableDto(res.getSpace() > 0)))
                .doOnNext(res -> log.info(res.toString()));
    }
}
