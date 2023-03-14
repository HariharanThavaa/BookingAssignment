package com.maersk.bookingvalidate.repository;


import com.maersk.bookingvalidate.dto.BookingDto;
import com.maersk.bookingvalidate.entity.Booking;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.cassandra.DataCassandraTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static com.maersk.bookingvalidate.dto.ContainerType.DRY;

@DataCassandraTest
@ExtendWith(SpringExtension.class)
public class BookingRepositoryTest {

    @Autowired
    private BookingRepository repository;

    @Test
    public void shouldSaveBooking() {

        BookingDto bookingDto = new BookingDto(DRY, 20, "Southampton", "Singapore", 5, LocalDateTime.now());
        Booking booking = new Booking(bookingDto);

        Flux<Booking> setup = repository.deleteAll().thenMany(repository.insert(booking));

        StepVerifier
                .create(setup)
                .expectNextCount(1)
                .verifyComplete();
    }

}
