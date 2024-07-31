package com.mstftrgt.todo_app_hexagonal.infra.common.usecase;

import com.mstftrgt.todo_app_hexagonal.domain.common.usecase.UseCaseHandler;
import com.mstftrgt.todo_app_hexagonal.domain.user.model.User;
import com.mstftrgt.todo_app_hexagonal.domain.user.usecase.UserRegister;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class FakeUserRegisterUseCaseHandler implements UseCaseHandler<User, UserRegister> {

    @Override
    public User handle(UserRegister useCase) {

        return User.builder()
                .id("testId")
                .username(useCase.getUsername())
                .password(useCase.getPassword())
                .build();
    }
}
