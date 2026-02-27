package com.jotorres.events.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EventRequestDto {
    @NotBlank(message = "Name should not be empty")
    String name;

    @FutureOrPresent(message = "Date should be a valid date (present or future)")
    @NotNull(message = "Date should not be null")
    LocalDate date;

    @NotBlank(message = "Location should not be empty")
    String location;
}
