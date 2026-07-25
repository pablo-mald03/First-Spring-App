package com.springcourse.expert.common.infrastructure.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

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

    /*Tiempo para renovar el JWT (TIEMPO EN EL QUE ES VALIDO QUE SE PUEDA RENOVAR EL TOKEN)*/
    @Value("${spring.jwt.refresh-window}")
    private Long refreshWindow;


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

    /*
     * Metodo que permite extraer los datos del token que provienen del jwt
     *
     * Claims son los atributos que tiene el jwt
     *
     * */
    private Claims getAllClaims(String token) {

        try {


            return Jwts.parserBuilder()
                    .setSigningKey(getSignature())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

        } catch (ExpiredJwtException e) {
            /*
             * Excepcion que permite capturar el token en caso de que ya este vencido
             * */
            return e.getClaims();
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            /*
             * Excepciones que saltan si el jwt viene con algun mal formato
             * */
            throw new RuntimeException("Invalid JWT token or mal formed", e);
        }
    }

    /*
     * Metodo que permite extraer directamente el usuario del JWT
     * */
    public String getUsername(String token) {
        return getAllClaims(token).getSubject();
    }

    /*
     * Metodo que permite extraer un solo claim SEGUN LA CONVENIENCIA DE LA VALIDACION
     *
     * Se le da una funcion por parametro que recibe los claims
     *
     * */
    private <T> T getClaim(String token, Function<Claims, T> claimsMapper) {

        Claims allClaims = getAllClaims(token);
        return claimsMapper.apply(allClaims);
    }

    /*
     * Metodo que permite poder obtener la FECHA DE EXPIRACION del JWT
     * */
    public Date getExpirationDate(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    /*
     * Metodo que permite saber si el token ya ha expirado
     * Retorna booleano
     *
     * before: Saber si la fecha es antes de la fecha actual
     *
     * */
    public boolean isTokenExpired(String token) {
        return getExpirationDate(token).before(new Date());
    }

    /*
     * Metodo que permite saber si el token puede ser renovado
     *
     * MIDE SI LA FECHA DE EXPIRACION DEL TOKEN YA PASO
     * Y SI ESTA DENTRO DEL INTERVALO EN EL QUE LOS TOKENS SE PUEDEN RENOVAR
     *
     * POR LO TANTO SE RESTAN LAS FECHAS
     *
     * */
    public boolean canBeTokenRenewed(String token) {
        Date expiration = getExpirationDate(token);

        long timeSinceExpiration =
                System.currentTimeMillis() - expiration.getTime();

        return timeSinceExpiration <= refreshWindow;
    }

    /*
     * Metodo que permite poder renovar el token
     * */
    public String renewToken(String token, UserDetails userDetails) {
        if (!canBeTokenRenewed(token)) {
            throw new RuntimeException("Token cannot be renewed");
        }

        return generateToken(userDetails);
    }

    /*
     * METODO QUE PERMITE SABER SI EL TOKENA UN ESA VALIDO
     *
     * Y RETORNA EL TOKEN
     * */

    public boolean isValidToken(String token, UserDetails userDetails) {
        String username = getUsername(token);
        return username.equals(userDetails.getUsername());
    }

}
