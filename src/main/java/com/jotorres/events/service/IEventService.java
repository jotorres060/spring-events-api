package com.jotorres.events.service;

import com.jotorres.events.dto.EventRequestDto;
import com.jotorres.events.dto.EventResponseDto;

import java.util.List;

public interface IEventService {
    List<EventResponseDto> findAll();
    EventResponseDto findById(Long id);
    EventResponseDto save(EventRequestDto eventRequestDto);
    EventResponseDto update(Long id, EventRequestDto eventRequestDto);
    void deleteById(Long id);
}
