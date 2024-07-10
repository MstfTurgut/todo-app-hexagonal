package com.mstftrgt.todo_app_hexagonal.infra.adapters.user.jpa;

import com.mstftrgt.todo_app_hexagonal.domain.user.model.User;
import com.mstftrgt.todo_app_hexagonal.domain.user.port.UserPort;
import com.mstftrgt.todo_app_hexagonal.domain.user.usecase.UserRegister;
import com.mstftrgt.todo_app_hexagonal.infra.adapters.user.jpa.entity.UserEntity;
import com.mstftrgt.todo_app_hexagonal.infra.adapters.user.jpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDataAdapter implements UserPort {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User create(UserRegister userRegister) {
        UserEntity userEntity = UserEntity.builder()
                .username(userRegister.getUsername())
                .password(passwordEncoder.encode(userRegister.getPassword()))
                .build();

        UserEntity savedUserEntity = userRepository.save(userEntity);

        return savedUserEntity.toModel();
    }

    @Override
    public boolean isUsernameUnique(String username) {
        Optional<UserEntity> userByUsernameOptional = userRepository.findByUsername(username);

        return userByUsernameOptional.isEmpty();
    }

}
