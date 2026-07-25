package com.springcourse.expert.common.infrastructure.filters;

/*
 * Clase que permite poder especificar los filtros de jwt
 * */

import com.springcourse.expert.common.infrastructure.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
/*
 * OncePerRequestFilter
 *
 * Esta clase permite poder generar un filtro por cada request
 *
 * Esta clase sirve como filto (SE PUEDEN DEFINIR VARIOS FILTROS EN BASE A LO QUE SE REQUIERA)
 * */
public class JwtFilter extends OncePerRequestFilter {


    /*
     * Instancia para poder acceder al service que gestiona el Jwt
     * */
    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    /*
     * LA FORMA DE PODER IMPLEMENTAR EXCEPCIONES QUE NOTIFIQUEN DE FORMA CORRECTA EL ERROR DE AUTENTICACION
     * ES UTILIZANDO LA INTERFACE:
     *
     * HandlerExceptionResolver
     *
     * */
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");

        /*
         * Se hace una verificacion de que venga en el header el JWT
         *
         * MIRAR CABECERA DE AUTORIZACION
         * */

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            log.error("No token found");
            filterChain.doFilter(request, response);
            return;
        }


        String token = authorization.substring(7);

        try {
            boolean tokenExpired = jwtService.isTokenExpired(token);
            boolean canBeTokenRenewed = jwtService.canBeTokenRenewed(token);

            /*
             * SI EL TOKEN ESTA EXPIRADO Y NOO PUEDE SER RENOVADO
             * */
            if (tokenExpired && !canBeTokenRenewed) {
                log.error("Token expired");
                filterChain.doFilter(request, response);
                return;
            }

            String username = jwtService.getUsername(token);

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            /*
             * PERMITE PODER VALIDAR UN USUARIO REAL REGISTRADO EN LA BASE DE DATOS.
             *
             * ESTE SI OBTIENE UN USUARIO REAL DE LA BASE DE DATOS
             * */
            boolean validToken = jwtService.isValidToken(token, userDetails);

            /*
             * Se valida el caso en el que no se pueda obtener el username y se genera el respectivo response
             * */
            if (!validToken || SecurityContextHolder.getContext().getAuthentication() != null) {
                log.error("Invalid token or user already authenticated");
                filterChain.doFilter(request, response);
                return;
            }

            /*
             * SI EL TOKEN ESTA EXPIRADO Y PUEDE SER RENOVADO
             *
             * ENTONCES SE HACE EL SET HACIA EL HEADER DEL RESPONSE PARA QUE ESTE
             * SEA RENOVADO/REEMPLAZADO Y PODRA SEGUIR PASANDO POR EL FILTRO
             * */
            if (tokenExpired && canBeTokenRenewed) {
                String renewToken = jwtService.renewToken(token, userDetails);
                response.setHeader("Authorization", "Bearer " + renewToken);
            }

            /*
             * Instancia que permite poder autenticar al usuario con los detalles que este tenga
             *
             * al igual que los roles que tenga
             * */
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
            /*
             * Se le agregan los detalles desde donde se ha hecho la autenticacion
             * para poder tener el contexto al respecto
             * */
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            /*
             * Es donde esta el contexto de las autenticaciones para que se tome en cuenta
             *
             * */
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (Exception e) {

            /*
             * PERMITE SOLTAR LA EXCEPCION PARA PODER INTERCEPTARLA DESDE EL HANDLER
             * */
            log.error("Error while processing request: {}", e.getMessage());
            handlerExceptionResolver.resolveException(request, response, null, e);
        }

        /*
         * Se pasa al siguiente filtro de autenticacion
         * */
        filterChain.doFilter(request, response);

    }
}
