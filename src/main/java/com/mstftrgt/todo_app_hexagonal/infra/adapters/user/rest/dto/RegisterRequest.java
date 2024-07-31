package com.mstftrgt.todo_app_hexagonal.infra.adapters.user.rest.dto;

import com.mstftrgt.todo_app_hexagonal.domain.user.usecase.UserRegister;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank
    @Size(min = 8, max = 16,message = "The username length must be between 8 and 16 characters.")
    private String username;

    @NotBlank
    @Size(min = 8, max = 16,message = "The password length must be between 8 and 16 characters.")
    private String password;

    public UserRegister toUseCase() {
        return UserRegister.builder()
                .username(username)
                .password(password)
                .build();
    }
}
