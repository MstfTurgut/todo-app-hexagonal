package com.mstftrgt.todo_app_hexagonal.domain.user;

import com.mstftrgt.todo_app_hexagonal.domain.adapters.UserFakeDataAdapter;
import com.mstftrgt.todo_app_hexagonal.domain.user.exception.UsernameAlreadyInUseException;
import com.mstftrgt.todo_app_hexagonal.domain.user.model.User;
import com.mstftrgt.todo_app_hexagonal.domain.user.usecase.UserRegister;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class UserRegisterTest {

    UserRegisterUseCaseHandler userRegisterUseCaseHandler;

    @BeforeEach
    void setUp() {
        userRegisterUseCaseHandler = new UserRegisterUseCaseHandler(new UserFakeDataAdapter());
    }

    @Test
    void should_register_user_when_given_username_is_unique() {
        // given
        UserRegister userRegister = UserRegister.builder()
                .username("uniqueUsername")
                .password("testPassword").build();

        // when
        User user = userRegisterUseCaseHandler.handle(userRegister);

        // then
        assertThat(user).isNotNull()
                .returns("testId", User::getId)
                .returns(userRegister.getUsername(), User::getUsername)
                .returns(userRegister.getPassword(), User::getPassword);
    }

    @Test
    void should_not_register_user_when_given_username_is_not_unique() {
        // given
        UserRegister userRegister = UserRegister.builder()
                .username("notUniqueUsername")
                .password("testPassword").build();

        // when & then
        assertThatExceptionOfType(UsernameAlreadyInUseException.class)
                .isThrownBy(() -> userRegisterUseCaseHandler.handle(userRegister))
                .withMessage("This username is taken : " + userRegister.getUsername());
    }
}
