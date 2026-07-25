package com.springcourse.expert.user.infrastructure.api;

import com.springcourse.expert.common.application.mediator.Mediator;
import com.springcourse.expert.user.application.command.login.LoginUserRequest;
import com.springcourse.expert.user.application.command.login.LoginUserResponse;
import com.springcourse.expert.user.application.command.register.RegisterUserRequest;
import com.springcourse.expert.user.application.command.register.RegisterUserResponse;
import com.springcourse.expert.user.infrastructure.api.dto.LoginRequestDto;
import com.springcourse.expert.user.infrastructure.api.dto.RegisterRequestDto;
import com.springcourse.expert.user.infrastructure.api.dto.TokenResponseDto;
import com.springcourse.expert.user.infrastructure.api.mapper.UserMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * Clase que permite representar el controlador para poder manejar los users
 * */
@RestController
@RequestMapping("api/v1/users")
@Tag(name = "User", description = "User API requests")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final Mediator mediator;

    private final UserMapper userMapper;


    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {

        /*(RECUERDO)*/
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("password")
//                .roles("USER")
//                .build();
//
//        String token = jwtService.generateToken(user);
//        LoginResponseDto loginResponseDto = new LoginResponseDto();
//        loginResponseDto.setToken(token);


        LoginUserRequest request = userMapper.mapToLoginRequest(loginRequestDto);

        LoginUserResponse response = mediator.dispatch(request);

        TokenResponseDto tokenResponseDto = userMapper.mapToTokenResponseDto(response);

        return ResponseEntity.ok(tokenResponseDto);
    }

    @PostMapping("/register")
    public ResponseEntity<TokenResponseDto> register(@RequestBody RegisterRequestDto registerRequestDto) {

        /*(RECUERDO)*/
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("password")
//                .roles("USER")
//                .build();
//
//        String token = jwtService.generateToken(user);
//        TokenResponseDto tokenResponseDto = new TokenResponseDto();
//        tokenResponseDto.setToken(token);

        RegisterUserRequest request = userMapper.mapToRegisterUserRequest(registerRequestDto);

        RegisterUserResponse response = mediator.dispatch(request);

        TokenResponseDto tokenResponseDto = userMapper.mapToTokenResponseDto(response);
        
        return ResponseEntity.ok(tokenResponseDto);
    }
}
