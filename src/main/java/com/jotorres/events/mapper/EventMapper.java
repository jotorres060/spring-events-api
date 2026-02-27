package com.jotorres.events.mapper;

import com.jotorres.events.domain.Event;
import com.jotorres.events.dto.EventRequestDto;
import com.jotorres.events.dto.EventResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EventMapper {
    Event toEntity(EventRequestDto dto);
    EventResponseDto toDto(Event entity);
    void updateEventFromDto(EventRequestDto dto, @MappingTarget Event entity);
}
