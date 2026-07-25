package com.springcourse.expert.user.application.command.register;

import com.springcourse.expert.common.application.mediator.Request;
import lombok.Data;

@Data
public class RegisterUserRequest implements Request<RegisterUserResponse> {
    private String email;
    private String password;
    private String firstName;
    private String lastName;

}
