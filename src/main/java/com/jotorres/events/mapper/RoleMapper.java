package com.jotorres.events.mapper;

import com.jotorres.events.domain.Role;
import com.jotorres.events.dto.RoleDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toEntity(RoleDto dto);
    RoleDto toDto(Role entity);
    List<RoleDto> toDtoList(List<Role> roleList);
}
