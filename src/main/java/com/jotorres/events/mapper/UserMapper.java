package com.jotorres.events.mapper;

import com.jotorres.events.domain.Role;
import com.jotorres.events.domain.User;
import com.jotorres.events.dto.UserResponseDto;
import com.jotorres.events.exception.ResourceNotFoundException;
import com.jotorres.events.security.dto.RegisterDto;
import com.jotorres.events.repository.RoleRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    @Autowired
    protected RoleRepository roleRepository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", source = "registerDto.roles", qualifiedByName = "mapRoleStringsToRoles")
    @Mapping(target = "attendedEvents", ignore = true)
    public abstract User registerDtoToUser(RegisterDto registerDto);

    public abstract UserResponseDto toUserResponseDto(User user);
    public abstract List<UserResponseDto> toUserResponseDtoList(List<User> userList);

    @Named("mapRoleStringsToRoles")
    public Set<Role> mapRoleStringsToRoles(Set<String> roleNames) {
        if (roleNames == null || roleNames.isEmpty()) {
            return this.roleRepository.findByName("ROLE_USER")
                    .map(Collections::singleton)
                    .orElseThrow(() -> new ResourceNotFoundException("ROLE_USER does not exists."));
        }

        return roleNames.stream()
                .map((roleName) -> this.roleRepository.findByName(roleName)
                        .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + roleName)))
                .collect(Collectors.toSet());
    }
}
