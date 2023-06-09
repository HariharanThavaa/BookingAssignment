package com.maersk.bookingvalidate.service;

import com.maersk.bookingvalidate.dto.BookingDto;
import com.maersk.bookingvalidate.dto.BookingRefDto;
import com.maersk.bookingvalidate.entity.Booking;
import com.maersk.bookingvalidate.repository.BookingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BookingService {

    @Autowired
    private BookingRepository repository;

    public Flux<BookingRefDto> book(Mono<BookingDto> bookingDto){
        return repository.insert(bookingDto.map(dto -> new Booking(dto)))
                .map(ent -> new BookingRefDto(ent.getBookingRef()));
    }
}
