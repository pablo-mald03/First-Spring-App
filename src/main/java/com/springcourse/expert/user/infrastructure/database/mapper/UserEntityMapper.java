package com.springcourse.expert.user.infrastructure.database.mapper;

import com.springcourse.expert.user.domain.User;
import com.springcourse.expert.user.infrastructure.database.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserEntityMapper {

    /*
     * SE INDICA QUE NO SE DEBEN MAPEAR LOS ATRIBUTOS HEREDADOS DE UserDetails
     * */
    @Mapping(target = "authorities", ignore = true)
    UserEntity mapToUserEntity(User user);

    User mapToUser(UserEntity userEntity);
}
