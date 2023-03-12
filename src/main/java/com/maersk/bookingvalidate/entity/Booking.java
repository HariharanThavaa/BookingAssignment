package com.maersk.bookingvalidate.entity;

import com.maersk.bookingvalidate.dto.BookingDto;
import com.maersk.bookingvalidate.dto.ContainerType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;

import static java.lang.String.format;

@Table("bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    private static int bookigCounter = 0;

    @PrimaryKey("booking_ref")
    private String bookingRef;
    @Column("container_type")
    private ContainerType containerType;
    @Column("container_size")
    private int containerSize;
    @Column("origin")
    private String origin;
    @Column("destination")
    private String destination;
    @Column("quantity")
    private int quantity;
    @Column("timestamp")
    private LocalDateTime timeStamp;

    public Booking(BookingDto bookingDto) {
        this.bookingRef = format("957%06d", ++bookigCounter);
        this.containerType = bookingDto.getContainerType();
        this.containerSize = bookingDto.getContainerSize();
        this.origin = bookingDto.getOrigin();
        this.destination = bookingDto.getDestination();
        this.quantity = bookingDto.getQuantity();
        this.timeStamp = bookingDto.getTimeStamp();
    }
}
