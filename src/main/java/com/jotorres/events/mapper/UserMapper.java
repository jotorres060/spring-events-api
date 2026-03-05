package com.jotorres.events.mapper;

import com.jotorres.events.domain.User;
import com.jotorres.events.dto.RegisterDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    User registerDtoToUser(RegisterDto registerDto);
}
