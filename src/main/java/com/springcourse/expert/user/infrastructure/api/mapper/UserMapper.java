package com.springcourse.expert.user.infrastructure.api.mapper;

import com.springcourse.expert.user.application.command.login.LoginUserRequest;
import com.springcourse.expert.user.application.command.login.LoginUserResponse;
import com.springcourse.expert.user.application.command.register.RegisterUserRequest;
import com.springcourse.expert.user.application.command.register.RegisterUserResponse;
import com.springcourse.expert.user.infrastructure.api.dto.LoginRequestDto;
import com.springcourse.expert.user.infrastructure.api.dto.RegisterRequestDto;
import com.springcourse.expert.user.infrastructure.api.dto.TokenResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserMapper {

    LoginUserRequest mapToLoginRequest(LoginRequestDto token);

    RegisterUserRequest mapToRegisterUserRequest(RegisterRequestDto registerRequestDto);

    TokenResponseDto mapToTokenResponseDto(LoginUserResponse loginUserResponse);

    TokenResponseDto mapToTokenResponseDto(RegisterUserResponse registerUserResponse);
}
