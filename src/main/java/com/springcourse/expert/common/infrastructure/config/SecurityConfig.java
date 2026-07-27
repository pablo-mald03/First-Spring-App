package com.springcourse.expert.common.infrastructure.config;

import com.springcourse.expert.common.infrastructure.filters.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/*
 * Clase que permite activar las configuraciones de seguridad de la aplicacion
 *
 * */
@Configuration
@EnableWebSecurity
/*
 * PERMITE AGREGAR CIERTAS FUNCIONALIDADES PARA PODER RESTRINGIR CIERTOS ENDPOINTS
 * */
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    /*
     * Se declara el servicio para poder utilizar el jwtFilter
     * */
    private final JwtFilter jwtFilter;

    /*SE DEBE DEFINIR CORRECTAMENTE TODOS LOS CAMPOS QUE DEBE TENER LA AUTENTICACION
     * PARA PODER TOMAR EL USUARIO DE LA BASE DE DATOS Y PODER VERIFICAR EL PERMISO HACIA LOS ENDPOINTS*/
    private final AuthenticationProvider authenticationProvider;

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
                                /*LOS REQUEST MATCHERS PERMITEN DEFINIR QUE METODOS HTTP SE PUEDEN CONSUMIR
                                 * EN BASE A LOS ROLES QUE SE LE ESPECIFIQUEN
                                 * */
                                .requestMatchers(
                                        // "/api/v1/products/**",
                                        "/api/v1/users/login",
                                        "/api/v1/users/register",
                                        /*URL'S PARA PODER VER LA DOCUMENTACION DE LA API*/
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**",
                                        "/swagger-ui.html",
                                        "/proxy/**"

                                        /*PERMINTALL permite que todos los usuarios puedan acceder directamente a esos endpoints*/
                                ).permitAll()
                                .requestMatchers("/actuator/**").hasAuthority("ADMIN")
                                .anyRequest().authenticated()
                        /*SE PUEDEN DEFINIR INCLUSO QUE METODOS SOLO SE PUEDEN USAR EN ESA URL*/
                        //.requestMatchers(HttpMethod.POST, "/api/v1/products/**").authenticated()

                        /*se le esta dando un acceso default al metodo http ya que de momento solo esa url esta permitida
                         * caso contrario pedira la autenticacion basica*/

                )
                .authenticationProvider(authenticationProvider)
                /*SE HABILITA UN FORMULARIO DE LOGIN PARA PODER INICIAR SESION
                 * ES UNA PAGINA PROPIA QUE GENERIA SPRING PARA PODER USAR LOGINS
                 * */
                .formLogin(Customizer.withDefaults())
                /*
                 *Customizer.withDefaults() PERMITE REDIRIGIR  A LA URL BASE DE LA API
                 * */
                .oauth2Login(Customizer.withDefaults())

                /*TAMBIEN SE PUEDE UTILIZAR UNA REDIRECCION AUTOMATICA HACIA UNA URL (PERO NO ES UTILIZADO POR APIS, ES MAS POR CLIENTES)
                 *
                 * ESTO REDIRIGE AL USUARIO A LA URL DESTINADA PARA PROCEDER DESPUES DE LOGIN EXITOSO
                 *
                 * .oauth2Login(httpSecurityOAuth2LoginConfigurer ->
                        httpSecurityOAuth2LoginConfigurer.defaultSuccessUrl("/dashboard",true))

                 * */

                .sessionManagement(session -> session.sessionCreationPolicy(
                        //SessionCreationPolicy.STATELESS //INDICA QUE LA SESION NO VA A TENER ESTADO Y SE BASARA EN JWT

                        SessionCreationPolicy.IF_REQUIRED /*SI ES NECESARIO MANTENER LA SESION NO ES MUY UTILIZADO PORQUE
                                                            UNA API REST NO ES NECESARIO QUE UTILICE EL OAUTH2 SOLO ES A NIVEL CLIENTE
                         */
                ))
                /*
                 * Se le agrega el filtro para poder verificar que se este autenticado con el jwt
                 * */
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                /*Se quita la autenticacion basica*/
                //.httpBasic(Customizer.withDefaults())
                .build();
    }

    /*
     * METODO O BEAN QUE PERMITIA REALIZAR LA AUTENTICACION DEL USUARIO (RECUERDO)
     * */
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("password")
//                .roles("USER")
//                .build();
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("admin")
//                .password("password")
//                .roles("ADMIN", "USER")
//                .build();
//        return new InMemoryUserDetailsManager(user, admin);
//    }
}
