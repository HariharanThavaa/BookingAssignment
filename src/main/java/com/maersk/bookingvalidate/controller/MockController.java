package com.maersk.bookingvalidate.controller;

import com.maersk.bookingvalidate.dto.AvailableSpaceDto;
import com.maersk.bookingvalidate.dto.CheckAvailabilityDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("api/bookings-mock")
public class MockController {

    @PostMapping("checkAvailable")
    public Mono<AvailableSpaceDto> availableSpaceDtoMono(@Valid @RequestBody Mono<CheckAvailabilityDto> bookingDtoMono) {
        return bookingDtoMono.doOnNext(req -> log.info(req.toString()))
                .map(req -> req.getContainerSize() == 20 ?
                        new AvailableSpaceDto(6) :
                        new AvailableSpaceDto(0));
    }
}
