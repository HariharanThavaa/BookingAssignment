package com.maersk.bookingvalidate.controller;

import com.maersk.bookingvalidate.dto.AvailableDto;
import com.maersk.bookingvalidate.dto.CheckAvailabilityDto;
import com.maersk.bookingvalidate.service.CheckAvailabilityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static com.maersk.bookingvalidate.dto.ContainerType.DRY;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(BookingController.class)
class BookingControllerTest {

    @MockBean
    private CheckAvailabilityService checkAvailabilityService;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void checkAvailability() {
        Mono<CheckAvailabilityDto> bookingDtoMono = Mono.just(new CheckAvailabilityDto(DRY, 20, "Southampton", "Singapore", 5));
        when(checkAvailabilityService.check(bookingDtoMono)).thenReturn(Mono.just(new AvailableDto(true)));

        webTestClient.post()
                .uri("/api/bookings/checkAvailable")
                .body(Mono.just(bookingDtoMono), CheckAvailabilityDto.class)
                .exchange().expectStatus().isOk();
    }
}