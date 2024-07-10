package com.mstftrgt.todo_app_hexagonal.domain.user.exception;

public class UsernameAlreadyInUseException extends RuntimeException {
    public UsernameAlreadyInUseException(String username) {
        super("This username is taken : " + username);
    }
}
