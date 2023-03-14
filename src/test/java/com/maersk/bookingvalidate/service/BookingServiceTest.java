package com.maersk.bookingvalidate.service;

import com.maersk.bookingvalidate.dto.BookingDto;
import com.maersk.bookingvalidate.dto.BookingRefDto;
import com.maersk.bookingvalidate.entity.Booking;
import com.maersk.bookingvalidate.repository.BookingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static com.maersk.bookingvalidate.dto.ContainerType.DRY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class BookingServiceTest {

    @InjectMocks
    private BookingService bookingService;

    @Mock
    private BookingRepository bookingRepository;


    @Test
    public void shouldGetBookingRef() {

        BookingDto bookingDto = new BookingDto(DRY, 20, "Southampton", "Singapore", 5, LocalDateTime.now());
        Booking booking = new Booking(bookingDto);
        Flux<Booking> bookingFlux = Flux.just(booking);

        when(bookingRepository.insert((Mono<Booking>)any())).thenReturn(bookingFlux);

        Flux<BookingRefDto> bookingRefDtoFlux = bookingService.book(Mono.just(bookingDto));

        StepVerifier
                .create(bookingRefDtoFlux)
                .consumeNextWith(dto -> {
                    assertEquals(dto.getBookingRef(), booking.getBookingRef());
                })
                .verifyComplete();
    }

}
