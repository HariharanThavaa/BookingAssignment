package com.maersk.bookingvalidate.dto;

import com.maersk.bookingvalidate.validator.OneOf;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CheckAvailabilityDto {
    private ContainerType containerType;
    @OneOf({20,40})
    private int containerSize;
    @Size(min = 5, max = 20)
    private String origin;
    @Size(min = 5, max = 20)
    private String destination;
    @Min(value = 1)
    @Max(value = 100)
    private int quantity;
}
