package com.springcourse.expert.user.infrastructure;

import lombok.Data;

/*
 * Clase que permite retornar los datos del login
 * */
@Data
public class LoginResponseDto {

    private String token;
}
