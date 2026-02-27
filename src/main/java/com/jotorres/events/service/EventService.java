package com.jotorres.events.service;

import com.jotorres.events.domain.Event;
import com.jotorres.events.dto.EventRequestDto;
import com.jotorres.events.dto.EventResponseDto;
import com.jotorres.events.exception.ResourceNotFoundException;
import com.jotorres.events.mapper.EventMapper;
import com.jotorres.events.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventService implements IEventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public List<EventResponseDto> findAll() {
        return this.eventRepository.findAll().stream()
                .map(this.eventMapper::toDto)
                .toList();
    }

    @Override
    public EventResponseDto findById(Long id) {
        Event event = this.getEventOrThrow(id);
        return this.eventMapper.toDto(event);
    }

    @Transactional
    @Override
    public EventResponseDto save(EventRequestDto eventRequestDto) {
        Event event = this.eventMapper.toEntity(eventRequestDto);
        Event savedEvent = this.eventRepository.save(event);
        return this.eventMapper.toDto(savedEvent);
    }

    @Transactional
    @Override
    public EventResponseDto update(Long id, EventRequestDto eventRequestDto) {
        Event existingEvent = this.getEventOrThrow(id);
        this.eventMapper.updateEventFromDto(eventRequestDto, existingEvent);
        return this.eventMapper.toDto(existingEvent);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        Event event = this.getEventOrThrow(id);
        this.eventRepository.delete(event);
    }

    private Event getEventOrThrow(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));
    }
}
