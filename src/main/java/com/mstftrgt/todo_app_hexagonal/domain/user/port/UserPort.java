package com.mstftrgt.todo_app_hexagonal.domain.user.port;

import com.mstftrgt.todo_app_hexagonal.domain.user.model.User;
import com.mstftrgt.todo_app_hexagonal.domain.user.usecase.UserRegister;


public interface UserPort {

    User create(UserRegister userRegister);

    boolean isUsernameUnique(String username);
}
