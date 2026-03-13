package com.jotorres.events.mapper;

import com.jotorres.events.domain.Category;
import com.jotorres.events.dto.CategoryDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toEntity(CategoryDto dto);
    CategoryDto toDto(Category entity);
}
