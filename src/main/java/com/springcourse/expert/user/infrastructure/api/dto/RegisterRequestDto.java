package com.springcourse.expert.user.infrastructure.api.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

/*
 * Clase que define el dto para poder pedir la request del login
 * */
@Data
public class RegisterRequestDto {

    @Email
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
