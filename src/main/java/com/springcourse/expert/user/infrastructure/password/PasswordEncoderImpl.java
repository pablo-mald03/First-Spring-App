package com.springcourse.expert.user.infrastructure.password;

import com.springcourse.expert.user.domain.port.PasswordEncoderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/*
 * Service que permite realizar la codificacion de la password
 * */
@Service
@RequiredArgsConstructor
public class PasswordEncoderImpl implements PasswordEncoderPort {

    private final PasswordEncoder passwordEncoder;

    @Override
    public String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}
