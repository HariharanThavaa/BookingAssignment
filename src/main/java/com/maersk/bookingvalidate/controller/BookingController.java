package com.maersk.bookingvalidate.controller;

import com.maersk.bookingvalidate.dto.AvailableDto;
import com.maersk.bookingvalidate.dto.BookingDto;
import com.maersk.bookingvalidate.dto.BookingRefDto;
import com.maersk.bookingvalidate.dto.CheckAvailabilityDto;
import com.maersk.bookingvalidate.service.BookingService;
import com.maersk.bookingvalidate.service.CheckAvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("api/bookings")
public class BookingController {

    @Autowired
    private CheckAvailabilityService checkAvailabilityService;

    @Autowired
    private BookingService bookingService;

    @PostMapping("checkAvailable")
    public Mono<AvailableDto> checkAvailability(@Valid @RequestBody Mono<CheckAvailabilityDto> checkAvailabilityDto) {
        return this.checkAvailabilityService.check(checkAvailabilityDto);
    }

    @PostMapping("book")
    public Flux<BookingRefDto> book(@Valid @RequestBody Mono<BookingDto> bookingDto) {
        return this.bookingService.book(bookingDto);
    }

}
