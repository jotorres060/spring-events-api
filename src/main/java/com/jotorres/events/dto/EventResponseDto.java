package com.jotorres.events.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EventResponseDto {
    Long id;
    String name;
    LocalDate date;
    String location;
}
