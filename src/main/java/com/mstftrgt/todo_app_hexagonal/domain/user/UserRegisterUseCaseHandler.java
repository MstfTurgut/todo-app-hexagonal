package com.mstftrgt.todo_app_hexagonal.domain.user;

import com.mstftrgt.todo_app_hexagonal.domain.common.usecase.UseCaseHandler;
import com.mstftrgt.todo_app_hexagonal.domain.user.exception.UsernameAlreadyInUseException;
import com.mstftrgt.todo_app_hexagonal.domain.user.model.User;
import com.mstftrgt.todo_app_hexagonal.domain.user.port.UserPort;
import com.mstftrgt.todo_app_hexagonal.domain.user.usecase.UserRegister;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegisterUseCaseHandler implements UseCaseHandler<User, UserRegister> {

    private final UserPort userPort;

    @Override
    public User handle(UserRegister useCase) {

        boolean isUsernameUnique = userPort.isUsernameUnique(useCase.getUsername());

        validateUsernameUniquenessOrElseThrow(isUsernameUnique, useCase.getUsername());

        return userPort.create(useCase);
    }

    private void validateUsernameUniquenessOrElseThrow(boolean isUsernameUnique, String username) {
        if(!isUsernameUnique) {
            throw new UsernameAlreadyInUseException(username);
        }
    }

}
