package com.springcourse.expert.user.application.command.login;

import com.springcourse.expert.common.application.mediator.RequestHandler;
import com.springcourse.expert.user.domain.port.AuthenticationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUserHandler implements RequestHandler<LoginUserRequest, LoginUserResponse> {

    private final AuthenticationPort authenticationPort;

    @Override
    public LoginUserResponse handle(LoginUserRequest request) {
        String token = authenticationPort.authenticate(request.getEmail(), request.getPassword());

        return new LoginUserResponse(token);
    }

    @Override
    public Class<LoginUserRequest> getRequestType() {
        return LoginUserRequest.class;
    }
}
