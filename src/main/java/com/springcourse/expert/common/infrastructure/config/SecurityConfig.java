package com.springcourse.expert.common.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/*
 * Clase que permite activar las configuraciones de seguridad de la aplicacion
 *
 * */
@Configuration
@EnableWebSecurity
public class SecurityConfig {


    /*
     * CORS: Cross origin, permite recibir peticiones desde otros lugares
     *
     * CSRF: Cross side request forgery: hace referencia a las peticiones que deben hacer hacia la api
     * pero desde nuestro propio dominio
     *
     * */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                                /*LOS REQUEST MATCHERS PERMITEN DEFINIR QUE METODOS HTTP SE PUEDEN CONSUMIR */
                                .requestMatchers(
                                        "/api/v1/products/**",
                                        "/api/v1/users/login",
                                        "/api/v1/register"

                                ).permitAll()
                                .anyRequest().authenticated()
                        /*SE PUEDEN DEFINIR INCLUSO QUE METODOS SOLO SE PUEDEN USAR EN ESA URL*/
                        //.requestMatchers(HttpMethod.POST, "/api/v1/products/**").authenticated()

                        /*se le esta dando un acceso default al metodo http ya que de momento solo esa url esta permitida
                         * caso contrario pedira la autenticacion basica*/

                )
                .sessionManagement(session -> session.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS //INDICA QUE LA SESION NO VA A TENER ESTADO Y SE BASARA EN JWT
                ))
                /*Se quita la autenticacion basica*/
                //.httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("password")
                .roles("ADMIN", "USER")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }
}
