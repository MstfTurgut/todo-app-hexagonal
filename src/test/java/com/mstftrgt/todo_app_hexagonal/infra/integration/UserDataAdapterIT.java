package com.mstftrgt.todo_app_hexagonal.infra.integration;

import com.mstftrgt.todo_app_hexagonal.domain.user.model.User;
import com.mstftrgt.todo_app_hexagonal.domain.user.usecase.UserRegister;
import com.mstftrgt.todo_app_hexagonal.infra.adapters.user.jpa.UserDataAdapter;
import com.mstftrgt.todo_app_hexagonal.infra.adapters.user.jpa.entity.UserEntity;
import com.mstftrgt.todo_app_hexagonal.infra.adapters.user.jpa.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:sql/users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserDataAdapterIT {

    @Autowired
    UserDataAdapter userDataAdapter;

    @Autowired
    UserRepository userRepository;

    @Test
    void should_create_user() {
        // given
        UserRegister userRegister = UserRegister.builder()
                .username("newUser")
                .password("password")
                .build();

        // when
        User createdUser = userDataAdapter.create(userRegister);

        // then
        Optional<UserEntity> userEntity = userRepository.findById(createdUser.getId());
        assertThat(userEntity).isPresent();
        assertThat(userEntity.get().toModel()).isEqualTo(createdUser);
    }

    @Test
    void should_return_true_when_given_username_is_unique() {
        String uniqueUsername = "uniqueUsername";
        assertThat(userDataAdapter.isUsernameUnique(uniqueUsername)).isTrue();
    }

    @Test
    void should_return_false_when_given_username_is_not_unique() {
        String existingUsername = "testUsername";
        assertThat(userDataAdapter.isUsernameUnique(existingUsername)).isFalse();
    }

}
