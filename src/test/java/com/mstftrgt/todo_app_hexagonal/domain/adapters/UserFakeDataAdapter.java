package com.mstftrgt.todo_app_hexagonal.domain.adapters;

import com.mstftrgt.todo_app_hexagonal.domain.user.model.User;
import com.mstftrgt.todo_app_hexagonal.domain.user.port.UserPort;
import com.mstftrgt.todo_app_hexagonal.domain.user.usecase.UserRegister;

public class UserFakeDataAdapter implements UserPort {

    @Override
    public User create(UserRegister userRegister) {
        return User.builder()
                .id("testId")
                .username(userRegister.getUsername())
                .password(userRegister.getPassword())
                .build();
    }

    @Override
    public boolean isUsernameUnique(String username) {
        return username.equals("uniqueUsername");
    }
}
