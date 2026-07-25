package com.springcourse.expert.common.infrastructure.config;

import com.springcourse.expert.user.infrastructure.database.repository.QueryUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
 * SE LE DA UNA FUNCIONALIDAD A LA INTERFAZ
 *
 * */
@Configuration
@RequiredArgsConstructor
public class AuthenticationConfig {

    private final QueryUserRepository queryUserRepository;

    /*Interfaz que permite retornar o cargar la informacion de un usuario en concreto
     *
     * SE LE TIENE QUE DAR UN USUARIO QUE IMPLEMENTE LA INTERFACE UserDetails (usuario)
     *
     * FORMA AUTOMATICA QUE TIENE LA APLICACION PARA PODER REALIZAR LA AUTENTICACION DEL USUARIO CON
     * DATOS REALES DE LA BASE DE DATOS
     *
     * */
    @Bean
    public UserDetailsService userDetailsService() {

        return username -> queryUserRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));
    }

    /*
     * Interfaz que permite realizar una encriptacion o encoder para la contrasenia del usuario
     *
     * */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * Metodo que permite retornar la instancia del provider de la autenticacion
     * */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    /*
     * Se permite acceder al authentication manager lo que permite tener la configuracion del usuario que esta intentando acceder
     * */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
