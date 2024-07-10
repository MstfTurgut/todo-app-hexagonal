package com.mstftrgt.todo_app_hexagonal.infra.adapters.user.rest;

import com.mstftrgt.todo_app_hexagonal.domain.common.usecase.UseCaseHandler;
import com.mstftrgt.todo_app_hexagonal.domain.user.model.User;
import com.mstftrgt.todo_app_hexagonal.domain.user.usecase.UserRegister;
import com.mstftrgt.todo_app_hexagonal.infra.adapters.user.rest.dto.RegisterRequest;
import com.mstftrgt.todo_app_hexagonal.infra.adapters.user.rest.dto.RegisterResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UseCaseHandler<User, UserRegister> userRegisterUseCaseHandler;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponse addUser(@Valid @RequestBody RegisterRequest registerRequest) {
        userRegisterUseCaseHandler.handle(registerRequest.toUseCase());
        return new RegisterResponse();
    }
}
