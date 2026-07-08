package com.springcourse.expert.category.infrastructure;

import com.springcourse.expert.category.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CategoryEntityMapper {

    @Mapping(target = "products", ignore = true)
    Category mapToCategory(CategoryEntity categoryEntity);
}
