package com.springcourse.expert.user.domain.port;

import com.springcourse.expert.user.domain.User;

import java.util.Optional;

/*
 * Interface que define como se comportara el repositorio para poder almacenar a un usuario
 * */
public interface UserRepository {

    Optional<User> findByEmail(String email);

    boolean existByEmail(String email);

    User insert(User user);

    User update(User user);

}
