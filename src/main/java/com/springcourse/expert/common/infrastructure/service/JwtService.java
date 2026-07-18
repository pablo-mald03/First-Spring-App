package com.springcourse.expert.common.infrastructure.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

/*
 * Esta clase es utilizada para poder manejar las configuraciones del json web token
 * */
@Service
public class JwtService {

    /*
     * Se pueden INYECTAR PARAMETROS DE VARIABLES DE ENTORNO UTILZANDO LA ANOTACION VALUE
     * */
    @Value("${spring.jwt.secret}")
    private String secretKey;
    @Value("${spring.jwt.expiration}")
    private Long expiration;

    /*
     * Metodo que permite poder generar un JWT con el userDetails
     * que seran todos los detalles del usuario que se pasaran por parametros como las contraseñas y demas cosas
     *
     * UserDetails ES UNA CLASE PREDETERMINADA QUE VIENE YA DEFINIDA PARA PODER REPRESENTAR A UN USUARIO
     *
     * */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = Map.of("authorities", userDetails.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .toList()

        );

        return generateToken(claims, userDetails.getUsername());
    }

    /*
     * Este metodo permite poder generar el JWT segun lso parametros que se le vayan agregando
     * */
    public String generateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                /*
                 * valores que vienen del usuario o ciertos agregados*/
                .setClaims(claims)
                /*el sujeto*/
                .setSubject(subject)
                /*
                 * la hora en la que fue creada*/
                .setIssuedAt(new Date(System.currentTimeMillis()))
                /*La expiracion del token*/
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                /*La firma*/
                .signWith(getSignature(), SignatureAlgorithm.HS256)
                .compact();

    }


    /*
     * SE UTILIZA LA CLASE DE Decoders.BASE64
     *
     * YA QUE ESTA FIRMA O SIGNATURE TIENE QUE ESTAR EN BASE64 PARA PODERSE HASHEAR
     * */
    private Key getSignature() {

        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
