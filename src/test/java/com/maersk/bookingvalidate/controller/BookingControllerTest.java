package com.maersk.bookingvalidate.controller;

import com.maersk.bookingvalidate.dto.AvailableDto;
import com.maersk.bookingvalidate.dto.BookingDto;
import com.maersk.bookingvalidate.dto.BookingRefDto;
import com.maersk.bookingvalidate.dto.CheckAvailabilityDto;
import com.maersk.bookingvalidate.service.BookingService;
import com.maersk.bookingvalidate.service.CheckAvailabilityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static com.maersk.bookingvalidate.dto.ContainerType.DRY;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(BookingController.class)
class BookingControllerTest {

    @MockBean
    private CheckAvailabilityService checkAvailabilityService;

    @MockBean
    private BookingService bookingService;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testCheckAvailability() {
        Mono<CheckAvailabilityDto> checkAvailabilityDto=
                Mono.just(new CheckAvailabilityDto(DRY, 20, "Southampton", "Singapore", 5));
        when(checkAvailabilityService.check(checkAvailabilityDto))
                .thenReturn(Mono.just(new AvailableDto(true)));

        webTestClient.post()
                .uri("/api/bookings/checkAvailable")
                .body(Mono.just(checkAvailabilityDto), CheckAvailabilityDto.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testBook() {
        Mono<BookingDto> bookingDto =
                Mono.just(new BookingDto(DRY, 30, "Southampton", "Singapore", 5, LocalDateTime.now()));
        when(bookingService.book(bookingDto)).thenReturn(Flux.just(new BookingRefDto("957000002")));

        webTestClient.post()
                .uri("/api/bookings/book")
                .body(Mono.just(bookingDto), BookingDto.class)
                .exchange().expectStatus().isOk();
    }


}