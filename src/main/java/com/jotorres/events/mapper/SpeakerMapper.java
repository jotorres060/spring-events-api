package com.jotorres.events.mapper;

import com.jotorres.events.domain.Speaker;
import com.jotorres.events.dto.SpeakerDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpeakerMapper {
    Speaker toEntity(SpeakerDto dto);
    SpeakerDto toDto(Speaker entity);
}
