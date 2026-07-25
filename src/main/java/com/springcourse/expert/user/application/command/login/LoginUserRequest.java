package com.springcourse.expert.user.application.command.login;

import com.springcourse.expert.common.application.mediator.Request;
import lombok.Data;

@Data
public class LoginUserRequest implements Request<LoginUserResponse> {
    private String email;
    private String password;

}
