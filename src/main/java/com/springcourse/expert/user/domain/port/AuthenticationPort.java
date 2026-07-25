package com.springcourse.expert.user.domain.port;

/*
 * Clase que permite autenticar el token del usuario
 * */
public interface AuthenticationPort {

    String authenticate(String userName, String password);
}
