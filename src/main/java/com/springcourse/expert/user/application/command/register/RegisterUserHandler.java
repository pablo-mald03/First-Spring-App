package com.springcourse.expert.user.application.command.register;

import com.springcourse.expert.common.application.mediator.RequestHandler;
import com.springcourse.expert.user.domain.User;
import com.springcourse.expert.user.domain.UserRole;
import com.springcourse.expert.user.domain.port.AuthenticationPort;
import com.springcourse.expert.user.domain.port.PasswordEncoderPort;
import com.springcourse.expert.user.domain.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterUserHandler implements RequestHandler<RegisterUserRequest, RegisterUserResponse> {

    private final UserRepository userRepository;
    private final PasswordEncoderPort passwordEncoderPort;

    private final AuthenticationPort authenticationPort;

    @Override
    public RegisterUserResponse handle(RegisterUserRequest request) {

        boolean existByEmail = userRepository.existByEmail(request.getEmail());

        if (existByEmail) {
            throw new RuntimeException("User already exists");
        }

        String encoded = passwordEncoderPort.encode(request.getPassword());

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(encoded)
                .role(UserRole.USER)
                .build();

        userRepository.insert(user);

        String token = authenticationPort.authenticate(request.getEmail(), request.getPassword());

        return new RegisterUserResponse(token);

    }

    @Override
    public Class<RegisterUserRequest> getRequestType() {
        return RegisterUserRequest.class;
    }
}
