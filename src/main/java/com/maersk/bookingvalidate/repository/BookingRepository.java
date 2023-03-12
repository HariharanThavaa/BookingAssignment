package com.maersk.bookingvalidate.repository;

import com.maersk.bookingvalidate.entity.Booking;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

public interface BookingRepository extends ReactiveCassandraRepository<Booking, String> {}
