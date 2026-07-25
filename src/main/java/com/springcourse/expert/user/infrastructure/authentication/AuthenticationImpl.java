package com.springcourse.expert.user.infrastructure.authentication;

import com.springcourse.expert.common.infrastructure.service.JwtService;
import com.springcourse.expert.user.domain.port.AuthenticationPort;
import com.springcourse.expert.user.infrastructure.database.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationImpl implements AuthenticationPort {


    /*
     * Va a tomar las configuraciones que se le dieron en security
     * y obtener todos los detalles junto con el metodo de encriptacion que se especifico
     * */
    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    /*Se le envia la password SIN ENCRIPTAR*/
    @Override
    public String authenticate(String userName, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userName,
                password
        ));
        /*
         * POR DEFECTO VA A DEVOLVER UserDetails. por lo tanto por eso se PUEDE CASTEAR A LA ENTIDAD DE USUARIOS
         *
         * */
        UserEntity entity = (UserEntity) authentication.getPrincipal();

        return jwtService.generateToken(entity);
    }
}
