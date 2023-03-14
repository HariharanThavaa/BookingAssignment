package com.maersk.bookingvalidate.service;

import com.maersk.bookingvalidate.dto.BookingDto;
import com.maersk.bookingvalidate.entity.Booking;
import com.maersk.bookingvalidate.repository.BookingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;


import java.time.LocalDateTime;

import static com.maersk.bookingvalidate.dto.ContainerType.DRY;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    BookingRepository bookingRepository;

    @Autowired
    BookingService bookingService;

    @Test
    void book() {
        Booking booking = new Booking(new BookingDto(DRY, 20, "Southampton", "Singapore", 5, LocalDateTime.now()));
        when(bookingRepository.insert(booking))
                .thenReturn(Mono
                        .just(new Booking("957000002",DRY, 20, "Southampton", "Singapore", 5, LocalDateTime.now())));
    }
}