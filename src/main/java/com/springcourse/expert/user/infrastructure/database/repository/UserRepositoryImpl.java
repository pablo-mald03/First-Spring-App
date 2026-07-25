package com.springcourse.expert.user.infrastructure.database.repository;

import com.springcourse.expert.user.domain.User;
import com.springcourse.expert.user.domain.port.UserRepository;
import com.springcourse.expert.user.infrastructure.database.entity.UserEntity;
import com.springcourse.expert.user.infrastructure.database.mapper.UserEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final QueryUserRepository queryUserRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    public Optional<User> findByEmail(String email) {
        return queryUserRepository.findByEmail(email).map(userEntityMapper::mapToUser);
    }

    @Override
    public boolean existByEmail(String email) {
        return queryUserRepository.findByEmail(email).isPresent();
    }

    @Override
    public User insert(User user) {
        UserEntity userEntity = userEntityMapper.mapToUserEntity(user);
        UserEntity saved = queryUserRepository.save(userEntity);
        return userEntityMapper.mapToUser(saved);
    }

    @Override
    public User update(User user) {

        UserEntity userEntity = userEntityMapper.mapToUserEntity(user);
        UserEntity saved = queryUserRepository.save(userEntity);
        return userEntityMapper.mapToUser(saved);
    }
}
