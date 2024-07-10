package com.mstftrgt.todo_app_hexagonal.infra.adapters.user.rest.dto;

import com.mstftrgt.todo_app_hexagonal.infra.common.Response;

public class RegisterResponse extends Response {
    public RegisterResponse() {
        super("User successfully registered.");
    }
}
