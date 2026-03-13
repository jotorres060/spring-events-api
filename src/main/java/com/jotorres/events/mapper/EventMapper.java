package com.jotorres.events.mapper;

import com.jotorres.events.domain.Event;
import com.jotorres.events.dto.EventRequestDto;
import com.jotorres.events.dto.EventResponseDto;
import com.jotorres.events.dto.EventSummaryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {
    @Mapping(target = "speakers", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "attendedUsers", ignore = true)
    Event toEntity(EventRequestDto dto);

    EventResponseDto toDto(Event entity);

    List<EventResponseDto> toEventResponseDtoList(List<Event> eventList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "speakers", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "attendedUsers", ignore = true)
    void updateEventFromDto(EventRequestDto dto, @MappingTarget Event entity);

    EventSummaryDto toEventSummaryDto(Event event);
    List<EventSummaryDto> toEventSummaryDtoList(List<Event> eventList);
}
