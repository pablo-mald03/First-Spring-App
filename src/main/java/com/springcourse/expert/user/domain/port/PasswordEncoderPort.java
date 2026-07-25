package com.springcourse.expert.user.domain.port;

/*
 * Interface que define la forma en la que se va a encriptar la password del usuario
 * */
public interface PasswordEncoderPort {

    String encode(String rawPassword);
}
